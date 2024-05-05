package com.mygdx.game.SideScrollers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Rectangles.Zeppelin;
import com.mygdx.game.ZeppelinGame;

public class SideScrollerBulg extends SideScrollerScreen {
    private static final String tilemapFileName = "maps/ZepMap1.tmx";
    //private ZeppelinGame game;
    public Zeppelin zeppelin;
    private World world;
    private SpriteBatch batch;

    public SideScrollerBulg(ZeppelinGame game) {
        super(tilemapFileName, game);
    }

    public void initialize() {
        //this.zeppelin = Zeppelin.getInstance();
        System.out.println("SideScrollerBulg initialize() called.");
        super.initialize();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void cameraUpdate() {
        super.cameraUpdate();
    }

    @Override
    public void spawnPlane() {
        super.spawnPlane();
    }

    @Override
    public void spawnStormCloud() {
        super.spawnStormCloud();
    }

    @Override
    public String getTilemapFileName() {
        return tilemapFileName;
    }

    @Override
    public void show() {
        super.show();
        System.out.println("SideScrollerBulg show() method called.");
    }

    public World getWorld() {
        return world;
    }

    public void dispose(){
        super.dispose();
    }
}
