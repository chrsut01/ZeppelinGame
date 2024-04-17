package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Dilemma;
import com.mygdx.game.ZeppelinGame;

public class DilemmaScreen extends ScreenAdapter {
    private Dilemma dilemma;
    private Stage stage;
    private TextField questionTextField;
    private TextButton[] answerButtons;


    public DilemmaScreen(ZeppelinGame game) {
        this.dilemma = dilemma;
        this.stage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("uiskin.json")); // Load a skin for UI elements

        // Initialize UI elements
        questionTextField = new TextField(dilemma.getQuestion(), skin);
        questionTextField.setPosition(100, 300); // Example position

        answerButtons = new TextButton[dilemma.getAnswers().size()];
        for (int i = 0; i < answerButtons.length; i++) {
            String answer = dilemma.getAnswers().get(i);
            TextButton button = new TextButton(answer, skin);
            button.setPosition(100, 200 - i * 50); // Example position
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Handle button click
                    // Example: System.out.println("Button clicked: " + answer);
                }
            });
            answerButtons[i] = button;
        }

        // Add UI elements to the stage
        stage.addActor(questionTextField);
        for (TextButton button : answerButtons) {
            stage.addActor(button);
        }

    }

    @Override
    public void show() {
        // Display dilemma question, answers, and images
    }

    @Override
    public void render(float delta) {
// Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update and render the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();    }

    public void addToStage(Actor actor) {
        stage.addActor(actor);
    }
}
