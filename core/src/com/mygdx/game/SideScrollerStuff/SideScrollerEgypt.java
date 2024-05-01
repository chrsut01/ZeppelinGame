package com.mygdx.game.SideScrollerStuff;

public class SideScrollerEgypt extends SideScrollerScreen {
    private static final String tilemapFileName = "ZepMap1.tmx";
    public SideScrollerEgypt() {
        super(tilemapFileName);
        this.sideScrollerScreen = this;
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
}
