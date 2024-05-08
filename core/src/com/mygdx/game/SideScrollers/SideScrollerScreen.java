package com.mygdx.game.SideScrollers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameLevel;
import com.mygdx.game.Rectangles.Bullet;
import com.mygdx.game.Rectangles.Plane;
import com.mygdx.game.Rectangles.StormCloud;
import com.mygdx.game.Rectangles.Zeppelin;
import com.mygdx.game.TileMapHelper;
import com.mygdx.game.ZeppelinGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygdx.game.Constants.PPM;

public class SideScrollerScreen extends ScreenAdapter {
    private final String tilemapFileName;
    private TileMapHelper tileMapHelper;
    protected SideScrollerScreen sideScrollerScreen;
    private final ZeppelinGame game;
    private GameLevel gameLevel;
    private Zeppelin zeppelin;
    private Plane plane;
    private final List<Plane> planes;
    private Bullet bullet;
    // 250 milliseconds (4 bullets per second)



    private final World world;
    private final Random random;
    private static final int MIN_Y_ANGLE = 0;
    private static final int MAX_Y_ANGLE = 60;
    private OrthographicCamera camera;
    private final SpriteBatch batch;

    private Stage stage;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private Screen introScreen;
    private Screen closingScreen;
    long lastPlaneTime;
    float planeSpawnTimer;

    public static final float MIN_PLANE_SPAWN_TIME = 0.2f;
    public static final float MAX_PLANE_SPAWN_TIME = 10f;

    long lastStormCloudTime = TimeUtils.millis() + (long) (MIN_StormCloud_SPAWN_TIME * 1000);
    float stormCloudSpawnTimer;
    public static final float MIN_StormCloud_SPAWN_TIME = 5f;
    public static final float MAX_StormCloud_SPAWN_TIME = 15f;
    private final ArrayList<StormCloud> stormClouds;

 //   private Texture mapImage;

  //  private float mapWidth;
  //  private float mapHeight;



    public SideScrollerScreen (String tilemapFileName, ZeppelinGame game){
        System.out.println("SideScrollerScreen constructor called");
        this.game = game;
        this.tilemapFileName = tilemapFileName;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0,0), false);
        this.planes = new ArrayList<>();
        this.stormClouds = new ArrayList<>();
        this.random = new Random();
    }

    public void initialize() {
        System.out.println("SideScrollerScreen initialize method called");
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        this.camera.update();
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
    }

    public void render(float delta) {
        this.update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);  // makes screen transparent
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();

        batch.begin();

        zeppelin.render(batch);

        for (Plane plane : planes) {
            plane.render(batch);
        }

        for (StormCloud stormCloud : stormClouds) {
            stormCloud.render(batch);
        }

        for (Bullet bullet : plane.bullets) {
            bullet.render(batch);
        }

      /*  for (Plane plane : planes) {
            for (Bullet bullet : plane.bullets) {
                bullet.render(batch);
            }
        }*/

        // Draw the map at the bottom left corner of the screen
        float mapX = camera.position.x - camera.viewportWidth / 2 + 20;
        float mapY = camera.position.y - camera.viewportHeight / 2 + 20;
       // batch.draw(mapImage, mapX, mapY, mapWidth, mapHeight);

        batch.end();

        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    // This may not be needed.
    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        zeppelin.update();
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

        // Check for collision between zeppelin and planes
        for (Iterator<Plane> iter = planes.iterator(); iter.hasNext(); ) {
            Plane plane = iter.next();
            if (plane.overlaps(zeppelin)) {
                // Handle collision between plane and zeppelin
                // planesHit++;
                plane.planeCrashSound.play();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        plane.planeFlyingSound.stop();
                    }
                }, 0.25F); // 1 second delay
                iter.remove();
            }
        }

        // Check for collision between zeppelin and polygon objects (not planes!) in tilemap
        for (PolygonMapObject polygonMapObject : tileMapHelper.getStaticBody()) {
            if (tileMapHelper.overlapsPolygon(polygonMapObject, zeppelin)) {
                // plane.planeCrashSound.play();
                // Gdx.app.exit();
            }
        }


        // STORM CLOUD SPAWNING TEMPORARILY DISABLED
   /*     // Check if it's time to spawn a cloud
        if (TimeUtils.timeSinceMillis(lastStormCloudTime) > stormCloudSpawnTimer) {
            spawnStormCloud();
            // Generate a new random spawn delay
            stormCloudSpawnTimer = MathUtils.random(MIN_StormCloud_SPAWN_TIME * 10000, MAX_StormCloud_SPAWN_TIME * 30000);
            // Update the last storm cloud spawn time
            lastStormCloudTime = TimeUtils.millis();
        }

        for (StormCloud stormCloud : stormClouds) {
            stormCloud.updatePosition(delta);
        }*/

        // Check if it's time to spawn a bullet
        if (plane.isFacing(zeppelin)) {
            plane.shoot(delta);
          //  plane.updateBullets(delta);
        }
        plane.updateBullets(delta);

            for (Bullet bullet : plane.bullets) {
                bullet.updatePosition(delta);
            }


        // Check if zeppelin reaches a certain x value, then next GameLevel initiated
        if (zeppelin.getX() > 6000) {
            System.out.println("Zeppelin reached the end of the level and called progressToNextLevel() method");
            game.incrementCurrentLevelCount();
            game.progressToNextLevel();
           // game.switchScreen(closingScreen);
           // dispose();
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

    public String getTilemapFileName() {
        return this.tilemapFileName;
    }

    @Override
    public void show() {
        System.out.println("SideScrollerScreen show() method called.");
        this.zeppelin = Zeppelin.getInstance();
    }
    public World getWorld() {
        System.out.println("SideScrollerScreen getWorld() method called.");
        return world;
    }

    public void dispose(){
        orthogonalTiledMapRenderer.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();

        batch.dispose();
        planes.clear();
        stormClouds.clear();
        planes.forEach(Plane::dispose);
        stormClouds.forEach(StormCloud::dispose);
        for (Plane plane : planes) {
            if (plane != null) {
                plane.dispose();
            }
        }
        for (StormCloud stormCloud : stormClouds) {
            if (stormCloud != null) {
                stormCloud.dispose();
            }
        }
        for (Bullet bullet : plane.bullets) {
            if (bullet != null) {
                bullet.dispose();
            }
        }
            zeppelin.dispose();
            Zeppelin.setInstance(null);

    }
}
