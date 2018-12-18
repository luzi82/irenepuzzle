package com.luzi82.irenepuzzle.puzzlescreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.luzi82.irenepuzzle.Utils;

public class PuzzleCompleteDialogGroup extends Group {

    // const
    static public final Color BG_COLOR = new Color(0,0,0, 0.5f);
    static public final Rectangle INNER_RECT = new Rectangle(0,0,1,Utils.PHI-1);

    // link
    final PuzzleScreen parent;

    // member
    Image bgImage;
    Texture bgTexture;

    public PuzzleCompleteDialogGroup(PuzzleScreen parent) {
        this.parent = parent;

        bgTexture = Utils.createColorTexture(BG_COLOR);
    }

    public void onParentResize(int parentWidth, int parentHeight){
        float myWidth = parentWidth / Utils.PHI;
        float myHeight = parentHeight / Utils.PHI;

        if(myWidth/INNER_RECT.getWidth()<myHeight/INNER_RECT.getHeight()){
            myHeight = INNER_RECT.getHeight() * myWidth/INNER_RECT.getWidth();
        }else{
            myWidth = INNER_RECT.getWidth() * myHeight/INNER_RECT.getHeight();
        }

        float myX = (parentWidth-myWidth)/2;
        float myY = (parentHeight-myHeight)/(Utils.PHI);

        Rectangle myRect = new Rectangle(myX, myY, myWidth, myHeight);
        Utils.setSize(this, myRect, INNER_RECT.getSize(new Vector2()));
    }

    public void setEnable(boolean enable){
        if(enable&&(bgImage==null)) {
            bgImage = new Image(bgTexture);
            Utils.setSize(bgImage, INNER_RECT);
            addActor(bgImage);
        }else if((!enable)&&(bgImage!=null)){
            removeActor(bgImage);
            bgImage = null;
        }
    }

    public void dispose(){
        if(bgTexture!=null) {
            bgTexture.dispose();
            bgTexture = null;
        }
    }

}
