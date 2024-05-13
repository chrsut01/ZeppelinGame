package com.mygdx.game.SideScrollers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Rectangles.Zeppelin;
import com.mygdx.game.ZeppelinGame;

public class SideScrollerEgypt extends SideScrollerScreen {
    private static final String tilemapFileName = "maps/MapTurkey.tmx";

    public Zeppelin zeppelin;
    private Rectangle zeppelinHitBox;
    private World world;

    public SideScrollerEgypt(ZeppelinGame game) {
        super(tilemapFileName, game);

    }

    public void initialize() {
        System.out.println("SideScrollerEgypt initialize() called.");
        super.initialize();
        this.zeppelin = Zeppelin.getInstance();
        this.zeppelin.playEngineSound(1.5f);
      //  this.zeppelinHitBox = new Rectangle(zeppelin.getX(), zeppelin.getY(), zeppelin.getWidth(), zeppelin.getHeight());

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
        System.out.println("SideScrollerEgypt show() method called.");
    }

    public World getWorld() {
        return world;
    }

    public void dispose(){
        super.dispose();
    }
}
