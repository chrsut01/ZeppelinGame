package com.mygdx.game.SideScrollerStuff;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameLevel;
import com.mygdx.game.Rectangles.Plane;
import com.mygdx.game.Rectangles.StormCloud;
import com.mygdx.game.Rectangles.Zeppelin;
import com.mygdx.game.TileMapHelper;
import com.mygdx.game.ZeppelinGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SideScrollerBulg extends SideScrollerScreen {
    private static final String tilemapFileName = "maps/ZepMap1.tmx";

    public Zeppelin zeppelin;

    private TileMapHelper tileMapHelper;
    protected SideScrollerScreen sideScrollerScreen;
    private ZeppelinGame game;
    private GameLevel gameLevel;
    private Plane plane;
    private List<Plane> planes;
    private World world;
    private Random random;
    private static final int MIN_Y_ANGLE = 0;
    private static final int MAX_Y_ANGLE = 60;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Stage stage;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    long lastPlaneTime;
    float planeSpawnTimer;

    public static final float MIN_PLANE_SPAWN_TIME = 0.2f;
    public static final float MAX_PLANE_SPAWN_TIME = 10f;

    long lastStormCloudTime = TimeUtils.millis() + (long) (MIN_StormCloud_SPAWN_TIME * 1000);
    float stormCloudSpawnTimer;
    public static final float MIN_StormCloud_SPAWN_TIME = 5f;
    public static final float MAX_StormCloud_SPAWN_TIME = 15f;
    private ArrayList<StormCloud> stormClouds;

    //   private Texture mapImage;
    //  private float mapWidth;
    //  private float mapHeight;


    public SideScrollerBulg() {
        super(tilemapFileName);
       // initialize();
    }

    public void initialize() {
        this.zeppelin = Zeppelin.getInstance();
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


}
