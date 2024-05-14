package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.DilemmaStuff.Dilemma;
import com.mygdx.game.DilemmaStuff.DilemmaFactory;
import com.mygdx.game.DilemmaStuff.DilemmaScreen;
import com.mygdx.game.ExtraScreens.ClosingScreen;
import com.mygdx.game.ExtraScreens.IntroScreen;
import com.mygdx.game.SideScrollers.SideScrollerEgypt;
import com.mygdx.game.SideScrollers.SideScrollerScreen;

import java.util.ArrayList;
import java.util.List;

public class ZeppelinGame extends Game {
    private GameLevel currentLevel;
    private List<GameLevel> gameLevels;
    private int currentLevelCount = 0;
    public SpriteBatch batch;
    public BitmapFont font;
    private IntroScreen introScreen;
    private SideScrollerScreen sideScrollerScreen;
    private ClosingScreen closingScreen;
    private DilemmaScreen dilemmaScreen;

    private List<SideScrollerScreen> sideScrollers;
    private OrthographicCamera camera;
    private TileMapHelper tileMapHelper;
    private Screen currentScreen;
    private boolean isProgressingToNextLevel = false;
    public int health = 100;

        private static ZeppelinGame instance;
        private ZeppelinGame() {
            // Private constructor to prevent instantiation
        }

        public static ZeppelinGame getInstance() {
            if (instance == null) {
                instance = new ZeppelinGame();
            }
            System.out.println("ZeppelinGame: getInstance() called. Returning instance: " + instance);
            return instance;
        }

    @Override
    public void create() {
        System.out.println("ZeppelinGame create() called. currentLevelCount at start of game: " + currentLevelCount);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();

        introScreen = new IntroScreen(this);
        closingScreen = new ClosingScreen(this);

        DilemmaFactory dilemmaFactory = new DilemmaFactory();

       // List<Dilemma> dilemmasBulg = DilemmaFactory.loadDilemmasFromJsonFile("assets/JSON_files/BulgDilemmas.json");
       // List<Dilemma> dilemmasMed = DilemmaFactory.loadDilemmasFromJsonFile("assets/JSON_files/MedDilemmas.json");
        List<Dilemma> dilemmasEgypt = DilemmaFactory.loadDilemmasFromJsonFile("assets/JSON_files/EgyptDilemmas.json");
      //  List<Dilemma> dilemmasSudan = DilemmaFactory.loadDilemmasFromJsonFile("assets/JSON_files/EgyptDilemmas.json");


       // SideScrollerBulg sideScrollerBulg = new SideScrollerBulg(this);
       // SideScrollerScreen sideScrollerMed = new SideScrollerMed(this);
        SideScrollerScreen sideScrollerEgypt = new SideScrollerEgypt(this);
      //  SideScrollerScreen sideScrollerSudan = new SideScrollerSudan(this);

    //    GameLevel gameLevelBulg = new GameLevel(sideScrollerBulg, dilemmasBulg);
       // System.out.println("ZeppelinGame: GameLevelBulg created: " + gameLevelBulg.toString());
    //    GameLevel gameLevelMed = new GameLevel(sideScrollerMed, dilemmasMed);
       // System.out.println("ZeppelinGame: GameLevelMed created: " + gameLevelMed.toString());
        GameLevel gameLevelEgypt = new GameLevel(sideScrollerEgypt, dilemmasEgypt);
       // System.out.println("ZeppelinGame: GameLevelEgypt created: " + gameLevelEgypt.toString());
    //    GameLevel gameLevelSudan = new GameLevel(sideScrollerSudan, dilemmasSudan);

        gameLevels = new ArrayList<>();

    //    gameLevels.add(gameLevelBulg);
    //    gameLevels.add(gameLevelMed);
        gameLevels.add(gameLevelEgypt);
     //   gameLevels.add(gameLevelSudan);

        setScreen(introScreen);

    }
 /*   public void progressToNextLevel() {
        System.out.println("ZeppelinGame: progressToNextLevel method called: currentLevelCount = " + currentLevelCount);

        if (currentLevelCount < gameLevels.size()) {
            GameLevel currentLevel = gameLevels.get(currentLevelCount);
            Dilemma nextDilemma = currentLevel.getNextDilemma();
            if (nextDilemma != null) {
                DilemmaScreen dilemmaScreen = currentLevel.getCurrentDilemmaScreen();
                if (dilemmaScreen == null) {
                    dilemmaScreen = DilemmaScreen.getInstance(this, nextDilemma);
                 //   dilemmaScreen.initializeUI();
                    currentLevel.setCurrentDilemmaScreen(dilemmaScreen);
                } else {
                    dilemmaScreen.setDilemma(nextDilemma);
                }
                setScreen(dilemmaScreen);
            } else {
                SideScrollerScreen sideScrollerScreen = currentLevel.getSideScroller();
                setScreen(sideScrollerScreen);
                currentLevelCount++;
            }
        } else {
            setScreen(closingScreen);
        }
    }*/

    // Method for progressing to the next gameLevel until the player has completed all levels, then closingScreen
    public void progressToNextLevel() {
        if (!isProgressingToNextLevel) {
            // Set the flag to indicate that progression is in progress
            isProgressingToNextLevel = true;
        System.out.println("ZeppelinGame: progressToNextLevel method called: currentLevelCount = " + currentLevelCount);
        if (currentLevelCount < gameLevels.size()) {
            currentLevel = gameLevels.get(currentLevelCount);
            System.out.println("ZeppelinGame: progressToNextLevel: currentLevel = " + currentLevel.toString());
            dilemmaScreen = DilemmaScreen.getInstance(this, currentLevel.getNextDilemma());
            switchScreen(dilemmaScreen);
            isProgressingToNextLevel = false;
        }
        } else {
            switchScreen(closingScreen);
        }
    }

    public void incrementCurrentLevelCount() {
        currentLevelCount++;
        System.out.println("ZeppelinGame: incrementCurrentLevelCount: currentLevelCount just incremented to = " + currentLevelCount);
    }
    public GameLevel getCurrentLevel() {
        System.out.println("ZeppelinGame: getCurrentLevel() yields: " + currentLevel.toString());
        return currentLevel;
    }

    public void switchScreen(Screen newScreen) {
        System.out.println("ZeppelinGame: switchScreen method called.");
        if(currentScreen != null) {
            System.out.println("ZeppelinGame: switchScreen: currentScreen disposed.");
            System.out.println("currentScreen = " + currentScreen);
            currentScreen.dispose();
        }
        System.out.println("ZeppelinGame: switchScreen: newScreen set.");
        System.out.println("newScreen = " + newScreen);
        setScreen(newScreen);
        currentScreen = newScreen;
    }

    @Override
    public void render() {
        super.render();
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit(); // Exit the game if the ESCAPE key is pressed
        }
    }
    public void dispose(){
        batch.dispose();
        font.dispose();
    }
}
