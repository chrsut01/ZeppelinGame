package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.ClosingScreen;
import com.mygdx.game.Screens.DilemmaScreen;
import com.mygdx.game.Screens.SideScrollerScreen;
import com.mygdx.game.Screens.IntroScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZeppelinGame extends Game {

    private List<GameLevel> levels;
    public SpriteBatch batch;
    public BitmapFont font;
    private IntroScreen introScreen;
    private SideScrollerScreen sideScrollerScreen;
    private ClosingScreen closingScreen;
    private DilemmaScreen dilemmaScreen;
    public int playerProgress;
    private int currentLevelIndex;
    public SideScroller SideScrollerBulgaria;
    private List<SideScroller> sideScrollers;
    private OrthographicCamera camera;


    // Other game-related variables and methods to track player progress

    @Override
    public void create() {
        camera = new OrthographicCamera();
        levels = new ArrayList<>();
       // GameLevel level1 = new GameLevel(/* parameters for level 1 */);
        levels.add(new GameLevel(SideScrollerBulgaria, new ArrayList<>()));

        introScreen = new IntroScreen(this);
        dilemmaScreen = new DilemmaScreen(this);
        sideScrollerScreen = new SideScrollerScreen();
        closingScreen = new ClosingScreen(this);

        // Set the initial screen based on player progress
        if (playerProgress == 0) {
            setScreen(introScreen); // Show intro screen if game hasn't started yet
        } else if (playerProgress < levels.size()) {
            // Show the appropriate game screen if there are levels to play
            createSideScrollerScreen(levels.get(playerProgress));
        } else {
            setScreen(closingScreen); // Show closing screen if all levels are completed
        }
    }

    public void createSideScrollerScreen(GameLevel gameLevel) {
        setScreen(new SideScrollerScreen());
    }


    // Methods to switch between screens
    public void showDilemmaScreen() {
        setScreen(dilemmaScreen);
    }

    public void showGameScreen() {
        setScreen(sideScrollerScreen);
    }

    public void showClosingScreen() {
        setScreen(closingScreen);
    }

    public GameLevel getCurrentLevel() {
        // Ensure that playerProgress does not exceed the bounds of the levels list
        int index = Math.min(playerProgress, levels.size() - 1);
        return levels.get(index);
    }

    // Method for progressing to the next level
    public void progressToNextLevel() {
        playerProgress++;
        if (playerProgress >= levels.size()) {
            playerProgress = levels.size() - 1;
        }

        // Set the current screen to the game screen with the next level
       // sideScrollerScreen.setLevel(levels.get(playerProgress));
        setScreen(sideScrollerScreen);
    }
}
