package com.luzi82.irenepuzzle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import javax.xml.soap.Text;

public class Utils {

    public static final float PHI = (float)((1+Math.sqrt(5))/2);

    public static void draw(SpriteBatch batch,Texture img,float[] wsen){
        batch.draw(img,wsen[0],wsen[1],wsen[2]-wsen[0],wsen[3]-wsen[1]);
    }

    public static void draw(SpriteBatch batch, TextureRegion img, float[] wsen){
        batch.draw(img,wsen[0],wsen[1],wsen[2]-wsen[0],wsen[3]-wsen[1]);
    }

    public static void setSize(Actor actor, float[] wsen){
        actor.setPosition(wsen[0],wsen[1]);
        actor.setScale(wsen[2]-wsen[0],wsen[3]-wsen[1]);
    }

    public static void setSize(Actor actor,float[] wsen,float[] wh){
        actor.setPosition(wsen[0],wsen[1]);
        actor.setScale((wsen[2]-wsen[0])/wh[0],(wsen[3]-wsen[1])/wh[1]);
    }

    public static void setBounds(Actor actor,float[] wsen){
        actor.setBounds(wsen[0],wsen[1],wsen[2]-wsen[0],wsen[3]-wsen[1]);
    }

    public static float[] wsenToWh(float[] wsen){
        return new float[]{wsen[2]-wsen[0],wsen[3]-wsen[1]};
    }

    public static Texture createColorTexture(Color color){
        Pixmap tmpPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        tmpPixmap.setColor(color);
        tmpPixmap.fill();
        Texture texture = new Texture(tmpPixmap);
        tmpPixmap.dispose();
        return texture;
    }

}
