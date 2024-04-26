package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Rectangles.Zeppelin;
import com.mygdx.game.Screens.ClosingScreen;
import com.mygdx.game.Screens.DilemmaScreen;
import com.mygdx.game.Screens.SideScrollerScreen;
import com.mygdx.game.Screens.IntroScreen;
import com.mygdx.game.SideScrollers.SideScrollerBulg;
import com.mygdx.game.SideScrollers.SideScrollerEgypt;
import com.mygdx.game.SideScrollers.SideScrollerMed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZeppelinGame extends Game {

    private GameLevel currentLevel;
    private List<GameLevel> gameLevels;
    private Zeppelin zeppelin;
    public SpriteBatch batch;
    public BitmapFont font;
    private IntroScreen introScreen;
    private SideScrollerScreen sideScrollerScreen;
    private ClosingScreen closingScreen;
    private DilemmaScreen dilemmaScreen;

    public int playerProgress = 0;
    private int currentLevelIndex;
    private List<SideScrollerScreen> sideScrollers;
    private OrthographicCamera camera;

    @Override
    public void create() {
        System.out.println("create method called in ZeppelinGame");
        camera = new OrthographicCamera();

        zeppelin = new Zeppelin();

        introScreen = new IntroScreen(this);
        dilemmaScreen = new DilemmaScreen(this);
        closingScreen = new ClosingScreen(this);

        gameLevels = new ArrayList<>();

        System.out.println("gameLevels ArrayList created");

        // Create instances of side scroller screens
        SideScrollerScreen sideScrollerBulg = new SideScrollerBulg();
       // SideScrollerScreen sideScrollerMed = new SideScrollerMed();
       // SideScrollerScreen sideScrollerEgypt = new SideScrollerEgypt();

        System.out.println("SideScrollerScreens created");

        // Load dilemmas for each level from JSON files
        List<Dilemma> dilemmasBulg = DilemmaFactory.loadDilemmasFromJson("sample.json");
      //  List<Dilemma> dilemmasMed = DilemmaFactory.loadDilemmasFromJson("dilemmas_med.json");
       // List<Dilemma> dilemmasEgypt = DilemmaFactory.loadDilemmasFromJson("dilemmas_egypt.json");

        System.out.println("Dilemmas loaded from JSON files");

        GameLevel gameLevelBulg = new GameLevel(sideScrollerBulg, dilemmasBulg);
        gameLevels.add(gameLevelBulg);
        System.out.println("GameLevels1: " + gameLevels.toString());
     /*   GameLevel gameLevelMed = new GameLevel(sideScrollerMed, dilemmasMed);
        gameLevels.add(gameLevelMed);
        System.out.println("GameLevels2: " + gameLevels.size());
        GameLevel gameLevelEgypt = new GameLevel(sideScrollerEgypt, dilemmasEgypt);
        gameLevels.add(gameLevelEgypt);
        System.out.println("GameLevels3: " + gameLevels.size());*/

        // Set the initial screen based on player progress
        if (playerProgress == 0) {
            setScreen(introScreen); // Show intro screen if game hasn't started yet
        } else if (playerProgress < gameLevels.size()) {
            // Get the current level and its associated dilemma
            GameLevel currentLevel = gameLevels.get(playerProgress - 1); // Subtract 1 because list indices start from 0
            Dilemma currentDilemma = currentLevel.getNextDilemma();

            // Show the dilemma screen with the current dilemma
            dilemmaScreen.setDilemma(currentDilemma);
            setScreen(dilemmaScreen);
           // startSideScroller();
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

  /*  public void startSideScroller() {
        SideScrollerScreen currentSideScroller = getCurrentLevel().getSideScroller();
        // Start the side scroller...
    }*/

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
