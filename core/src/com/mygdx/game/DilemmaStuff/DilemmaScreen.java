package com.mygdx.game.DilemmaStuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameLevel;
import com.mygdx.game.SideScrollerStuff.SideScrollerScreen;
import com.mygdx.game.ZeppelinGame;


public class DilemmaScreen extends ScreenAdapter {
    private ZeppelinGame game;
    private GameLevel gameLevel;
    protected Dilemma dilemma;
    private Stage stage;
    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private World world;

    private TextField questionTextField;
    private Skin skin;
    private TextButton[] answerButtons;
    private TextField responseTextField;
    private float scaleX = 2.0f;
    private float scaleY = 2.0f;

    private TextureRegionDrawable background;
    private boolean buttonClicked;

    public DilemmaScreen(ZeppelinGame game, Dilemma dilemma) {
        this.game = game;
        this.gameLevel = game.getCurrentLevel();
        this.dilemma = dilemma;
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin();
        initializeUI();
    }

    public void initializeUI(){
        System.out.println("DilemmaScreen: initializeUI() method called");

        Gdx.input.setInputProcessor(stage);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(scaleX, scaleY);
        // background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main-menu-background.png"))));
        Color fontColor = Color.WHITE;
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = fontColor;
        skin.add("default", textFieldStyle, TextField.TextFieldStyle.class);

        questionTextField = new TextField(dilemma.getQuestion(), skin);
        questionTextField.setPosition(100, Gdx.graphics.getHeight() - 100); // Set position
        questionTextField.setSize(1000, 50); // Set size
        stage.addActor(questionTextField);

        TextButton.TextButtonStyle squareStyle = new TextButton.TextButtonStyle();
        squareStyle.up = background;
        squareStyle.font = font;

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        skin.add("default", buttonStyle);

        answerButtons = new TextButton[dilemma.getAnswers().size()];
        float buttonY = Gdx.graphics.getHeight() - 200; // Initial Y position for buttons
        for (int i = 0; i < answerButtons.length; i++) {
            String answer = dilemma.getAnswers().get(i);
            final int index = i; // Declare final variable here
            TextButton button = new TextButton(answer, skin);
            button.setPosition(100, buttonY - i * 50);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("DilemmaScreen: Button clicked = " + index);
                    responseTextField.setText(dilemma.getResponses().get(index)); // Display response

                    if(dilemma.getCorrectAnswerIndex() == index) {
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                Dilemma nextDilemma = gameLevel.getNextDilemma();
                                System.out.println("DilemmaScreen: clicked() and run() called.");

                                if(nextDilemma != null){
                                    DilemmaScreen nextDilemmaScreen = new DilemmaScreen(game, nextDilemma);
                                    game.setScreen(nextDilemmaScreen);
                                } else {
                                    System.out.println("DilemmaScreen: getNextDilemma() null");
                                    SideScrollerScreen sideScroller = gameLevel.getSideScroller();
                                    game.setScreen(sideScroller);
                                }
                            }
                        }, 1f);
                    }
                }
            });
            answerButtons[i] = button;
            stage.addActor(button);
        }

        // Create and position response text field
        responseTextField = new TextField("", skin);
        responseTextField.setPosition(100, 100);
        responseTextField.setSize(1000, 50);
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
     //   background.getRegion().getTexture().dispose();
        skin.dispose();
        questionTextField.clear();
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
