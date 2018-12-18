package com.luzi82.irenepuzzle.puzzlescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.luzi82.irenepuzzle.Utils;

public class PuzzleScreen extends ScreenAdapter {

    // const
    static final int ROW_COUNT = 2;
    static final int COL_COUNT = 2;

    // layout var affected by resize
    boolean sizeGood = false;
    float boardPieceSize = 0;

    // stage
    Stage stage;

    PuzzleCompleteDialogGroup puzzleCompleteDialogGroup;
    BoardPanelGroup boardPanelGroup;
    PiecePanelGroup piecePanelGroup;

    Texture puzzleImage;
    TextureRegion[] pieceTextureRegionAry;

    // actions
    PieceDragLayerGroup pieceDragLayerGroup;

    // member
    public int[][] pieceXYAry; // idx: piece-id, xy. dev temp var

    public enum State{
        PLAY,
        COMPLETE
    }
    public State state;

    public PuzzleScreen(){
        state = State.PLAY;
        pieceXYAry = new int[ROW_COUNT*COL_COUNT][];
    }

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

        pieceDragLayerGroup =new PieceDragLayerGroup(this);
        boardPanelGroup = new BoardPanelGroup(this, ROW_COUNT, COL_COUNT);
        piecePanelGroup = new PiecePanelGroup(this);
        puzzleCompleteDialogGroup = new PuzzleCompleteDialogGroup(this);

        stage.addActor(boardPanelGroup);
        stage.addActor(piecePanelGroup);
        stage.addActor(pieceDragLayerGroup);
        stage.addActor(puzzleCompleteDialogGroup);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        Rectangle fullRect = new Rectangle(0,0,width,height);

        Rectangle boardPanelRect;
        float piecePanelWidth = height / 8f;
        float mid = width - piecePanelWidth;
        sizeGood = mid > 0;
        if (!sizeGood) return;
        if (mid / COL_COUNT > height / ROW_COUNT) {
            float boardPanelWidth = height * COL_COUNT / ROW_COUNT;
            boardPanelRect = new Rectangle(mid - boardPanelWidth, 0, boardPanelWidth, height);
        } else {
            float boardPanelHeight = mid * ROW_COUNT / COL_COUNT;
            boardPanelRect = new Rectangle(0, (height - boardPanelHeight) / 2f, mid, boardPanelHeight);
        }
        boardPieceSize = Math.min(mid / COL_COUNT, height / ROW_COUNT);

//        piecePanelWSEN = new float[]{mid, 0, width, height};
        Rectangle piecePanelRect = new Rectangle(mid, 0, piecePanelWidth, height);

        Vector2 tmpV2 = new Vector2();

        Utils.setSize(pieceDragLayerGroup, fullRect, fullRect.getSize(tmpV2));
        Utils.setSize(boardPanelGroup, boardPanelRect, boardPanelGroup.INNER_RECT.getSize(tmpV2));
        Utils.setSize(piecePanelGroup, piecePanelRect, PiecePanelGroup.INNER_RECT.getSize(tmpV2));

        puzzleCompleteDialogGroup.onParentResize(width, height);
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
        puzzleCompleteDialogGroup.dispose();
        boardPanelGroup.dispose();
        piecePanelGroup.dispose();
        puzzleImage.dispose();
        stage.dispose();
    }

    public boolean isPuzzleComplete() {
        for(int i=0;i<ROW_COUNT*COL_COUNT;++i){
            if(pieceXYAry[i]==null)return false;
            int x = pieceXYAry[i][0]; int y = pieceXYAry[i][1];
            int tx = i%COL_COUNT; int ty = (ROW_COUNT-1-i/COL_COUNT);
            // Gdx.app.log("",String.format("CTXLIQOEXF xy=%d,%d txy=%d,%d",x,y,tx,ty));
            if(x!=tx)return false;
            if(y!=ty)return false;
        }
        return true;
    }

    public void onPuzzleComplete(){
        Gdx.app.log("","FZTCOITZJJ onPuzzleComplete");
        state = State.COMPLETE;
        puzzleCompleteDialogGroup.setEnable(true);
    }

}
