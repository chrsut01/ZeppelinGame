package com.mygdx.game.Rectangles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bullet extends Rectangle {
    float x, y; // Position
    int yAngle;
    float velocityX, velocityY; // Velocity
    private final Sprite bulletSprite;
    private final Texture bulletImage;
    public Sound bulletHitSound;
    private boolean bulletHitSoundPlayed = false;

    public Bullet(float x, float y, int yAngle, float velocityX, float velocityY) {
        this.x = x;
        this.y = y;
        this.yAngle = yAngle;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        bulletImage = new Texture("bullet.png");
        bulletHitSound = Gdx.audio.newSound(Gdx.files.internal("bullet_hit4.mp3"));
        bulletSprite = new Sprite(bulletImage);
        bulletSprite.setSize(6, 2);
    }

    public void updatePosition(float deltaTime) {
        // from Plane update method:
        y += yAngle * deltaTime;
        x -= 400 * deltaTime;
    }

    public boolean overlaps(Zeppelin zeppelin) {
        Rectangle bulletBounds = new Rectangle(x, y, 6, 2);
        Rectangle zeppelinBounds = new Rectangle(zeppelin.getX() + 15, zeppelin.getY() + 10, zeppelin.getWidth() - 32, zeppelin.getHeight() - 20);
        return bulletBounds.overlaps(zeppelinBounds);
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



    public boolean isOutOfBounds() {
        return x < this.x - 1200;
    }

    public void setBulletHitSound(boolean played) {
        this.bulletHitSoundPlayed = played;
    }

    public void dispose() {
        bulletImage.dispose();
        bulletHitSound.dispose();
        bulletSprite.getTexture().dispose();
    }
}
