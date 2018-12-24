package com.luzi82.irenepuzzle.menuscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.luzi82.irenepuzzle.Utils;

public class MenuScreen extends ScreenAdapter {

    // const
    public static final float BOADER = (float)((1-Math.sqrt(Utils.PHI-1))/2);
    public static final float SPACE = BOADER;
    public static final float INNER_WIDTH = 2*BOADER+SPACE+2;

    Stage stage;

    Group rootGroup;
    Table table;

    Texture texture;

    @Override
    public void show(){
        Gdx.app.log("",""+SPACE);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        rootGroup = new Group();
        stage.addActor(rootGroup);

        table = new Table();
        rootGroup.addActor(table);
        table.setFillParent(true);
        table.setRound(false);
        table.setDebug(true);

        table.defaults().width(1f).height(1f).space(SPACE);

        texture = new Texture(Gdx.files.internal("badlogic.jpg"));

        ImageButton ib0 = new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
        ib0.setRound(false);
        table.add(ib0);

        ImageButton ib1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
        ib1.setRound(false);
        table.add(ib1);

        //table.row();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        Rectangle fullRect = new Rectangle(0,0,width,height);

        Utils.setSize(rootGroup, fullRect, new Vector2(INNER_WIDTH, INNER_WIDTH*height/width));
        rootGroup.setBounds(0,0,INNER_WIDTH,INNER_WIDTH*height/width);
    }

        @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

}
