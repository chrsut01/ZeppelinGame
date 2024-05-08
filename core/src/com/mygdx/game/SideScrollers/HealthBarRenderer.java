package com.mygdx.game.SideScrollers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HealthBarRenderer {
    private HealthBar healthBar;
    private BitmapFont font;

    public HealthBarRenderer(HealthBar healthBar) {
        this.healthBar = healthBar;
        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK); // Set text color
    }

    public void render(SpriteBatch batch, float x, float y) {
        String healthText = "Health Points: " + healthBar.getCurrentHealth();
        font.draw(batch, healthText, x, y);
    }
}
