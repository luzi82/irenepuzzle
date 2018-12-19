package com.luzi82.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class OneLineLabel extends Group {

    // setting
    FreeTypeFontGenerator freeTypeFontGenerator;
    int alignment;
    CharSequence text;

    int fontSizeMin = 8;
    int fontSizeMax = 256;

    // dynamic
    int fontHeightPt = -1;

    // member
    Group labelScaleGroup;
    BitmapFont labelFont;
    Label label;

    public OneLineLabel(){
        labelScaleGroup = new Group();
        addActor(labelScaleGroup);
    }

    public void setFreeTypeFontGenerator(FreeTypeFontGenerator freeTypeFontGenerator){
        this.freeTypeFontGenerator = freeTypeFontGenerator;
        updateLabel();
    }

    public void setText(CharSequence text){
        this.text = text;
        updateLabel();
    }

    public void setAlignment(int alignment){
        this.alignment = alignment;
    }

    public void setFontSizeMin(int fontSizeMin) {
        this.fontSizeMin = fontSizeMin;
    }

    public void setFontSizeMax(int fontSizeMax) {
        this.fontSizeMax = fontSizeMax;
    }

    public void act(float delta){
        super.act(delta);
        detectFontSizeChange();
    }

    void detectFontSizeChange(){
        Stage stage = getStage();
        if(stage==null)return;

        Vector2 topPt = localToStageCoordinates(Vector2.Y.cpy());
        topPt = stage.stageToScreenCoordinates(topPt);

        Vector2 basePt = localToStageCoordinates(Vector2.Zero.cpy());
        basePt = stage.stageToScreenCoordinates(basePt);

        int fontHeightPixel = (int)Math.ceil(topPt.dst(basePt));
        int fontHeightPt = freeTypeFontGenerator.scaleForPixelHeight(fontHeightPixel);

        boolean change = false;
        change = change || ( OneLineLabel.this.fontHeightPt > fontHeightPt*2 );
        change = change || ( OneLineLabel.this.fontHeightPt < fontHeightPt );

        if(!change)return;

        fontHeightPt = (int)Math.ceil(fontHeightPt*SQRT2);
        fontHeightPt = Math.max(fontHeightPt, fontSizeMin);
        fontHeightPt = Math.min(fontHeightPt, fontSizeMax);
        change = (fontHeightPt!=OneLineLabel.this.fontHeightPt);

        if(!change)return;

        OneLineLabel.this.fontHeightPt = fontHeightPt;
        updateLabel();
    }

    void updateLabel(){
        if(label!=null) {
            labelScaleGroup.removeActor(label);
            label = null;
        }
        if(labelFont!=null){
            labelFont.dispose();
            labelFont=null;
        }

        if(freeTypeFontGenerator==null)return;
        if(text==null)return;
        if(fontHeightPt<=0)return;

        Gdx.app.log("HUEDGZBEGD", String.format("fontHeightPt=%d",fontHeightPt));

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = fontHeightPt;
        labelFont = freeTypeFontGenerator.generateFont(fontParameter);

        float fontHeight = labelFont.getLineHeight();
        labelScaleGroup.setScale(1/fontHeight);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = labelFont;
        label = new Label(text,labelStyle);
        label.setAlignment(alignment);
        if((alignment&Align.left)!=0){
            label.setPosition(0,0);
        }else if((alignment&Align.right)!=0){
            label.setPosition(-label.getPrefWidth(),0);
        }else{
            label.setPosition(-label.getPrefWidth()/2f,0);
        }
        labelScaleGroup.addActor(label);
    }

    public void dispose(){
        if(labelFont!=null){
            labelFont.dispose();
            labelFont=null;
        }
    }

    static final float SQRT2 = (float)Math.sqrt(2);

}
