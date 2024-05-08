package com.mygdx.game.Rectangles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Plane extends Rectangle{

    private final Texture planeImage;
    public Sound planeFlyingSound;

    private Zeppelin zeppelin;
    public Sound planeCrashSound;
    public static final int width = 44;
    public static final int height = 44;
    //private static float x = GameConfig.SCREEN_WIDTH;
    private float x;
    private float y;
    private final int yAngle;
    private final Sprite planeSprite;
    public List<Bullet> bullets = new ArrayList<>();
    private long lastBulletTime = 0;
    private long startFacingTime = 0;
    private static final long FACING_DURATION = 2500;
    private static final float BULLET_SPAWN_INTERVAL = 500;
    public Sound shootingSound;
    private boolean shootingSoundStarted = false;
    public Plane(float x, float y, int yAngle) {
        this.x = x;
        this.y = y;
        this.yAngle = yAngle;
        planeImage = new Texture("sopwith_camel_small.png");
        planeFlyingSound = Gdx.audio.newSound(Gdx.files.internal("plane_flying.mp3"));
        planeCrashSound = Gdx.audio.newSound(Gdx.files.internal("plane_crash.mp3"));
        shootingSound = Gdx.audio.newSound(Gdx.files.internal("machine_gun.mp3"));
        planeSprite = new Sprite(planeImage);
        planeSprite.setSize(width, height);
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public int getyAngle() {
        return yAngle;
    }

    public void updatePosition(float deltaTime) {
        y += yAngle * deltaTime;
        x -= 100 * deltaTime;
    }

    public boolean overlaps(Zeppelin zeppelin) {
        Rectangle planeBounds = new Rectangle(x, y, width, height);
        Rectangle zeppelinBounds = new Rectangle(zeppelin.getX(), zeppelin.getY(), zeppelin.getWidth(), zeppelin.getHeight());
        return planeBounds.overlaps(zeppelinBounds);
    }


    // Determine if the plane is facing the zeppelin
    public boolean isFacing(Zeppelin zeppelin) {
        if (zeppelin != null) {
            float zepX = zeppelin.getX();
            float zepY = zeppelin.getY();
            if ((zepX < x && zepY < y && yAngle < 0) || (zepX < x && zepY > y && yAngle > 0)) {
             //   System.out.println("Plane is facing the zeppelin");
                return true;
            }
        }
        return false;
    }


    public void shoot(float deltaTime) {
        // Only shoot if the plane is facing the zeppelin and shooting hasn't started yet
        if (!shootingSoundStarted) {
            System.out.println("Plane is facing the zeppelin and !shootingSoundStarted");
            playShootingSound(); // Start the shooting sound
            shootingSoundStarted = true;
        }
    }
    public void playShootingSound() {
        if (shootingSound != null) {
            System.out.println("shootingSound method called");
            shootingSound.play(); // Start the shooting sound
            shootingSoundStarted = true; // Set flag to indicate that shooting sound has started
        }
    }
    public void updateBullets(float deltaTime) {
        // Create and add bullets if shooting has started
        if (shootingSoundStarted) {
            startFacingTime += deltaTime;
            lastBulletTime += deltaTime;

            // Check if it's time to spawn a bullet
            if (startFacingTime < FACING_DURATION && lastBulletTime < BULLET_SPAWN_INTERVAL) {
                Bullet bullet = new Bullet(x, y, yAngle);
                bullet.x = getX(); // Set initial position
                bullet.y = getY();
                bullet.yAngle = getyAngle(); // Set initial angle

                bullets.add(bullet);
            }

            // Reset shooting time if necessary
            if (startFacingTime >= FACING_DURATION && lastBulletTime >= BULLET_SPAWN_INTERVAL) {
                lastBulletTime = 0;
                startFacingTime = 0;
                shootingSoundStarted = false;
            }
        }
    }




   /* public void shoot(float deltaTime){
        System.out.println("shoot method called");
        boolean shootingSoundStarted = false; // Flag to track if shooting sound has started

        Bullet bullet = null;

        while (startFacingTime < FACING_DURATION && lastBulletTime < BULLET_SPAWN_INTERVAL) {
            startFacingTime += deltaTime;
            lastBulletTime += deltaTime;

            bullet = new Bullet(x, y, yAngle);
            bullet.x = getX(); // Set initial position
            bullet.y = getY();
            bullet.yAngle = getyAngle(); // Set initial angle

            // Check if shooting sound has not yet started and start it
            if (!shootingSoundStarted) {
                bullet.shootingSound.play(); // Start the shooting sound
                shootingSoundStarted = true; // Set flag to indicate that shooting sound has started
            }
            bullets.add(bullet);
            System.out.println("Bullet added to list");
        }
        if (startFacingTime >= FACING_DURATION && lastBulletTime >= BULLET_SPAWN_INTERVAL) {
            lastBulletTime = 0;
            startFacingTime = 0;
        }
    }*/



    public void render (SpriteBatch batch) {
        planeSprite.setPosition(x, y);
        planeSprite.draw(batch);
    }

    public void dispose() {
        planeImage.dispose();
        planeFlyingSound.dispose();
        planeCrashSound.dispose();
        shootingSound.dispose();
        bullets.clear();
        planeSprite.getTexture().dispose();

    }
}
