package com.luzi82.irenepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PuzzleScreen extends ScreenAdapter {

    // const
    static final int ROW_COUNT = 6;
    static final int COL_COUNT = 8;

    static final float[] BOARD_PANEL_WSEN = {0, 0, COL_COUNT, ROW_COUNT};
    static final float[] BOARD_PANEL_WH = Utils.wsenToWh(BOARD_PANEL_WSEN);

    // texture
    Texture boardPanelBackgroundTexture;
    Texture puzzleImage;
    TextureRegion[] pieceTextureRegionAry;

    // layout var affected by resize
    boolean sizeGood = false;
    float[] boardPanelWSEN = new float[4];
    float[] piecePanelWSEN = new float[4];

    // stage
    Stage stage;

    Group boardPanel;
    Image boardPanelBgImage;

    PiecePanelGroup piecePanelGroup;


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        float gray;
        Color grayColor;

        gray = 2 - Utils.PHI;
        grayColor = new Color(gray, gray, gray, 1f);
        boardPanelBackgroundTexture = Utils.createColorTexture(grayColor);

        puzzleImage = new Texture("badlogic.jpg");
        pieceTextureRegionAry = new TextureRegion[ROW_COUNT * COL_COUNT];
        int i = 0;
        for (int r = 0; r < ROW_COUNT; ++r) {
            for (int c = 0; c < COL_COUNT; ++c) {
                float u = ((float) c) / COL_COUNT;
                float v = ((float) r) / ROW_COUNT;
                float u2 = ((float) c + 1) / COL_COUNT;
                float v2 = ((float) r + 1) / ROW_COUNT;
                pieceTextureRegionAry[i] = new TextureRegion(puzzleImage, u, v, u2, v2);
                ++i;
            }
        }

        boardPanel = new Group();
        Utils.setBounds(boardPanel, BOARD_PANEL_WSEN);
        stage.addActor(boardPanel);
        boardPanel.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                return false;
            }
        });

        boardPanelBgImage = new Image(boardPanelBackgroundTexture);
        Utils.setSize(boardPanelBgImage, BOARD_PANEL_WSEN);
        boardPanel.addActor(boardPanelBgImage);

        piecePanelGroup = new PiecePanelGroup();
        stage.addActor(piecePanelGroup);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        //System.out.println(String.format("resize %s %s",width,height));
        float piecePanelWidth = height / 8f;
        float mid = width - piecePanelWidth;
        sizeGood = mid > 0;
        if (!sizeGood) return;
        if (mid / COL_COUNT > height / ROW_COUNT) {
            float boardPanelWidth = height * COL_COUNT / ROW_COUNT;
            boardPanelWSEN = new float[]{mid - boardPanelWidth, 0, mid, height};
        } else {
            float boardPanelHeight = mid * ROW_COUNT / COL_COUNT;
            boardPanelWSEN = new float[]{0, (height - boardPanelHeight) / 2f, mid, (height + boardPanelHeight) / 2f};
        }

        piecePanelWSEN = new float[]{mid, 0, width, height};

        Utils.setSize(boardPanel, boardPanelWSEN, BOARD_PANEL_WH);
        Utils.setSize(piecePanelGroup, piecePanelWSEN, PiecePanelGroup.PIECE_PANEL_WH);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
