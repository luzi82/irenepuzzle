package com.luzi82.irenepuzzle.menuscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.luzi82.irenepuzzle.IrenePuzzleGame;
import com.luzi82.irenepuzzle.Utils;
import com.luzi82.irenepuzzle.puzzlescreen.PuzzleScreen;

public class MenuScreen extends ScreenAdapter {

    // const
    public static final float BOADER = (float) ((1 - Math.sqrt(Utils.PHI - 1)) / 2);
    public static final float SPACE = BOADER;
    public static final float INNER_WIDTH = 2 * BOADER + SPACE + 2;

    Stage stage;

    ScrollPane scrollPane;
    Table table;
    ImageButton[] ibAry;

    Texture texture;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setRound(false);
        table.setDebug(true);

        texture = new Texture(Gdx.files.internal("badlogic.jpg"));

        ibAry = new ImageButton[4];

        ibAry[0] = new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
        ibAry[0].setRound(false);
        table.add(ibAry[0]);

        ibAry[1] = new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
        ibAry[1].setRound(false);
        table.add(ibAry[1]);

        table.row();

        ibAry[2] = new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
        ibAry[2].setRound(false);
        table.add(ibAry[2]);

        ibAry[3] = new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
        ibAry[3].setRound(false);
        table.add(ibAry[3]);

        scrollPane = new ScrollPane(table);
        scrollPane.setDebug(true, true);
        scrollPane.layout();
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFillParent(true);
        scrollPane.setLayoutEnabled(true);

        stage.addActor(scrollPane);

        for(ImageButton ib:ibAry){
            ib.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    IrenePuzzleGame.getInstance().setScreen(new PuzzleScreen());
                }
            });
        }

//        stage.addActor(table);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        if (height > width) {
            float tableHeight = width * (2 + SPACE) / INNER_WIDTH;
            float top = (height - tableHeight) / (Utils.PHI + 1);
            if (top < width * SPACE / INNER_WIDTH) {
                top = width * BOADER / INNER_WIDTH;
            }
            table.padLeft(width * BOADER / INNER_WIDTH);
            table.padRight(width * BOADER / INNER_WIDTH);
            table.padTop(top);
            table.padBottom(height - top - tableHeight);
        } else {
            table.pad(width * SPACE / INNER_WIDTH);
        }
        for (Cell cell : table.getCells()) {
            cell.space(width * SPACE / INNER_WIDTH);
            cell.width(width * 1 / INNER_WIDTH);
            cell.height(width * 1 / INNER_WIDTH);
        }

        for (ImageButton ib : ibAry) {
            ib.getImageCell().width(width * 1 / INNER_WIDTH).height(width * 1 / INNER_WIDTH);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

}
