package com.mygdx.game.ExtraScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameConfig;
import com.mygdx.game.ZeppelinGame;

public class IntroScreen extends ScreenAdapter {

    final ZeppelinGame game;
    Texture backgroundImage;
    Music valkyriesMusic;
    OrthographicCamera camera;
    int screenWidth = GameConfig.SCREEN_WIDTH;
    int screenHeight = GameConfig.SCREEN_HEIGHT;
    private boolean isSpacePressed = false;

    public IntroScreen(ZeppelinGame game) {
            this.game = game;
            backgroundImage = new Texture(Gdx.files.internal("ForsideScreen.png"));
            valkyriesMusic = Gdx.audio.newMusic(Gdx.files.internal("valkyries.mp3"));

            camera = new OrthographicCamera();
            camera.setToOrtho(false, screenWidth, screenHeight);

            game.font = new BitmapFont();
        }


        @Override
        public void render(float delta) {
            ScreenUtils.clear(0, 0, 0.2f, 0);

            camera.update();
            game.batch.setProjectionMatrix(camera.combined);

            game.batch.begin();

            game.batch.draw(backgroundImage, 0, 0, screenWidth, screenHeight);

            game.font.draw(game.batch, " ", screenWidth / 2 -200, screenHeight / 2 + 25);
            game.font.draw(game.batch, " ", screenWidth / 2 - 200, screenHeight / 2 - 50);

            // Set the font size
            game.font.getData().setScale(2f);

            game.batch.end();

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isSpacePressed) {
                System.out.println("ForsideScreen: Space key pressed.");
                game.setScreen(new ManualScreen(game));
                isSpacePressed = true;
                dispose();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
                Gdx.app.exit();
        }

    @Override
    public void show() {
        valkyriesMusic.play();
    }

        @Override
        public void resize(int width, int height) {

        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void hide() {

        }

        @Override
        public void dispose() {
            backgroundImage.dispose();
            valkyriesMusic.dispose();
            game.font.dispose();
        }
}
