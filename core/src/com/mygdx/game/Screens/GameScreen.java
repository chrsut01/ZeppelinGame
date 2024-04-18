package com.mygdx.game.Screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;
import com.mygdx.game.GameLevel;
import com.mygdx.game.Entities.Plane;
import com.mygdx.game.Entities.Zeppelin;
import com.mygdx.game.TileMapHelper;
import com.mygdx.game.ZeppelinGame;

public class GameScreen extends ScreenAdapter {
    private TileMapHelper tileMapHelper;
    private GameLevel currentLevel;
    private Zeppelin zeppelin;
    private Plane plane;
    private List<Plane> planes;
    private String tilmapFileName;
    private World world;

    public GameScreen(ZeppelinGame game){
        //this.zeppelin = new Zeppelin();
        this.planes = new ArrayList<>();

        tileMapHelper = new TileMapHelper(this);
        currentLevel = game.getCurrentLevel();
        if (currentLevel != null) {
            tileMapHelper.setupMap(currentLevel.getTilemapFileName());
        } else {
            tileMapHelper.setupMap("ZepMap1.tmx");
        }
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

    public void setLevel(GameLevel gameLevel) {
        this.currentLevel = gameLevel;
        if (tileMapHelper != null && currentLevel != null) {
            tileMapHelper.setupMap(currentLevel.getTilemapFileName());
        }
    }
}
