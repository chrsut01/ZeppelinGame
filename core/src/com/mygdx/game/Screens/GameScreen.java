package com.mygdx.game.Screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.physics.box2d.World;
import java.util.List;
import com.mygdx.game.GameLevel;
import com.mygdx.game.Entities.Plane;
import com.mygdx.game.Entities.Zeppelin;
import com.mygdx.game.ZeppelinGame;

public class GameScreen extends ScreenAdapter {
    private GameLevel currentLevel;
    private Zeppelin zeppelin;
    private Plane plane;
    private List<Plane> planes;
    private String backgroundImagePath;
    private World world;

    public GameScreen(ZeppelinGame game) {
        this.currentLevel = game.getCurrentLevel();
        this.zeppelin = new Zeppelin();
        this.planes = currentLevel.getPlanes();
        this.backgroundImagePath = currentLevel.getBackgroundImagePath();
        // Initialize other game elements based on level data
    }

    @Override
    public void show() {
        // Load background image, tilemap, and other level-specific data
    }

    @Override
    public void render(float delta) {
        // Render background image, tilemap, zeppelin, obstacles, etc.

        // Render the game
        if (currentLevel != null) {
            // Check if currentLevel is not null before accessing its methods
            List<Plane> planes = currentLevel.getPlanes();
            if (planes != null) {
                // Use planes if it's not null
                // Example usage of planes
                for (Plane plane : planes) {
                    // Do something with each plane
                }
            } else {
                // Handle the case where planes is null
                // For example:
                // System.out.println("Planes list is null");
            }
        } else {
            // Handle the case where currentLevel is null
            // For example:
            // System.out.println("Current level is null");
        }
    }

    public World getWorld() {
        return world;
    }

    public void setLevel(GameLevel gameLevel) {
        this.currentLevel = gameLevel;
    }

    // Other methods as needed

}
