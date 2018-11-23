package com.luzi82.irenepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class PuzzleScreen extends ScreenAdapter {

    SpriteBatch batch;

    boolean sizeGood=false;
    int[] boardWSEN = new int[4];
    int[] pieceWSEN = new int[4];

    Texture boardPanelBackgroundTexture;
    Texture piecePanelBackgroundTexture;

    @Override
    public void show () {
        batch = new SpriteBatch();
        Pixmap tmpPixmap;
        float gray;

        gray = 2-Utils.PHI;
        tmpPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        tmpPixmap.setColor(gray,gray,gray,1f);
        tmpPixmap.fill();
        boardPanelBackgroundTexture = new Texture(tmpPixmap);
        tmpPixmap.dispose();
        tmpPixmap=null;

        gray = Utils.PHI-1;
        tmpPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        tmpPixmap.setColor(gray,gray,gray,1f);
        tmpPixmap.fill();
        piecePanelBackgroundTexture = new Texture(tmpPixmap);
        tmpPixmap.dispose();
        tmpPixmap=null;
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
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }

}
