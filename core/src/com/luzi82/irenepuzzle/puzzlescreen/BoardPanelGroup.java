package com.luzi82.irenepuzzle.puzzlescreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.luzi82.irenepuzzle.Utils;

public class BoardPanelGroup extends Group {

    // const
    final int ROW_COUNT;
    final int COL_COUNT;

    final float[] BOARD_PANEL_WSEN;
    final float[] BOARD_PANEL_WH;

    static public final float GRAY = 2 - Utils.PHI;
    static public final Color BG_COLOR = new Color(GRAY, GRAY, GRAY, 1f);

    // member
    Texture bgTexture;
    Image bgImage;

    public BoardPanelGroup(int rowCount, int colCount) {
        ROW_COUNT = rowCount;
        COL_COUNT = colCount;
        BOARD_PANEL_WSEN = new float[]{0, 0, COL_COUNT, ROW_COUNT};
        BOARD_PANEL_WH = Utils.wsenToWh(BOARD_PANEL_WSEN);

        bgTexture = Utils.createColorTexture(BG_COLOR);
        bgImage = new Image(bgTexture);
        Utils.setSize(bgImage, BOARD_PANEL_WSEN);
        addActor(bgImage);
    }

    public void dispose(){
        bgTexture.dispose();
    }

}
