package com.luzi82.irenepuzzle.puzzlescreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.luzi82.irenepuzzle.Utils;

public class PiecePanelGroup extends Group {

    // const
    static public final float PIECE_PANEL_HEIGHT_RATIO = 8;
    static public final float[] PIECE_PANEL_WSEN = {0, 0, 1, PIECE_PANEL_HEIGHT_RATIO};
    static public final float[] PIECE_PANEL_WH = Utils.wsenToWh(PIECE_PANEL_WSEN);
    static public final float GRAY = Utils.PHI - 1;
    static public final Color BG_COLOR = new Color(GRAY, GRAY, GRAY, 1f);

    static public final float PIECE_SIZE = 0.8f;
    static public final float PIECE_OFFSET = (1-PIECE_SIZE)/2f;

    // link
    final PuzzleScreen parent;

    // member
    Image bgImage;
    Texture bgTexture;

    Group contextGroup;
    Image[] pieceImageAry;

    public PiecePanelGroup(PuzzleScreen parent) {
        this.parent = parent;

        bgTexture = Utils.createColorTexture(BG_COLOR);
        bgImage = new Image(bgTexture);
        Utils.setSize(bgImage, PIECE_PANEL_WSEN);
        addActor(bgImage);

        contextGroup = new Group();
        addActor(contextGroup);

        pieceImageAry = new Image[parent.pieceTextureRegionAry.length];
        for (int i = 0; i < pieceImageAry.length; ++i) {
            pieceImageAry[i] = new Image(parent.pieceTextureRegionAry[i]);
            pieceImageAry[i].setSize(PIECE_SIZE,PIECE_SIZE);
            pieceImageAry[i].setPosition(PIECE_OFFSET,i+PIECE_OFFSET);
            contextGroup.addActor(pieceImageAry[i]);
        }
    }

    public void dispose() {
        bgTexture.dispose();
    }

}
