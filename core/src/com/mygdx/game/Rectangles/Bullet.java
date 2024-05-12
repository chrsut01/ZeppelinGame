package com.mygdx.game.Rectangles;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bullet extends Rectangle {
    //private final OrthographicCamera camera;
    float x, y; // Position
    int yAngle;
    float velocityX, velocityY; // Velocity
    private final Sprite bulletSprite;
    private final Texture bulletImage;
   // public Sound shootingSound;
    public Sound bulletHitSound;
    private Zeppelin zeppelin;


    public Bullet(float x, float y, int yAngle, float velocityX, float velocityY) {
        this.x = x;
        this.y = y;
        this.yAngle = yAngle;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        bulletImage = new Texture("bullet.png");
        bulletSprite = new Sprite(bulletImage);
        bulletSprite.setSize(6, 2);
       // this.camera = camera;
    }

    public void updatePosition(float deltaTime) {
        //x += velocityX * deltaTime;
       // y += velocityY * deltaTime;

        // from Plane update method:
        y += yAngle * deltaTime;
        x -= 400 * deltaTime;

      //  x += MathUtils.cosDeg(yAngle) * velocityX * deltaTime;
      //  y += MathUtils.sinDeg(yAngle) * velocityY * deltaTime;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void render(SpriteBatch batch) {
        bulletSprite.setPosition(x, y);
        bulletSprite.draw(batch);
    }

    public void dispose() {
        bulletImage.dispose();
        //   shootingSound.dispose();
        bulletSprite.getTexture().dispose();
    }

    public boolean isOutOfBounds() {
        return x < this.x - 1200;
    }
  /*  public boolean isOutOfBounds() {
        float cameraLeft = camera.position.x - camera.viewportWidth / 2;
        float cameraBottom = camera.position.y - camera.viewportHeight / 2;
        float cameraTop = camera.position.y + camera.viewportHeight / 2;

        return x < cameraLeft || y < cameraBottom || y > cameraTop;
    }*/
}
