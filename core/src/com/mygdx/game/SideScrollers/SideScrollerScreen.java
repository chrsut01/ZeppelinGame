package com.mygdx.game.SideScrollers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private static int highAltitude = 1800;
    private static float highAltitudeStartTime = 0;
    private static float MAX_HIGH_ALTITUDE_TIME = 1f;
    private boolean showHighAltitudeWarning = false;
    private Sound altitudeAlarmSound;
    private Timer healthDecreaseTimer;
    protected Plane plane;
    protected final List<Plane> planes;
    private Bullet bullet;
    private boolean zepCrashSoundPlayed = false;
    private final World world;
    private final Random random;
    private static final int MIN_Y_ANGLE = 0;
    private static final int MAX_Y_ANGLE = 60;
    protected OrthographicCamera camera;
    protected SpriteBatch batch;

    private Stage stage;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
 //   private Screen IntroScreen;
 //   private Screen ClosingScreen;
    private Screen GameOverScreen;
    long lastPlaneTime;
    float planeSpawnTimer;
    private static final float PLANE_SPAWN_DELAY = 2f; // Delay in seconds before planes can spawn
    private static final float STORMCLOUD_SPAWN_DELAY = 3f; // Delay in seconds before storm clouds can spawn
    private long screenStartTime;
    public static final float MIN_PLANE_SPAWN_TIME = 0.5f;
    public static final float MAX_PLANE_SPAWN_TIME = 4f;

    private StormCloud stormCloud;
    private final ArrayList<StormCloud> stormClouds;
    long lastStormCloudTime = TimeUtils.millis() + (long) (MIN_StormCloud_SPAWN_TIME * 1000);
    float stormCloudSpawnTimer;
    public static final float MIN_StormCloud_SPAWN_TIME = 5f;
    public static final float MAX_StormCloud_SPAWN_TIME = 15f;

    protected Texture mapImage;
    protected float mapWidth;
    protected float mapHeight;


    public SideScrollerScreen (String tilemapFileName, ZeppelinGame game){
        System.out.println("SideScrollerScreen constructor called");
        this.game = game;
        this.tilemapFileName = tilemapFileName;
        this.batch = game.batch;
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
        game.font = new BitmapFont();
        this.altitudeAlarmSound = Gdx.audio.newSound(Gdx.files.internal("alarmSound.mp3"));
    }

    public void render(float delta) {
        this.update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);  // makes screen transparent
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();

        batch.begin();

        if (showHighAltitudeWarning) {
            showHighAltitudeWarning();
        }

        zeppelin.render(batch);

        for (Plane plane : planes) {
            plane.render(batch);
        }

        if (plane != null && plane.bullets != null){
        for (Bullet bullet : plane.bullets) {
            bullet.render(batch);
            }
        }

        for (StormCloud stormCloud : stormClouds) {
            stormCloud.render(batch);
        }

        zeppelin.renderFlicker(batch);

        game.font.draw(game.batch, "Health points: " + game.health, camera.position.x - camera.viewportWidth / 2 + 25, camera.position.y + 40);
        game.font.getData().setScale(2f);

        renderMapImage();

        batch.end();

        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        zeppelin.update();
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        // Check if it is time to spawn a plane
        float elapsedTime = TimeUtils.nanoTime() - screenStartTime;
        float elapsedTimeSeconds = elapsedTime / 1_000_000_000.0f;
        // Check if it's time to spawn a plane
        if (elapsedTimeSeconds > PLANE_SPAWN_DELAY) {
            if (TimeUtils.timeSinceMillis(lastPlaneTime) > planeSpawnTimer) {
                spawnPlane();
                // Generate a new random spawn delay
                planeSpawnTimer = MathUtils.random(MIN_PLANE_SPAWN_TIME * 1000, MAX_PLANE_SPAWN_TIME * 1000);
                // Update the last plane spawn time
                lastPlaneTime = TimeUtils.millis();
            }
        }

        for (Plane plane : planes) {
            plane.updatePosition(delta);
        }

        // Check for collision between zeppelin and planes
        for (Iterator<Plane> iter = planes.iterator(); iter.hasNext(); ) {
            Plane plane = iter.next();
            if (plane.overlaps(zeppelin)) {
                plane.planeCrashSound.play();
                game.health -= 10;

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        plane.planeFlyingSound.stop();
                        plane.setCanShoot(false);
                    }
                }, 0.25F);
                iter.remove();
            } else {
                if (plane.isFacing(zeppelin)) {
                    plane.shootBullets(delta);
                }
            }
        }

        // Check for collision between zeppelin and polygon objects (not planes!) in tilemap
        for (PolygonMapObject polygonMapObject : tileMapHelper.getStaticBody()) {
            if (tileMapHelper.overlapsPolygon(polygonMapObject, zeppelin) && !isZepCrashSoundPlayed()) {
                System.out.println("zeppelin getY() = " + zeppelin.getY() + " zeppelin getX() = " + zeppelin.getX());
                plane.planeCrashSound.play();
                zeppelin.playCrashSound();
                setZepCrashSoundPlayed(true);
                // so that zeppelin freezes in place
                zeppelin.xSpeed = 0;
                zeppelin.ySpeed = 0;
                game.health = 0;
            }
        }

        // Check if it's time to spawn a cloud
        elapsedTimeSeconds = elapsedTime / 1_000_000_000.0f;
        if (elapsedTimeSeconds > STORMCLOUD_SPAWN_DELAY) {
            if (TimeUtils.timeSinceMillis(lastStormCloudTime) > stormCloudSpawnTimer) {
                spawnStormCloud();
                // Generate a new random spawn delay
                stormCloudSpawnTimer = MathUtils.random(MIN_StormCloud_SPAWN_TIME * 2000, MAX_StormCloud_SPAWN_TIME * 3000);
                // Update the last storm cloud spawn time
                lastStormCloudTime = TimeUtils.millis();
            }
        }

        for (StormCloud stormCloud : stormClouds) {
            stormCloud.updatePosition(delta);
        }

        // Check if zeppelin hits the stormCloudBounds
        for (Iterator<StormCloud> iter = stormClouds.iterator(); iter.hasNext(); ) {
            StormCloud stormCloud = iter.next();
            if (stormCloud.overlaps(zeppelin) && !stormCloud.isLightningSoundPlayed()) {
                zeppelin.showFlicker = true;
                zeppelin.zeppelinFlicker(2.5f);
                stormCloud.lightningStrikeSound.play(15.0f);
                stormCloud.setLightningSoundPlayed(true);
                game.health -= 20;
            }
        }

        if (plane != null && plane.canShoot && plane.bullets != null) {
            for (Bullet bullet : plane.bullets) {
                bullet.updatePosition(delta);
            }
        }


        // Check if bullet hits the zeppelin
        if (plane != null && plane.bullets != null) {
            for (Iterator<Bullet> iter = plane.bullets.iterator(); iter.hasNext(); ) {
                Bullet bullet = iter.next();
                if (bullet.overlaps(zeppelin)) {
                    bullet.bulletHitSound.play(0.2f);
                    bullet.setBulletHitSound(true);
                    game.health -= 1;
                    iter.remove();
                }
            }
        }

        // Check if zeppelin is flying too high (for too long)
        if (zeppelin.getY() > highAltitude) {
            highAltitudeStartTime += Gdx.graphics.getDeltaTime();
            if (highAltitudeStartTime > MAX_HIGH_ALTITUDE_TIME && !showHighAltitudeWarning) {
                showHighAltitudeWarning = true;
                highAltitudeStartTime = 0;
                scheduleWarningBlinking();
                altitudeAlarmSound.play(0.5f);
                startHealthDecreaseTimer();
            }
        } else {
            showHighAltitudeWarning = false;
            highAltitudeStartTime = 0;
            altitudeAlarmSound.stop();

            if (healthDecreaseTimer != null) {
                healthDecreaseTimer.stop();
                healthDecreaseTimer = null;
            }
        }

        // Check if the game health is zero and schedule the game over screen switch
        if (game.health <= 0) {
            game.health = 0;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    System.out.println("Health points reached zero. Switching to GameOverScreen.");
                    game.switchScreen(GameOverScreen);
                    dispose();
                }
            }, 2); // 2 seconds delay
        }


        // Check if zeppelin reaches a certain x value, then next GameLevel initiated
        if (zeppelin.getX() > 8000) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    System.out.println("SideScrollerScreen: Zeppelin reached the end of the screen. Progressing to next level.");
                    game.incrementCurrentLevelCount();
                    game.progressToNextLevel();
                    dispose();
                }
            }, 2);
        }
    }

    private boolean showWarning = true;
    private void showHighAltitudeWarning() {
        if (showWarning){
        String warning = "Højde advarsel!\nBesætningen bliver svimmel!\nLavere højde hurtigt!";
        game.font.draw(batch, warning, camera.position.x - camera.viewportWidth / 2 + 30, camera.position.y + camera.viewportHeight / 2 - 30);
        game.font.getData().setScale(2f);
        }
    }

    // Method to toggle warning visibility
    private void toggleWarningVisibility() {
        showWarning = !showWarning;
    }

    // Schedule a timer to toggle warning visibility every second
    private void scheduleWarningBlinking() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                toggleWarningVisibility();
            }
        }, 0, 0.5f); // Toggle every...
    }

    // Method to start the timer for decreasing health at high altitude
    private void startHealthDecreaseTimer() {
        healthDecreaseTimer = new Timer();
        healthDecreaseTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                // Decrease health points every second
                game.health -= 1; // Adjust the amount as needed
            }
        }, 1, 1); // Start after 1 second and repeat every 1 second
    }

    public boolean isZepCrashSoundPlayed() {
        return zepCrashSoundPlayed;
    }

    public void setZepCrashSoundPlayed(boolean played) {
        this.zepCrashSoundPlayed = played;
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
        // to keep planes below a certain altitude
        if (y > GameConfig.TILEMAP_HEIGHT - 1000) {
            y = GameConfig.TILEMAP_HEIGHT - 1000; // Adjust x coordinate if it's beyond the limit
        }
        plane = new Plane(x, y, yAngle);
        plane.planeFlyingSound.play(0.2f);
        planes.add(plane);
    }

    public void spawnStormCloud() {
        float x = camera.position.x + camera.viewportWidth / 2;
        float minY = 400;
        float maxY = GameConfig.TILEMAP_HEIGHT - 800;
        float y = MathUtils.random(minY, maxY);

        stormCloud = new StormCloud(x, y);
        stormClouds.add(stormCloud);

        final float delayBeforeFlicker = MathUtils.random(3f, 3.5f);  // Random delay before starting flickering
        final float totalFlickerDuration = MathUtils.random(3.5f, 5f);  // Random total duration for flickering

        // Schedule a task to start flickering after the random delay
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                stormCloud.flickerLightning(totalFlickerDuration);
            }
        }, delayBeforeFlicker);
    }

    protected void renderMapImage() {
        if (mapImage != null) {
            float mapX = camera.position.x - camera.viewportWidth / 2 + 20;
            float mapY = camera.position.y - camera.viewportHeight / 2 + 20;
            batch.draw(mapImage, mapX, mapY, mapWidth, mapHeight);
        }
    }

    protected void setMapImage(Texture mapImage) {
        this.mapImage = mapImage;
        if (mapImage != null) {
            mapHeight = Gdx.graphics.getHeight() * 0.25f;
            float aspectRatio = (float) mapImage.getHeight() / (float) mapImage.getWidth();
            mapWidth = mapHeight / aspectRatio;
        }
    }


    public String getTilemapFileName() {
        return this.tilemapFileName;
    }

    @Override
    public void show() {
        this.zeppelin = Zeppelin.getInstance();
        this.screenStartTime = TimeUtils.nanoTime();

    }
    public World getWorld() {
        return world;
    }

    public void dispose(){
     /*   if (plane != null && plane.bullets != null){
        for (Bullet bullet : plane.bullets) {
            if (bullet != null) {
                bullet.dispose();}
            }
        }
        planes.forEach(Plane::dispose);
        planes.clear();
        stormClouds.forEach(StormCloud::dispose);
        stormClouds.clear();
        altitudeAlarmSound.dispose();
        game.font.dispose();
        mapImage.dispose();
        tileMapHelper.dispose();

        // Dispose Box2D world
        if (world != null) {
            world.dispose();
        }
        // Dispose Box2D debug renderer
        if (box2DDebugRenderer != null) {
            box2DDebugRenderer.dispose();
        }
      //  zeppelin.dispose();
        Zeppelin.setInstance(null);*/
    }
}
