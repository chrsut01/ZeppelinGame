package com.mygdx.game.SideScrollerStuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameLevel;
import com.mygdx.game.Rectangles.Plane;
import com.mygdx.game.Rectangles.StormCloud;
import com.mygdx.game.Rectangles.Zeppelin;
import com.mygdx.game.TileMapHelper;
import com.mygdx.game.ZeppelinGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public class SideScrollerScreen extends ScreenAdapter {
    private String tilemapFileName;
    private TileMapHelper tileMapHelper;
    protected SideScrollerScreen sideScrollerScreen;
    private ZeppelinGame game;
    private GameLevel gameLevel;
    private Zeppelin zeppelin;
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

 //   private Texture mapImage;

  //  private float mapWidth;
  //  private float mapHeight;

    private ArrayList<StormCloud> stormClouds;

    public SideScrollerScreen (String tilemapFileName){
        this.tilemapFileName = tilemapFileName;
    }
    public SideScrollerScreen(){
        this.batch = new SpriteBatch();
       // this.sideScrollerScreen = new SideScrollerScreen();
      //  zeppelin = new Zeppelin();
        this.planes = new ArrayList<>();
        this.stormClouds = new ArrayList<>();

        this.random = new Random();
     //   this.world = new World(new Vector2(0,0), false);
     //   this.box2DDebugRenderer = new Box2DDebugRenderer();
     //   this.tileMapHelper = new TileMapHelper(this,this);
     //   this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();

     //   this.mapImage = new Texture("map-to-afrika.png");
        // Calculate the maximum size based on the desired maximum width or height
     //   mapHeight = Gdx.graphics.getHeight() * 0.3f;
    //    float aspectRatio = (float) mapImage.getHeight() / (float) mapImage.getWidth();
        // Calculate the scaling factor based on the maximum width or height
     //   mapWidth = mapHeight / aspectRatio;

        initialize();
        System.out.println("SideScrollerScreen initiated");
    }

    public void initialize() {
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        this.camera.update();
        this.world = new World(new Vector2(0,0), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this,this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
        System.out.println("Tilemap loaded and setMap() called.");
    }

    // This may not be needed.
    public void update(float delta) {

        // Update the world (physics simulation
        world.step(1/60f,6,2);
        //zeppelin.update();
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        // Check if it's time to spawn a plane
        if (TimeUtils.timeSinceMillis(lastPlaneTime) > planeSpawnTimer) {
            spawnPlane();
            // Generate a new random spawn delay
            planeSpawnTimer = MathUtils.random(MIN_PLANE_SPAWN_TIME * 1000, MAX_PLANE_SPAWN_TIME * 1000);
            // Update the last plane spawn time
            lastPlaneTime = TimeUtils.millis();
        }

        for (Plane plane : planes) {
            plane.updatePosition(delta);
        }

        // Check if it's time to spawn a cloud
        if (TimeUtils.timeSinceMillis(lastStormCloudTime) > stormCloudSpawnTimer) {
            spawnStormCloud();
            // Generate a new random spawn delay
            stormCloudSpawnTimer = MathUtils.random(MIN_StormCloud_SPAWN_TIME * 1000, MAX_StormCloud_SPAWN_TIME * 1000);
            // Update the last storm cloud spawn time
            lastStormCloudTime = TimeUtils.millis();
        }

        for (StormCloud stormCloud : stormClouds) {
            stormCloud.updatePosition(delta);
        }

        // Check if zeppelin reaches a certain x value, then next GameLevel initiated
        if (zeppelin.getX() >= 900) {
            game.progressToNextLevel();
        }
    }

    public void cameraUpdate() {
        int mapWidth = GameConfig.TILEMAP_WIDTH;
        int mapHeight = GameConfig.TILEMAP_HEIGHT;

        float zeppelinX = zeppelin.getX();
        float zeppelinY = zeppelin.getY();

        // Calculate the target camera position to keep the Zeppelin centered
        float targetCameraX = MathUtils.clamp(zeppelinX + zeppelin.getWidth() / 2, camera.viewportWidth / 2, mapWidth - camera.viewportWidth / 2);
        float targetCameraY = MathUtils.clamp(zeppelinY + zeppelin.getHeight() / 2, camera.viewportHeight / 2, mapHeight - camera.viewportHeight / 2);

        camera.position.set(targetCameraX, targetCameraY, 0);

        // Ensure the camera stays within the tilemap boundaries
        camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2, mapWidth - camera.viewportWidth / 2);
        camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2, mapHeight - camera.viewportHeight / 2);

        camera.update();
    }

    public void spawnPlane() {

        float x = camera.position.x + camera.viewportWidth / 2;
        float minY = camera.position.y - camera.viewportHeight / 2;
        float maxY = camera.position.y + camera.viewportHeight / 2;
        float y = MathUtils.random(minY + (camera.viewportHeight / 4), maxY - (camera.viewportHeight / 4)); // Adjusted y-coordinate range
        float middleY = camera.position.y; // Calculate the middle of the screen

        // Determine the yAngle based on the relative position of the plane to the middle of the screen
        int yAngle;
        if (y < middleY) {
            yAngle = random(MIN_Y_ANGLE, MAX_Y_ANGLE);  // Plane starts above the middle of the screen
        } else {
            yAngle = -random(MIN_Y_ANGLE, MAX_Y_ANGLE); // Plane starts below or at the middle of the screen
        }
        plane = new Plane(x, y, yAngle);
        plane.planeFlyingSound.play();
        planes.add(plane);

    }

    public void spawnStormCloud() {
        float x = camera.position.x + camera.viewportWidth / 2;
        float minY = 400;
        float maxY = GameConfig.TILEMAP_HEIGHT - 800;
        float y = MathUtils.random(minY, maxY);

        StormCloud stormCloud = new StormCloud(x, y);
        stormClouds.add(stormCloud);
    }

    @Override
    public void show() {
        // Load background image, tilemap, and other level-specific data
    }

    public String getTilemapFileName() {
        return this.tilemapFileName;
    }

    public void setTilemapFileName(String tilemapFileName) {
        this.tilemapFileName = tilemapFileName;
    }
    public World getWorld() {
        return world;
    }


}