package com.luzi82.irenepuzzle.puzzlescreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.luzi82.irenepuzzle.Utils;

public class BoardPanelGroup extends Group {

    // const
    public final int ROW_COUNT;
    public final int COL_COUNT;

    //    final float[] BOARD_PANEL_WSEN;
//    final float[] BOARD_PANEL_WH;
    final Rectangle INNER_RECT;

    static public final float GRAY = 2 - Utils.PHI;
    static public final Color BG_COLOR = new Color(GRAY, GRAY, GRAY, 1f);

    // parent
    final PuzzleScreen parent;

    // member
    Texture bgTexture;
    Image bgImage;
    Group pieceImageGroup;
    public Image[] pieceImageAry; // idx: piece id

    public BoardPanelGroup(PuzzleScreen parent, int rowCount, int colCount) {
        this.parent = parent;

        ROW_COUNT = rowCount;
        COL_COUNT = colCount;
//        BOARD_PANEL_WSEN = new float[]{0, 0, COL_COUNT, ROW_COUNT};
//        BOARD_PANEL_WH = Utils.wsenToWh(BOARD_PANEL_WSEN);
        INNER_RECT = new Rectangle(0, 0, COL_COUNT, ROW_COUNT);

        bgTexture = Utils.createColorTexture(BG_COLOR);
        bgImage = new Image(bgTexture);
        Utils.setSize(bgImage, INNER_RECT);
        addActor(bgImage);

        pieceImageGroup = new Group();
        addActor(pieceImageGroup);
        pieceImageAry = new Image[ROW_COUNT*COL_COUNT];
    }

    Image createGetPieceImage(int pieceId){
        if(pieceImageAry[pieceId]==null){
            pieceImageAry[pieceId]=new Image(parent.pieceTextureRegionAry[pieceId]);
            pieceImageAry[pieceId].setSize(1,1);
            pieceImageGroup.addActor(pieceImageAry[pieceId]);
        }
        return pieceImageAry[pieceId];
    }

    public void dispose() {
        bgTexture.dispose();
    }

}
