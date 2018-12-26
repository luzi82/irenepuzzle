package com.luzi82.irenepuzzle.puzzlescreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.luzi82.irenepuzzle.Utils;

public class PiecePanelGroup extends Group {

    // const
    static public final float PIECE_PANEL_HEIGHT_RATIO = 8;
    static public final Rectangle INNER_RECT = new Rectangle(0, 0, 1, PIECE_PANEL_HEIGHT_RATIO);
    static public final float GRAY = Utils.PHI - 1;
    static public final Color BG_COLOR = new Color(GRAY, GRAY, GRAY, 1f);

    static public final float PIECE_SIZE = 0.8f;
    static public final float PIECE_OFFSET = (1 - PIECE_SIZE) / 2f;

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
        Utils.setSize(bgImage, INNER_RECT);
        addActor(bgImage);

        contextGroup = new Group();
        addActor(contextGroup);

        pieceImageAry = new Image[parent.pieceTextureRegionAry.length];
        for (int i = 0; i < pieceImageAry.length; ++i) {
            pieceImageAry[i] = new Image(parent.pieceTextureRegionAry[i]);
            pieceImageAry[i].setSize(PIECE_SIZE, PIECE_SIZE);
            pieceImageAry[i].setPosition(PIECE_OFFSET, i + PIECE_OFFSET);
            contextGroup.addActor(pieceImageAry[i]);
        }

        setBounds(0, 0, 1, PIECE_PANEL_HEIGHT_RATIO);
        addListener(new UpDownScroll());
        contextGroup.addListener(parent.pieceDragLayerGroup.newPiecePanelTakePieceOut());
    }

    class UpDownScroll extends DragListener {

        float dragOffsetY;

        public UpDownScroll() {
            setTapSquareSize(0.1f);
        }

        @Override
        public void dragStart(InputEvent event, float x, float y, int pointer) {
            super.dragStart(event, x, y, pointer);

            if (parent.state != PuzzleScreen.State.PLAY) {
                cancel();
                return;
            }

            float absDeltaX = Math.abs(x - getTouchDownX());
            float absDeltaY = Math.abs(y - getTouchDownY());

            if (absDeltaX > absDeltaY) {
                cancel();
                return;
            }

            dragOffsetY = contextGroup.getY() - getTouchDownY();
        }

        @Override
        public void drag(InputEvent event, float x, float y, int pointer) {
            super.drag(event, x, y, pointer);

            contextGroup.setY(y + dragOffsetY);
        }

        @Override
        public void dragStop(InputEvent event, float x, float y, int pointer) {
            super.dragStop(event, x, y, pointer);
        }
    }

    public void dispose() {
        bgTexture.dispose();
    }

}
