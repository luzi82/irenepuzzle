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

    // const
    static final int ROW_COUNT = 6;
    static final int COL_COUNT = 8;

    // texture
    Texture boardPanelBackgroundTexture;
    Texture piecePanelBackgroundTexture;
    Texture puzzleImage;
    TextureRegion[] pieceTextureRegionAry;

    // layout var affected by resize
    SpriteBatch batch;

    boolean sizeGood=false;
    float[] boardWSEN = new float[4];
    float[] pieceWSEN = new float[4];

    float piecePanelPieceDistance;
    float piecePanelPieceSize;
    float[] pieceContentWE = new float[2];

    // layout var affected by action
    float pieceContentOffset = 0;

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
        float piecePanelWidth = height/8f;
        float mid = width-piecePanelWidth;
        sizeGood = mid>0;
        if(sizeGood) {
            boardWSEN = new float[]{0,0,mid,height};
            pieceWSEN = new float[]{mid,0,width,height};

            piecePanelPieceDistance = piecePanelWidth;
            piecePanelPieceSize = piecePanelWidth*0.8f;
            pieceContentWE[0] = mid+(piecePanelWidth-piecePanelPieceSize)/2;
            pieceContentWE[1] = pieceContentWE[0] + piecePanelPieceSize;

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

        float[] wsen = new float[4];
        for(int i=0;i<pieceTextureRegionAry.length;++i){
            wsen[0] = pieceContentWE[0];
            wsen[1] = i*piecePanelPieceDistance+pieceContentOffset;
            wsen[2] = pieceContentWE[1];
            wsen[3] = wsen[1]+piecePanelPieceSize;
            Utils.draw(batch,pieceTextureRegionAry[i],wsen);
        }
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }

}
