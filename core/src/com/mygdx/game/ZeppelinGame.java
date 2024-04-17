package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.ClosingScreen;
import com.mygdx.game.Screens.DilemmaScreen;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Screens.IntroScreen;

import java.util.ArrayList;
import java.util.List;

public class ZeppelinGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    private IntroScreen introScreen;
    private GameScreen gameScreen;
    private ClosingScreen closingScreen;
    private DilemmaScreen dilemmaScreen;
    public int playerProgress;
    public GameLevel level1;
    private List<GameLevel> levels;
    private int currentLevelIndex;

    // Other game-related variables and methods to track player progress

    @Override
    public void create() {
        levels = new ArrayList<>();
        GameLevel level1 = new GameLevel(/* parameters for level 1 */);
        levels.add(level1);

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

    public GameLevel getCurrentLevel() {
        if (playerProgress == 0) {
            // If the game has just started, return the first level
            return levels.get(0);
        } else {
            // Increment the current level index to get the next level
            currentLevelIndex++;
            // Ensure that the currentLevelIndex does not exceed the number of levels
            if (currentLevelIndex >= levels.size()) {
                // Reset the index to the last level if it exceeds the size
                currentLevelIndex = levels.size() - 1;
            }
            return levels.get(currentLevelIndex);
        }
    }

    // Method for progressing to the next level
    public void progressToNextLevel() {
        playerProgress++;

        // Check if the current level index exceeds the number of levels
        if (playerProgress >= levels.size()) {
            // If it does, set the index to the last level
            playerProgress = levels.size() - 1;
        }

        // Set the current screen to the game screen with the next level
        gameScreen.setLevel(levels.get(playerProgress));
        setScreen(gameScreen);
    }

    // Methods to handle player progress and game state

    // Other methods as needed
}
