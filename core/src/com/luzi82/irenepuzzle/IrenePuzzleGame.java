package com.luzi82.irenepuzzle;

import com.badlogic.gdx.Game;
import com.luzi82.irenepuzzle.puzzlescreen.PuzzleScreen;

public class IrenePuzzleGame extends Game {

    @Override
    public void create() {
        setScreen(new PuzzleScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
