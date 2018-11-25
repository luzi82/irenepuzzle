package com.luzi82.irenepuzzle.puzzlescreen;

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
import com.luzi82.irenepuzzle.Utils;

public class PuzzleScreen extends ScreenAdapter {

    // const
    static final int ROW_COUNT = 6;
    static final int COL_COUNT = 8;

    // layout var affected by resize
    boolean sizeGood = false;
    float[] boardPanelWSEN = new float[4];
    float[] piecePanelWSEN = new float[4];

    // stage
    Stage stage;

    BoardPanelGroup boardPanelGroup;
    PiecePanelGroup piecePanelGroup;

    Texture puzzleImage;
    TextureRegion[] pieceTextureRegionAry;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

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

        boardPanelGroup = new BoardPanelGroup(ROW_COUNT, COL_COUNT);
        stage.addActor(boardPanelGroup);
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

        Utils.setSize(boardPanelGroup, boardPanelWSEN, boardPanelGroup.BOARD_PANEL_WH);
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
