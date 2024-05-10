package com.mygdx.game.Rectangles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.List;

public class Plane extends Rectangle{

    private final Texture planeImage;
    public Sound planeFlyingSound;

    private Zeppelin zeppelin;
    public Sound planeCrashSound;
    public static final int width = 89/2;
    public static final int height = 44/2;
    //private static float x = GameConfig.SCREEN_WIDTH;
    private float x;
    private float y;
    private final int yAngle;
    private final Sprite planeSprite;
    public List<Bullet> bullets = new ArrayList<>();
    private long lastBulletTime = 0;
    private long startFacingTime = 0;
    private static final long SHOOTING_TIME = 2500;
    private static final long BULLET_SPAWN_INTERVAL = 250;
    private boolean canShoot;

    private static final float BULLET_SPEED = - 400;
    public Sound shootingSound;
    private boolean shootingSoundStarted;

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
        this.shootingSoundStarted = false;
        this.canShoot = true;
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
        float xOffset = (width - zeppelin.getWidth()) / 2;
        float yOffset = (height - zeppelin.getHeight()) / 2;

        Rectangle planeBounds = new Rectangle(x, y, width, height);
        Rectangle zeppelinBounds = new Rectangle(zeppelin.getX() + xOffset, zeppelin.getY() + yOffset, zeppelin.getWidth() - 10, zeppelin.getHeight() - 5);
        return planeBounds.overlaps(zeppelinBounds);
    }

    private boolean alreadyFacing = false;

    public boolean isFacing(Zeppelin zeppelin) {
        if (!alreadyFacing && zeppelin != null) {
            float zepX = zeppelin.getX();
            float zepY = zeppelin.getY();
            if ((zepX < x && zepY < y && yAngle < 0) || (zepX < x && zepY > y && yAngle > 0)) {
                System.out.println("Plane isFacing the zeppelin");
                alreadyFacing = true; // Set the flag to true after the condition is met
                return true;
            }
        }
        return false;
    }

    public void shootBullets(float deltaTime) {
        // Only shoot if the plane is facing the zeppelin and shooting hasn't started yet
        if (canShoot && !shootingSoundStarted) {
            System.out.println("shoot method: canShoot && !shootingSoundStarted");
            playShootingSound(); // Start the shooting sound
            shootingSoundStarted = true;
            // timer for shooting bullets for 2 seconds
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    updateBullets(deltaTime);
                }
            }, 0, 0.25f);
// timer for shooting bullets every 0.25 seconds
        }
    }
    public void playShootingSound() {
        if (shootingSound != null) {
            System.out.println("playShootingSound method called");
            shootingSound.play(); // Start the shooting sound
            shootingSoundStarted = true; // Set flag to indicate that shooting sound has started
        }
    }


 /*   public void updateBullets(float deltaTime) {
        // Create and add bullets if shooting has started
        if (shootingSoundStarted) {
            startFacingTime += deltaTime;
            lastBulletTime += deltaTime;
            // Check if it's time to stop shooting
            if (startFacingTime <= SHOOTING_TIME) {

                    // Check if it's time to spawn a bullet
                    if (lastBulletTime >= BULLET_SPAWN_INTERVAL) {
                        float velocityX = MathUtils.cosDeg(yAngle) * BULLET_SPEED;
                        float velocityY = MathUtils.sinDeg(yAngle) * BULLET_SPEED;

                        Bullet bullet = new Bullet(x, y, yAngle, velocityX, velocityY);
                        bullet.x = getX() + 5; // Set initial position
                        bullet.y = getY() + 10;
                        bullets.add(bullet);

                        lastBulletTime = 0; // Reset the timer
                    }
                }
            if (startFacingTime >= SHOOTING_TIME) {
                shootingSound.stop(); // Stop the shooting sound
                shootingSoundStarted = false; // Set flag to indicate that shooting sound has stopped
                startFacingTime = 0; // Reset the timer
                lastBulletTime = 0; // Reset the timer
            }
                // Reset shooting time if necessary
            /*    if (!shootingSoundStarted) {
                    lastBulletTime = 0;
                    startFacingTime = 0;
                }
            }
        }*/


    //First updateBullets method
    public void updateBullets(float deltaTime) {
      //  System.out.println("updateBullets method called, startFacingTime: " + startFacingTime);
        // Create and add bullets if shooting has started
        if (shootingSoundStarted && startFacingTime < SHOOTING_TIME) {
            startFacingTime += Gdx.graphics.getDeltaTime();
            lastBulletTime += Gdx.graphics.getDeltaTime();

          //  System.out.println("deltaTime= " + deltaTime);

            // Check if it's time to spawn a bullet
              //  System.out.println("startFacingTime < SHOOTING_TIME " + startFacingTime + " " + SHOOTING_TIME);
                if(lastBulletTime >= BULLET_SPAWN_INTERVAL) {
                    System.out.println("lastBulletTime >= BULLET_SPAWN_INTERVAL" + lastBulletTime + " " + BULLET_SPAWN_INTERVAL);
                float velocityX = MathUtils.cosDeg(yAngle) * BULLET_SPEED;
                float velocityY = MathUtils.sinDeg(yAngle) * BULLET_SPEED;

                Bullet bullet = new Bullet(x, y, yAngle, velocityX, velocityY);
                bullet.x = getX() + 5; // Set initial position
                bullet.y = getY() + 10;
                System.out.println("new bullet created");
                //bullet.yAngle = yAngle; // Set initial angle

                bullets.add(bullet);
                lastBulletTime -= BULLET_SPAWN_INTERVAL; // Reset the timer
            }

            // Reset shooting time if necessary
            if (startFacingTime >= SHOOTING_TIME) {
                System.out.println("startFacingTime >= SHOOTING_TIME " + startFacingTime + " " + SHOOTING_TIME);
                lastBulletTime = 0;
                startFacingTime = 0;
                shootingSoundStarted = false;
            }

    }
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }


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
