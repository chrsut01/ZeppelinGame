package com.mygdx.game.SideScrollers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Rectangles.Zeppelin;
import com.mygdx.game.ZeppelinGame;

public class SideScrollerEgypt extends SideScrollerScreen {
    private static final String tilemapFileName = "tileMaps/MapEgypt.tmx";
    private Texture mapImage;

    public Zeppelin zeppelin;
    private World world;


    public SideScrollerEgypt(ZeppelinGame game) {
        super(tilemapFileName, game);
        this.mapImage = new Texture("ProgMapEgypt.png");
    }

    public void initialize() {
        System.out.println("SideScrollerEgypt initialize() called.");
        super.initialize();
        this.zeppelin = Zeppelin.getInstance();
        this.zeppelin.playEngineSound(0.4f);
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
        setMapImage(mapImage);
    }


    public World getWorld() {
        return world;
    }

    public void dispose(){
        super.dispose();
        mapImage.dispose();
    }
}
