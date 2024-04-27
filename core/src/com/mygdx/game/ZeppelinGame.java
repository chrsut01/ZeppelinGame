package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Rectangles.Zeppelin;
import com.mygdx.game.Screens.ClosingScreen;
import com.mygdx.game.Screens.DilemmaScreen;
import com.mygdx.game.Screens.IntroScreen;
import com.mygdx.game.Screens.SideScrollerScreen;
import com.mygdx.game.SideScrollers.SideScrollerBulg;

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
        zeppelin = new Zeppelin();

        introScreen = new IntroScreen(this);
       // dilemmaScreen = new DilemmaScreen(this);
        closingScreen = new ClosingScreen(this);

        DilemmaFactory dilemmaFactory = new DilemmaFactory();

        List<Dilemma> dilemmasBulg = DilemmaFactory.loadDilemmasFromJsonFile("assets/JSON_files/sample2.json");
        //  List<Dilemma> dilemmasMed = DilemmaFactory.loadDilemmasFromJson("dilemmas_med.json");
        // List<Dilemma> dilemmasEgypt = DilemmaFactory.loadDilemmasFromJson("dilemmas_egypt.json");

        System.out.println("ZeppelinGame: Dilemmas loaded from JSON files");
        System.out.println("ZeppelinGame: DilemmasBulg: " + dilemmasBulg.toString());

        SideScrollerBulg sideScrollerBulg = new SideScrollerBulg();
       // SideScrollerScreen sideScrollerMed = new SideScrollerMed();
       // SideScrollerScreen sideScrollerEgypt = new SideScrollerEgypt();

        System.out.println("ZeppelinGame: SideScrollerBulg created");

        GameLevel gameLevelBulg = new GameLevel(sideScrollerBulg, dilemmasBulg);
        System.out.println("ZeppelinGame: GameLevelBulg created");
        gameLevels = new ArrayList<>();
        gameLevels.add(gameLevelBulg);
        System.out.println("ZeppelinGame: gameLevels ArrayList: " + gameLevels.toString());
     /*   GameLevel gameLevelMed = new GameLevel(sideScrollerMed, dilemmasMed);
        gameLevels.add(gameLevelMed);
        System.out.println("GameLevels2: " + gameLevels.size());
        GameLevel gameLevelEgypt = new GameLevel(sideScrollerEgypt, dilemmasEgypt);
        gameLevels.add(gameLevelEgypt);
        System.out.println("GameLevels3: " + gameLevels.size());*/

        /*	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		this.setScreen(new MainMenuScreen(this));
	}*/

        // Set the initial screen based on player progress
        if (playerProgress == 0) {
            this.setScreen(introScreen); // Show intro screen if game hasn't started yet
        } else if (playerProgress < gameLevels.size()) {
            // Get the current level and its associated dilemma
            currentLevel = gameLevels.get(playerProgress - 1); // Subtract 1 because list indices start from 0
            Dilemma currentDilemma = currentLevel.getNextDilemma();

            System.out.println("ZeppelinGame: Player's progress = " + playerProgress);
            System.out.println("ZeppelinGame: currentLevel: " + currentLevel);

            // Show the dilemma screen with the current dilemma
            dilemmaScreen = new DilemmaScreen(this, currentDilemma);
            this.setScreen(dilemmaScreen);
           // startSideScroller();
            playerProgress ++;
        } else {
            setScreen(closingScreen); // Show closing screen if all levels are completed
        }
    }

    @Override
    public void render() {
        super.render(); // Call the superclass render method to render the current screen

        handleInput(); // Handle input for the game
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit(); // Exit the game if the ESCAPE key is pressed
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
