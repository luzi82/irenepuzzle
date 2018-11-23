package com.luzi82.irenepuzzle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Utils {

    public static final float PHI = (float)((1+Math.sqrt(5))/2);

    public static void draw(SpriteBatch batch,Texture img,int[] wsen){
        batch.draw(img,wsen[0],wsen[1],wsen[2]-wsen[0],wsen[3]-wsen[1]);
    }

}
