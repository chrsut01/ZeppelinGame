package com.mygdx.game.SideScrollers;

import com.mygdx.game.Screens.SideScrollerScreen;

public class SideScrollerBulg extends SideScrollerScreen {
    private static final String tilemapFileName = "ZepMap1.tmx";
    public SideScrollerBulg() {
        super(tilemapFileName);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
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
}
