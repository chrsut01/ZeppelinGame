package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.DilemmaStuff.Dilemma;
import com.mygdx.game.DilemmaStuff.DilemmaFactory;
import com.mygdx.game.Rectangles.Zeppelin;
import com.mygdx.game.ExtraScreens.ClosingScreen;
import com.mygdx.game.DilemmaStuff.DilemmaScreen;
import com.mygdx.game.ExtraScreens.IntroScreen;
import com.mygdx.game.SideScrollerStuff.SideScrollerScreen;
import com.mygdx.game.SideScrollerStuff.SideScrollerBulg;

import java.util.ArrayList;
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
    private TileMapHelper tileMapHelper;


    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
      //  zeppelin = new Zeppelin();

        introScreen = new IntroScreen(this);
       // dilemmaScreen = new DilemmaScreen(this);
        closingScreen = new ClosingScreen(this);

        DilemmaFactory dilemmaFactory = new DilemmaFactory();

        List<Dilemma> dilemmasBulg = DilemmaFactory.loadDilemmasFromJsonFile("assets/JSON_files/sample2.json");
        //  List<Dilemma> dilemmasMed = DilemmaFactory.loadDilemmasFromJson("dilemmas_med.json");
        // List<Dilemma> dilemmasEgypt = DilemmaFactory.loadDilemmasFromJson("dilemmas_egypt.json");

     //   System.out.println("ZeppelinGame: Dilemmas loaded from JSON files");
     //   System.out.println("ZeppelinGame: DilemmasBulg: " + dilemmasBulg.toString());

        SideScrollerBulg sideScrollerBulg = new SideScrollerBulg();
       // SideScrollerScreen sideScrollerMed = new SideScrollerMed();
       // SideScrollerScreen sideScrollerEgypt = new SideScrollerEgypt();

     //   System.out.println("ZeppelinGame: SideScrollerBulg created: " + sideScrollerBulg.toString());

        GameLevel gameLevelBulg = new GameLevel(sideScrollerBulg, dilemmasBulg);
     //   System.out.println("ZeppelinGame: GameLevelBulg created: " + gameLevelBulg.toString());
        gameLevels = new ArrayList<>();
        gameLevels.add(gameLevelBulg);
       // System.out.println("ZeppelinGame: gameLevels ArrayList: " + gameLevels.toString());
     /*   GameLevel gameLevelMed = new GameLevel(sideScrollerMed, dilemmasMed);
        gameLevels.add(gameLevelMed);
        System.out.println("GameLevels2: " + gameLevels.size());
        GameLevel gameLevelEgypt = new GameLevel(sideScrollerEgypt, dilemmasEgypt);
        gameLevels.add(gameLevelEgypt);
        System.out.println("GameLevels3: " + gameLevels.size());*/
        if (playerProgress == 0) {
            setScreen(introScreen);
        }
    }

    // Method for progressing to the next gameLevel until the player has completed all levels, then closingScreen
    public void progressToNextLevel() {
        playerProgress++;
        System.out.println("ZeppelinGame: Player's progress = " + playerProgress);
        // Check if playerProgress exceeds the bounds of gameLevels
        if (playerProgress <= gameLevels.size()) {
            System.out.println("ZeppelinGame: Player's progress <= gameLevels.size() = " + gameLevels.size());
            // Get the current level based on playerProgress
            currentLevel = gameLevels.get(playerProgress - 1);
            dilemmaScreen = new DilemmaScreen(this, currentLevel.getNextDilemma());
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
        // Ensure that playerProgress does not exceed the bounds of the levels list
        int index = Math.min(playerProgress, gameLevels.size() - 1);
        return gameLevels.get(index);
    }

  /*  public void showNextDilemma() {
        Dilemma nextDilemma = getCurrentLevel().getNextDilemma();
        if (nextDilemma != null) {
            // Display the next dilemma...
        } else {
            // No more dilemmas in this level
        }
    }*/
}
