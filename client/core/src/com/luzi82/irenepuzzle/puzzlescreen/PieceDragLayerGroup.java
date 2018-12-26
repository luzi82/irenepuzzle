package com.luzi82.irenepuzzle.puzzlescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class PieceDragLayerGroup extends Group {

    final PuzzleScreen parent;

    Image dragImage;

    PieceDragLayerGroup(PuzzleScreen parent) {
        this.parent = parent;
    }

    PiecePanelDragPiece newPiecePanelTakePieceOut() {
        return new PiecePanelDragPiece();
    }

    class PiecePanelDragPiece extends DragListener {

        int pieceIdx;

        public PiecePanelDragPiece() {
            setTapSquareSize(0.1f);
        }

        @Override
        public void dragStart(InputEvent event, float x, float y, int pointer) {
            // Gdx.app.log("asdf", "asdf");
            super.dragStart(event, x, y, pointer);

            if (parent.state != PuzzleScreen.State.PLAY) {
                cancel();
                return;
            }

            float absDeltaX = Math.abs(x - getTouchDownX());
            float absDeltaY = Math.abs(y - getTouchDownY());

            if (absDeltaX <= absDeltaY) {
                cancel();
                return;
            }

            pieceIdx = (int) Math.floor(y);

            // Gdx.app.log("", "" + pieceIdx);
            if (pieceIdx < 0) {
                cancel();
                return;
            }
            if (pieceIdx >= parent.pieceTextureRegionAry.length) {
                cancel();
                return;
            }

            resetImage(pieceIdx);
            setImagePosition(new Vector2(event.getStageX(), event.getStageY()));
        }

        @Override
        public void drag(InputEvent event, float x, float y, int pointer) {
            setImagePosition(new Vector2(event.getStageX(), event.getStageY()));
        }

        @Override
        public void dragStop(InputEvent event, float x, float y, int pointer) {
            resetImage(-1);
            Vector2 stageXY = new Vector2(event.getStageX(), event.getStageY());
            Vector2 boardXY = parent.boardPanelGroup.stageToLocalCoordinates(stageXY);
            int boardX = (int) boardXY.x;
            int boardY = (int) boardXY.y;
            boolean boardXYInRect = true;
            boardXYInRect = boardXYInRect && (boardX >= 0);
            boardXYInRect = boardXYInRect && (boardY >= 0);
            boardXYInRect = boardXYInRect && (boardX < parent.boardPanelGroup.COL_COUNT);
            boardXYInRect = boardXYInRect && (boardY < parent.boardPanelGroup.ROW_COUNT);
            if (!boardXYInRect) {
                cancel();
                return;
            }
            Image pieceImage = parent.boardPanelGroup.createGetPieceImage(pieceIdx);
            pieceImage.setPosition(boardX, boardY);
            parent.pieceXYAry[pieceIdx] = new int[]{boardX, boardY};
            if (parent.isPuzzleComplete()) {
                parent.onPuzzleComplete();
            }
        }
    }

    private void resetImage(int pieceIdx) {
        clearChildren();
        dragImage = null;
        if (pieceIdx < 0) return;
        dragImage = new Image(parent.pieceTextureRegionAry[pieceIdx]);
        dragImage.setSize(parent.boardPieceSize, parent.boardPieceSize);
        addActor(dragImage);
    }

    private void setImagePosition(Vector2 position) {
        if (dragImage == null) return;
        float x = position.x - parent.boardPieceSize / 2;
        float y = position.y - parent.boardPieceSize / 2;
        Gdx.app.debug("", String.format("x:%.2f y:%.2f", x, y));
        dragImage.setPosition(x, y);
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        if (dragImage != null) {
            dragImage.setSize(parent.boardPieceSize, parent.boardPieceSize);
        }
    }
}
