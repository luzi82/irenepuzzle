package com.luzi82.irenepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

public class PuzzleScreen extends ScreenAdapter {

    static final int ROW_COUNT = 6;
    static final int COL_COUNT = 8;

    SpriteBatch batch;

    boolean sizeGood=false;
    int[] boardWSEN = new int[4];
    int[] pieceWSEN = new int[4];

    Texture boardPanelBackgroundTexture;
    Texture piecePanelBackgroundTexture;

    Texture puzzleImage;
    TextureRegion[] pieceTextureRegionAry;

    @Override
    public void show () {
        batch = new SpriteBatch();
        float gray;
        Color grayColor;

        gray = 2-Utils.PHI;
        grayColor = new Color(gray,gray,gray,1f);
        boardPanelBackgroundTexture = Utils.createColorTexture(grayColor);

        gray = Utils.PHI-1;
        grayColor = new Color(gray,gray,gray,1f);
        piecePanelBackgroundTexture = Utils.createColorTexture(grayColor);

        puzzleImage = new Texture("badlogic.jpg");
        pieceTextureRegionAry = new TextureRegion[ROW_COUNT*COL_COUNT];
        int i=0;
        for(int r=0;r<ROW_COUNT;++r) {
            for(int c=0;c<COL_COUNT;++c) {
                float u = ((float)c)/COL_COUNT;
                float v = ((float)r)/ROW_COUNT;
                float u2 = ((float)c+1)/COL_COUNT;
                float v2 = ((float)r+1)/ROW_COUNT;
                pieceTextureRegionAry[i] = new TextureRegion(puzzleImage,u,v,u2,v2);
                ++i;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        //System.out.println(String.format("resize %s %s",width,height));
        int mid = width-height/8;
        sizeGood = mid>0;
        if(sizeGood) {
            boardWSEN = new int[]{0,0,mid,height};
            pieceWSEN = new int[]{mid,0,width,height};
            Matrix4 m4=new Matrix4();
            batch.setTransformMatrix(m4);
            m4=new Matrix4();
            m4.setToOrtho2D(0,0,width,height);
            batch.setProjectionMatrix(m4);
        }
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(!sizeGood)return;
        batch.begin();
        Utils.draw(batch,boardPanelBackgroundTexture,boardWSEN);
        Utils.draw(batch,piecePanelBackgroundTexture,pieceWSEN);
        int i=0;
        for(int r=0;r<ROW_COUNT;++r) {
            for(int c=0;c<COL_COUNT;++c) {
                int[] wsen=new int[4];
                int rr = ROW_COUNT-1-r;
                wsen[0] = c*100;
                wsen[1] = rr*100;
                wsen[2] = wsen[0]+90;
                wsen[3] = wsen[1]+90;
                Utils.draw(batch,pieceTextureRegionAry[i],wsen);
                ++i;
            }
        }
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }

}
