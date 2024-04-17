package com.mygdx.game.Screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.List;
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
        //this.currentLevel = level;
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
    }

    public World getWorld() {
        return world;
    }

    // Other methods as needed

}
