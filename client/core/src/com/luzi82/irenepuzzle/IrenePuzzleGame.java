package com.luzi82.irenepuzzle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.luzi82.irenepuzzle.menuscreen.MenuScreen;

public class IrenePuzzleGame extends Game {

    public FreeTypeFontGenerator fontGenerator;

    @Override
    public void create() {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("DroidSansFallback.ttf"));
        setScreen(new MenuScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
        if (fontGenerator != null) {
            fontGenerator.dispose();
            fontGenerator = null;
        }
    }

    static public IrenePuzzleGame getInstance() {
        return (IrenePuzzleGame) Gdx.app.getApplicationListener();
    }
}
