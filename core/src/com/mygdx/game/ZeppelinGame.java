package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.DilemmaStuff.Dilemma;
import com.mygdx.game.DilemmaStuff.DilemmaFactory;
import com.mygdx.game.DilemmaStuff.DilemmaScreen;
import com.mygdx.game.ExtraScreens.ClosingScreen;
import com.mygdx.game.ExtraScreens.IntroScreen;
import com.mygdx.game.SideScrollers.SideScrollerBulg;
import com.mygdx.game.SideScrollers.SideScrollerEgypt;
import com.mygdx.game.SideScrollers.SideScrollerMed;
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

    //  public int playerProgress = 0;

    private List<SideScrollerScreen> sideScrollers;
    private OrthographicCamera camera;
    private TileMapHelper tileMapHelper;

        private static ZeppelinGame instance;
        private ZeppelinGame() {
            // Private constructor to prevent instantiation
        }

        public static ZeppelinGame getInstance() {
            if (instance == null) {
                instance = new ZeppelinGame();
            }
            return instance;
        }

    @Override
    public void create() {
        System.out.println("currentLevelCount at start of game: " + currentLevelCount);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();

        introScreen = new IntroScreen(this);
        closingScreen = new ClosingScreen(this);

        DilemmaFactory dilemmaFactory = new DilemmaFactory();

        List<Dilemma> dilemmasBulg = DilemmaFactory.loadDilemmasFromJsonFile("assets/JSON_files/BulgDilemmas.json");
        List<Dilemma> dilemmasMed = DilemmaFactory.loadDilemmasFromJsonFile("assets/JSON_files/MedDilemmas.json");
        List<Dilemma> dilemmasEgypt = DilemmaFactory.loadDilemmasFromJsonFile("assets/JSON_files/EgyptDilemmas.json");


        SideScrollerBulg sideScrollerBulg = new SideScrollerBulg();
        SideScrollerScreen sideScrollerMed = new SideScrollerMed();
        SideScrollerScreen sideScrollerEgypt = new SideScrollerEgypt();


        GameLevel gameLevelBulg = new GameLevel(sideScrollerBulg, dilemmasBulg);
        System.out.println("ZeppelinGame: GameLevelBulg created: " + gameLevelBulg.toString());
        GameLevel gameLevelMed = new GameLevel(sideScrollerMed, dilemmasMed);
        System.out.println("ZeppelinGame: GameLevelMed created: " + gameLevelMed.toString());
        GameLevel gameLevelEgypt = new GameLevel(sideScrollerEgypt, dilemmasEgypt);
        System.out.println("ZeppelinGame: GameLevelEgypt created: " + gameLevelEgypt.toString());

        gameLevels = new ArrayList<>();

        gameLevels.add(gameLevelBulg);
        gameLevels.add(gameLevelMed);
        gameLevels.add(gameLevelEgypt);

        if (currentLevelCount == 0) {
            setScreen(introScreen);
        }
    }

    // Method for progressing to the next gameLevel until the player has completed all levels, then closingScreen
    public void progressToNextLevel() {
        System.out.println("ZeppelinGame: progressToNextLevel: currentLevelCount = " + currentLevelCount);
        // Check if playerProgress exceeds the bounds of gameLevels
        if (currentLevelCount < gameLevels.size()) {
            System.out.println("ZeppelinGame: gameLevels.size() = " + gameLevels.size());
            // Get the current level based on playerProgress
            currentLevel = gameLevels.get(currentLevelCount);
            System.out.println("ZeppelinGame: progressToNextLevel(): currentLevel: " + currentLevel.toString());
           // dilemmaScreen = new DilemmaScreen(this, currentLevel.getNextDilemma());
            dilemmaScreen = new DilemmaScreen(this, currentLevel.getNextDilemma());
            currentLevelCount++;
            System.out.println("progressToNextLevel: currentLevelCount just incremented to = " + currentLevelCount);
            setScreen(dilemmaScreen);
        } else {
            // If playerProgress exceeds the bounds, show the closing screen
            setScreen(closingScreen);
        }
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

    public GameLevel getCurrentLevel() {
    /*    if (currentLevelCount >= 0 && currentLevelCount < gameLevels.size()) {
            return gameLevels.get(currentLevelCount);
        } else {
            return gameLevels.isEmpty() ? null : gameLevels.get(0);
        }*/
        // Previous version of the method
        System.out.println("ZeppelinGame: getCurrentLevel() yields: " + currentLevel.toString());
        // Ensure that playerProgress does not exceed the bounds of the levels list
        int index = Math.min(currentLevelCount, gameLevels.size() - 1);
        return gameLevels.get(index);
    }
}
