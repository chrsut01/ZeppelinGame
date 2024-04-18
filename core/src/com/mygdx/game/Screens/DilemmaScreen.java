package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
    private Dilemma dilemma;
    private Stage stage;
    private TextField questionTextField;
    private Skin skin;
    private TextButton[] answerButtons;
    private TextButton option1Button;
    private TextButton option2Button;
    private TextButton option3Button;

    private TextButton square;
    private TextureRegionDrawable background;
    private boolean gamePaused;
    private boolean buttonClicked;


    public DilemmaScreen(ZeppelinGame game) {


        this.dilemma = dilemma;
        this.stage = new Stage(new ScreenViewport());

        // try catch methods for error handling
    /*    skin = new Skin(Gdx.files.internal("uiskin2.json")); // Load a skin for UI elements

      //  FileHandle fontFile = Gdx.files.internal("arial_16.fnt");
      //  BitmapFont font = new BitmapFont(fontFile);

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
        }*/

    }

    private void createUI() {
        // Create a skin
        skin = new Skin();

        // Create a bitmap font for text
        BitmapFont font = new BitmapFont();

        // Define the background texture region for the square
        background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("background.png")))); // Replace "background.png" with your desired texture

        // Create a button style for the square
        TextButton.TextButtonStyle squareStyle = new TextButton.TextButtonStyle();
        squareStyle.up = background;
        squareStyle.font = font;

        // Create a button for the square
        square = new TextButton("DILEMMA 1 \n Question", squareStyle);
        square.setSize(400, 400);
        square.setPosition(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f - 200);

        // Add the square to the stage
        stage.addActor(square);

        // Calculate button positions relative to the square
        float buttonX = square.getX() + 100; // Adjust the X position as needed
        float option1Y = square.getY() + 100; // Adjust the Y position as needed
        float option2Y = square.getY() + 50; // Adjust the Y position as needed

        // Create button style
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        skin.add("default", buttonStyle);

        option1Button = new TextButton("Option 1", skin);
        option1Button.setPosition(buttonX, option1Y);
        option1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle option 1 choice
                buttonClicked = true;
                dispose();
                // Dispose of the screen
                DisposeFunction();
                // Need some funtion to resume game. Maybe not this.
               // ResumeGameFunction();
            }
        });

        option2Button = new TextButton("Option 2", skin);
        option2Button.setPosition(buttonX, option2Y);;
        option2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Option 2 clicked");
                // Handle option 2 choice
                buttonClicked = true;
                dispose();
            }
        });

        option3Button = new TextButton("Option 3", skin);
        option3Button.setPosition(buttonX, option2Y);;
        option3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Option 3 clicked");
                // Handle option 3 choice
                buttonClicked = true;
                dispose();
            }
        });
        stage.addActor(option1Button);
        stage.addActor(option2Button);
        stage.addActor(option3Button);
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

    @Override
    public void dispose() {
        background.getRegion().getTexture().dispose();
        square.clear();
        option1Button.clear();
        option2Button.clear();
        option3Button.clear();
        stage.dispose();
    }

    private void DisposeFunction() {
        // Dispose of the screen
        dispose();
    }

}
