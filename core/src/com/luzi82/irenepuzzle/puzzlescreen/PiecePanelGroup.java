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

    // member
    Image bgImage;
    Texture bgTexture;

    public PiecePanelGroup() {
        bgTexture = Utils.createColorTexture(BG_COLOR);
        bgImage = new Image(bgTexture);
        Utils.setSize(bgImage, PIECE_PANEL_WSEN);
        addActor(bgImage);
    }

    public void dispose(){
        bgTexture.dispose();
    }

}
