package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Screens.ClosingScreen;
import com.mygdx.game.Screens.DilemmaScreen;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Screens.IntroScreen;

public abstract class ZeppelinGame extends Game {

    private IntroScreen introScreen;
    private GameScreen gameScreen;
    private ClosingScreen closingScreen;
    private DilemmaScreen dilemmaScreen;

    // Other game-related variables and methods to track player progress

    @Override
    public void create() {
        introScreen = new IntroScreen(this);
        dilemmaScreen = new DilemmaScreen(this);
        gameScreen = new GameScreen(this);
        closingScreen = new ClosingScreen(this);

        setScreen(introScreen);
    }

    // Methods to switch between screens
    public void showDilemmaScreen() {
        setScreen(dilemmaScreen);
    }

    public void showGameScreen() {
        setScreen(gameScreen);
    }

    public void showClosingScreen() {
        setScreen(closingScreen);
    }

    // Methods to handle player progress and game state

    // Other methods as needed
}
