package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.ClosingScreen;
import com.mygdx.game.Screens.DilemmaScreen;
import com.mygdx.game.Screens.SideScrollerScreen;
import com.mygdx.game.Screens.IntroScreen;
import com.mygdx.game.SideScrollers.SideScrollerBulg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZeppelinGame extends Game {

    private GameLevel currentLevel;
    private List<GameLevel> gameLevels;
    public SpriteBatch batch;
    public BitmapFont font;
    private IntroScreen introScreen;
    private SideScrollerScreen sideScrollerScreen;
    private ClosingScreen closingScreen;
    private DilemmaScreen dilemmaScreen;

    public int playerProgress = 0;
    private int currentLevelIndex;
    //public SideScrollerScreen SideScrollerBulg;
    private List<SideScrollerScreen> sideScrollers;
    private OrthographicCamera camera;


    // Other game-related variables and methods to track player progress

    @Override
    public void create() {
        camera = new OrthographicCamera();
        gameLevels = new ArrayList<>();

        introScreen = new IntroScreen(this);
        dilemmaScreen = new DilemmaScreen(this);
        closingScreen = new ClosingScreen(this);

        SideScrollerScreen sideScrollerBulg = new SideScrollerBulg();
        currentLevel = new GameLevel(sideScrollerBulg, new ArrayList<>());
        gameLevels.add(currentLevel);

        // Set the initial screen based on player progress
        if (playerProgress == 0) {
            setScreen(introScreen); // Show intro screen if game hasn't started yet
        } else if (playerProgress < gameLevels.size()) {
            // Show the appropriate game screen if there are levels to play
            startSideScroller();
            playerProgress ++;
        } else {
            setScreen(closingScreen); // Show closing screen if all levels are completed
        }
    }


    public void showNextDilemma() {
        Dilemma nextDilemma = getCurrentLevel().getNextDilemma();
        if (nextDilemma != null) {
            // Display the next dilemma...
        } else {
            // No more dilemmas in this level
        }
    }

    public void startSideScroller() {
        SideScrollerScreen currentSideScroller = getCurrentLevel().getSideScroller();
        // Start the side scroller...
    }

    public GameLevel getCurrentLevel() {
        // Ensure that playerProgress does not exceed the bounds of the levels list
        int index = Math.min(playerProgress, gameLevels.size() - 1);
        return gameLevels.get(index);
    }

     // Method for progressing to the next level
    public void progressToNextLevel() {
        playerProgress++;
        if (playerProgress >= gameLevels.size()) {
            playerProgress = gameLevels.size() - 1;
        }

        // Set the current screen to the game screen with the next level
       // sideScrollerScreen.setLevel(levels.get(playerProgress));
        setScreen(sideScrollerScreen);
    }

    public void showClosingScreen() {
        setScreen(closingScreen);
    }
}
