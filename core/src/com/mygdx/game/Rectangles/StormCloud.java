package com.mygdx.game.Rectangles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

public class StormCloud extends Rectangle {
    private Vector2 position;
    private final Texture stormCloudImage;
    private float radius;
    private final float width = 3364 * 0.4f; // 3364 (orig pixels)
    private final float height = 1564 * 0.4f; //1564 (orig pixels)
    private final float boundsWidth = 672;
    private final float boundsHeight = 312;

    private final Sprite stormCloudSprite;
    public Rectangle stormCloudHitBox;
    private final Texture lightningTexture = new Texture("lightning.png");
    public Sound lightningStrikeSound;
    public boolean showLightning;
    private float lightningDuration = 1.0f;
    private float timeSinceSpawn = 0.0f;
    private boolean lightningSoundPlayed = false;


    public StormCloud(float x, float y) {
        // this.position = new Vector2(x, y);
        // this.radius = radius;
        this.x = x;
        this.y = y;
        stormCloudImage = new Texture("storm-cloud.png");
        lightningStrikeSound = Gdx.audio.newSound(Gdx.files.internal("lightning_strike.mp3"));
        stormCloudSprite = new Sprite(stormCloudImage);
        stormCloudSprite.setSize(width, height);
        this.showLightning = false;
    }


  public void updatePosition(float deltaTime) {
      // Move the cloud to the left
      x -= 50 * deltaTime;

      timeSinceSpawn += deltaTime;

      if (!showLightning && timeSinceSpawn >= 3.5f) {
         showLightning = true;
         Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                showLightning = false;
            }
        }, lightningDuration); // Delay before showing lightning (adjust as needed)
      }
  }

    public boolean overlaps(Zeppelin zeppelin) {
        float xOffset = (width - boundsWidth) / 2;
        float yOffset = (height - boundsHeight) / 2;
        Rectangle stormCloudBounds = new Rectangle(x + xOffset, y + yOffset, boundsWidth, boundsHeight);
        Rectangle zeppelinBounds = new Rectangle(zeppelin.getX(), zeppelin.getY(), zeppelin.getWidth(), zeppelin.getHeight());
        return stormCloudBounds.overlaps(zeppelinBounds);
    }


    public boolean isLightningSoundPlayed() {
        return lightningSoundPlayed;
    }

    public void setLightningSoundPlayed(boolean played) {
        this.lightningSoundPlayed = played;
    }
    public void render(SpriteBatch batch) {
        stormCloudSprite.setPosition(x, y);
        stormCloudSprite.draw(batch);
        if (showLightning) {
            float lightningX = x + (stormCloudSprite.getWidth() - lightningTexture.getWidth()) / 2;
            float lightningY = y + (stormCloudSprite.getHeight() - lightningTexture.getHeight()) / 2;
            batch.draw(lightningTexture, lightningX, lightningY);
        }
    }

    public void dispose() {
        stormCloudImage.dispose();
        lightningTexture.dispose();
        stormCloudHitBox = null;
    }
}
