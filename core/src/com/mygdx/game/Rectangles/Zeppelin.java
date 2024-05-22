package com.mygdx.game.Rectangles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.GameConfig;


public class Zeppelin extends Rectangle {
    private static Zeppelin instance;
    private static final float width = 783/2f;
    private static final float height = 109/2f;
    private static final float MAX_SPEED = 80; // Maximum speed of the zeppelin
    private static final float ACCELERATION = 55f; // Acceleration factor
    private static final float DECELERATION = 20f; // Deceleration factor
    private static final float MIN_SPEED = 1; // Minimum speed before stopping

    public float ySpeed = 0;
    public float xSpeed = 80;

    private Sprite zeppelinSprite;
    private Sound engineSound;
    public Sound zeppelinCrashSound;
    public boolean showFlicker;
    private Sprite flickerSprite;


    private Zeppelin() {
        init();
    }
    public static Zeppelin getInstance() {
        if (instance == null) {
            instance = new Zeppelin();
        }
        return instance;
    }

    public static void setInstance(Zeppelin zepInstance) {
        instance = zepInstance;
    }

    private void init() {
        zeppelinSprite = new Sprite(new Texture(Gdx.files.internal("zeppelin-image.png")));
        engineSound = Gdx.audio.newSound(Gdx.files.internal("ZeppelinEngine.mp3"));

        zeppelinSprite.setSize(width, height);

        zeppelinSprite.setOrigin(width / 2, height / 2);
        zeppelinSprite.setPosition(GameConfig.SCREEN_WIDTH / 2f - width / 2,
                GameConfig.TILEMAP_HEIGHT / 2f - height / 2);
        flickerSprite = new Sprite(new Texture(Gdx.files.internal("zeppelin-shock-image.png")));
    }

    public void update() {
        handleInput();
        zeppelinSprite.translateX(xSpeed * Gdx.graphics.getDeltaTime());
    }

    public void render(SpriteBatch batch) {
        zeppelinSprite.draw(batch);
        if (showFlicker) {
            System.out.println("Zeppelin: render() called: showFlicker.");
            flickerSprite.setPosition(zeppelinSprite.getX() - 10, zeppelinSprite.getY() - 10);
            flickerSprite.draw(batch);
        }
    }

    public void playEngineSound(float volume) {
        engineSound.loop(volume);
    }
    public void playCrashSound() {
        zeppelinCrashSound = Gdx.audio.newSound(Gdx.files.internal("plane_crash.mp3"));
        zeppelinCrashSound.play(1.0f);
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            ySpeed += ACCELERATION * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            ySpeed -= ACCELERATION * Gdx.graphics.getDeltaTime();
        if (!Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (ySpeed > 0)
                ySpeed -= DECELERATION * Gdx.graphics.getDeltaTime();
            else if (ySpeed < 0)
                ySpeed += DECELERATION * Gdx.graphics.getDeltaTime();
        }

        // Limit the speed
        ySpeed = MathUtils.clamp(ySpeed, -MAX_SPEED, MAX_SPEED);

        // Apply position update based on speed
        zeppelinSprite.translateY(ySpeed * Gdx.graphics.getDeltaTime());

        // Ensure speed doesn't drop below a certain threshold to avoid jittering
        if (Math.abs(ySpeed) < MIN_SPEED) ySpeed = 0;

        // Ensure the zeppelin stays within the screen bounds vertically
        float minY = 0;
        float maxY = GameConfig.TILEMAP_HEIGHT - (zeppelinSprite.getHeight() +7);

        // Check if the zeppelin is at the top of the screen and reduce ySpeed to change direction more naturally
        if (zeppelinSprite.getY() == maxY && ySpeed > 0) {
            ySpeed -= ACCELERATION * Gdx.graphics.getDeltaTime();
        }
        // Keep the zeppelin within the screen bounds
        zeppelinSprite.setY(MathUtils.clamp(zeppelinSprite.getY(), minY, maxY));
    }

    // Check if zeppelin overlaps with a polygonMapObject
    public boolean overlaps (PolygonMapObject polygonMapObject) {
        Rectangle zeppelinBounds = new Rectangle(getX() + 15, getY() + 10, getWidth() - 32, getHeight() - 20);
        return polygonMapObject.getPolygon().getBoundingRectangle().overlaps(getBoundingRectangle());
    }

    public void setShowFlicker(boolean showFlicker) {
        this.showFlicker = showFlicker;
    }
    public void setFlickerOpacity(float opacity) {
        flickerSprite.setAlpha(opacity);
    }

    public void zeppelinFlicker(final float totalFlickerDuration) {
        System.out.println("Zeppelin: zeppelinFlicker() called");
        final float[] elapsedTime = {0}; // Variable to track the elapsed time
        final float[] nextFlickerDelay = {0}; // Variable to track the delay before the next flicker

        Timer.schedule(new Timer.Task() {
            @Override
            public void run(){
                elapsedTime[0] += nextFlickerDelay[0];

                if(elapsedTime[0] >= totalFlickerDuration){
                    setShowFlicker(false);
                    return;
                }
                float flickerDuration = MathUtils.random(0.01f, 0.05f); // Random duration for each flicker
                float opacity = MathUtils.random(0.01f, 0.1f); // Random opacity

                // Show lightning with random opacity
                setShowFlicker(true);
                setFlickerOpacity(opacity);

                // Schedule a task to hide the lightning after the flicker duration
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        setShowFlicker(false);
                    }
                }, flickerDuration);

                nextFlickerDelay[0] = MathUtils.random(0.05f, 0.1f); // Random delay before the next flicker
            }
        }, 0, MathUtils.random(0.05f, 0.1f));
    }


    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return  height;
    }
    public float getX() {
        return zeppelinSprite.getX();
    }

    public float getY() {
        return zeppelinSprite.getY();
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(zeppelinSprite.getX() + 15, zeppelinSprite.getY() + 10, zeppelinSprite.getWidth() - 32, zeppelinSprite.getHeight() - 20);
    }
    public void dispose() {
        zeppelinSprite.getTexture().dispose();
        engineSound.dispose();
        zeppelinCrashSound.dispose();
        flickerSprite.getTexture().dispose();

    }

}

