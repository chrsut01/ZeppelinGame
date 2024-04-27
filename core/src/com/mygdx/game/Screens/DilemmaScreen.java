package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Dilemma;
import com.mygdx.game.ZeppelinGame;


public class DilemmaScreen extends ScreenAdapter {
    protected Dilemma dilemma;
    private Stage stage;
    private TextField questionTextField;
    private Skin skin;
    private TextButton[] answerButtons;
    private TextField responseTextField;

    private TextButton square;
    private TextureRegionDrawable background;
    private boolean buttonClicked;

    public DilemmaScreen(ZeppelinGame game, Dilemma dilemma) {
        this.stage = new Stage(new ScreenViewport());

        skin = new Skin();
        BitmapFont font = new BitmapFont();
        background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main-menu-background.png"))));

        TextButton.TextButtonStyle squareStyle = new TextButton.TextButtonStyle();
        squareStyle.up = background;
        squareStyle.font = font;

        square = new TextButton(dilemma.getQuestion(), squareStyle);
        square.setSize(400, 400);
        square.setPosition(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f - 200);
        stage.addActor(square);

        float buttonX = square.getX() + 100;
        float option1Y = square.getY() + 100;
        float option2Y = square.getY() + 50;

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        skin.add("default", buttonStyle);

        answerButtons = new TextButton[dilemma.getAnswers().size()];
        for (int i = 0; i < answerButtons.length; i++) {
            String answer = dilemma.getAnswers().get(i);
            final int index = i; // Declare final variable here
            TextButton button = new TextButton(answer, skin);
            button.setPosition(buttonX, option1Y - i * 50);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buttonClicked = true;
                    // Handle button click here
                    responseTextField.setText(dilemma.getResponses().get(index)); // Display response
                }
            });
            answerButtons[i] = button;
            stage.addActor(button);
        }

        // Create and position response text field
        responseTextField = new TextField("", skin);
        responseTextField.setPosition(100, 100);
        responseTextField.setSize(400, 50);
        stage.addActor(responseTextField);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        background.getRegion().getTexture().dispose();
        square.clear();
        responseTextField.clear();
        for (TextButton button : answerButtons) {
            button.clear();
        }
        stage.dispose();
    }

    public void setDilemma(Dilemma currentDilemma) {
        this.dilemma = currentDilemma;
    }
}
