package com.mygdx.game.Rectangles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Plane extends Rectangle{

    private OrthographicCamera camera;
    private final Texture planeImage;
    public Sound planeFlyingSound;

    private Zeppelin zeppelin;
    public Sound planeCrashSound;
    public static final int width = 89/2;
    public static final int height = 44/2;
    private float x;
    private float y;
    private final int yAngle;
    private final Sprite planeSprite;
    public List<Bullet> bullets;
    private static final int MAX_BULLETS = 10;
    private int bulletsShot = 0;

    public boolean canShoot;
    private boolean shootingStarted;
    private float shootingTime;

    private static final float BULLET_SPEED = - 200;
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
        bullets = new ArrayList<>();
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
        Rectangle zeppelinBounds = new Rectangle(zeppelin.getX() + 15, zeppelin.getY() + 10, zeppelin.getWidth() - 32, zeppelin.getHeight() - 20);
        return planeBounds.overlaps(zeppelinBounds);
    }

    private boolean isFacingZeppelin = false;

   public boolean isFacing(Zeppelin zeppelin) {
        if (!isFacingZeppelin && zeppelin != null) {
            float zepX = zeppelin.getX();
            float zepY = zeppelin.getY();
            if ((zepX < x && zepY < y && yAngle < -10) || (zepX < x && zepY > y && yAngle > 10) || (zepX < x && zepY == y && yAngle < 5) || (zepX < x && zepY == y && yAngle > -5)) {
                System.out.println("Plane isFacing the zeppelin");
                isFacingZeppelin = true; // Set the flag to true after the condition is met
                return true;
            }
        }
        return false;
    }


    public void playShootingSound() {
        if (shootingSound != null) {
            System.out.println("playShootingSound method called");
            shootingSound.play(10f); // Start the shooting sound
            shootingSoundStarted = true; // Set flag to indicate that shooting sound has started
        }
    }

    public void shootBullets(float deltaTime) {
        // Only shoot if the plane is facing the zeppelin and shooting hasn't started yet
        if (canShoot && !shootingStarted && bulletsShot < MAX_BULLETS) {
            System.out.println("Start shooting");
            playShootingSound(); // Start the shooting sound
            shootingStarted = true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (bulletsShot < MAX_BULLETS) {
                        spawnBullet(deltaTime);
                        bulletsShot++;
                    } else {
                        stopShooting();
                    }
                }
            }, 0, 0.15f); // Interval can be adjusted
        }
    }
    private void spawnBullet(float deltaTime) {
        float velocityX = MathUtils.cosDeg(yAngle) * BULLET_SPEED;
        float velocityY = MathUtils.sinDeg(yAngle) * BULLET_SPEED;
        Bullet bullet = new Bullet(x, y, yAngle, velocityX, velocityY);
        bullet.x = getX() + 5; // Set initial position
        bullet.y = getY() + 10;
        bullets.add(bullet);
    }

    private void stopShooting() {
        shootingStarted = false;
        shootingTime = 0;
    }
    public void updateBullets(float deltaTime) {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.updatePosition(deltaTime);
            // Remove bullet if it is off screen
            if (bullet.isOutOfBounds()) {
                iterator.remove();
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
