package com.mygdx.game.SideScrollers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameConfig;
import com.mygdx.game.Rectangles.Plane;
import com.mygdx.game.Rectangles.Zeppelin;
import com.mygdx.game.ZeppelinGame;

public class SideScrollerTurkey extends SideScrollerScreen {
    private static final String tilemapFileName = "tileMaps/MapTurkey.tmx";
    public Zeppelin zeppelin;
    private World world;
    private SpriteBatch batch;

    public SideScrollerTurkey(ZeppelinGame game) {
        super(tilemapFileName, game);
        this.mapImage = new Texture("ProgMapTurkey.png");

    }

    public void initialize() {
        System.out.println("SideScrollerTurkey initialize() called.");
        super.initialize();
        this.zeppelin = Zeppelin.getInstance();
        this.zeppelin.playEngineSound(0.2f);
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
        // Override to make planes fly horizontally
        float x = camera.position.x + camera.viewportWidth / 2;
        float minY = camera.position.y - camera.viewportHeight / 2;
        float maxY = camera.position.y + camera.viewportHeight / 2;
        float y = MathUtils.random(minY + (camera.viewportHeight / 4), maxY - (camera.viewportHeight / 4));

        // Set yAngle to 0 to make the plane fly straight horizontally
        int yAngle = 0;

        // Adjust the y coordinate if needed
        if (y > GameConfig.TILEMAP_HEIGHT - 1000) {
            y = GameConfig.TILEMAP_HEIGHT - 1000;
        }

        plane = new Plane(x, y, yAngle);
        plane.planeFlyingSound.play();
        plane.canShoot = false;
        planes.add(plane);
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
    }
}
