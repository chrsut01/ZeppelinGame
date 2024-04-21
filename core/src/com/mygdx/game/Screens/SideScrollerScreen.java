package com.mygdx.game.Screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;
import com.mygdx.game.SideScroller;
import com.mygdx.game.Entities.Plane;
import com.mygdx.game.Entities.Zeppelin;
import com.mygdx.game.TileMapHelper;
import com.mygdx.game.ZeppelinGame;

public class SideScrollerScreen extends ScreenAdapter {
    private String tilemapFileName;
    private TileMapHelper tileMapHelper;
    private SideScroller currentLevel;
    private ZeppelinGame game;
    private Zeppelin zeppelin;
    private Plane plane;
    private List<Plane> planes;
    private String tilmapFileName;
    private World world;

    public SideScrollerScreen(){
        this.planes = new ArrayList<>();

        tileMapHelper = new TileMapHelper(this);
        //currentLevel = game.getCurrentLevel();
        if (currentLevel != null) {
            this.tilemapFileName = currentLevel.getTilemapFileName(); // Update tilemapFileName
            tileMapHelper.setupMap(this.tilemapFileName);
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

    public void setLevel(SideScroller gameLevel) {
        this.currentLevel = gameLevel;
        if (tileMapHelper != null && currentLevel != null) {
            tileMapHelper.setupMap(currentLevel.getTilemapFileName());
        }
    }
}
