package com.mygdx.game.Rectangles;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bullet extends Rectangle {
    float x, y; // Position
    int yAngle;
    private final Sprite bulletSprite;
    private final Texture bulletImage;
   // public Sound shootingSound;
    public Sound bulletHitSound;
    float velocityX, velocityY; // Velocity

    public Bullet(float x, float y, int yAngle){
        this.x = x;
        this.y = y;
        this.yAngle = yAngle;
        bulletImage = new Texture("bullet.png");
        //shootingSound = Gdx.audio.newSound(Gdx.files.internal("machine_gun.mp3"));
        bulletSprite = new Sprite(bulletImage);
        bulletSprite.setSize(3, 1);
       // this.velocityX = velocityX;
       // this.velocityY = velocityY;
    }

    public void updatePosition(float deltaTime) {
        y += yAngle * deltaTime;
        x -= 400 * deltaTime;
    }

    public void dispose() {
        bulletImage.dispose();
     //   shootingSound.dispose();
        bulletSprite.getTexture().dispose();
    }

    public void render(SpriteBatch batch) {
        bulletSprite.setPosition(x, y);
        bulletSprite.draw(batch);
    }
}
