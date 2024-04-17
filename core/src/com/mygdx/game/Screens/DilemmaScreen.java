package com.mygdx.game.Screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Dilemma;
import com.mygdx.game.ZeppelinGame;

public class DilemmaScreen extends ScreenAdapter {
    private Dilemma dilemma;
    private Stage stage;

    public DilemmaScreen(ZeppelinGame game) {
        this.dilemma = dilemma;
        this.stage = new Stage(new ScreenViewport());
        // Initialize UI elements based on the dilemma data
    }

    @Override
    public void show() {
        // Display dilemma question, answers, and images
    }

    @Override
    public void render(float delta) {
        // Render UI elements
    }

    public void addToStage(Actor actor) {
        stage.addActor(actor);
    }
}
