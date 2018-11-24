package com.luzi82.irenepuzzle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PiecePanelGroup extends Group {

    // const
    static public final float PIECE_PANEL_HEIGHT_RATIO = 8;
    static public final float[] PIECE_PANEL_WSEN = {0, 0, 1, PIECE_PANEL_HEIGHT_RATIO};
    static public final float[] PIECE_PANEL_WH = Utils.wsenToWh(PIECE_PANEL_WSEN);
    static public final float GRAY = Utils.PHI - 1;
    static public final Color BG_COLOR = new Color(GRAY, GRAY, GRAY, 1f);

    // member
    Image piecePanelBgImage;
    Texture piecePanelBgTexture;

    public PiecePanelGroup() {
        piecePanelBgTexture = Utils.createColorTexture(BG_COLOR);
        piecePanelBgImage = new Image(piecePanelBgTexture);
        Utils.setSize(piecePanelBgImage, PIECE_PANEL_WSEN);
        addActor(piecePanelBgImage);
    }

}
