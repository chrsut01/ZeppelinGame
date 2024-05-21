package com.mygdx.game.DilemmaStuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameLevel;
import com.mygdx.game.SideScrollers.SideScrollerScreen;
import com.mygdx.game.ZeppelinGame;


public class DilemmaScreen extends ScreenAdapter {
    private static DilemmaScreen instance;
    private final ZeppelinGame game;
    private final GameLevel gameLevel;
    protected Dilemma dilemma;

    private final Stage stage;

    private final Skin skin;
    private TextButton[] answerButtons;
    private TextField responseTextField;
    private final float scaleX = 2.0f;
    private final float scaleY = 2.0f;

   // private Label questionLabel;
    private TextureRegionDrawable background;
  //  private OrthographicCamera camera;
  //  private Box2DDebugRenderer box2DDebugRenderer;
  //  private World world;
  //  private TextField questionTextField;

    private DilemmaScreen(ZeppelinGame game, Dilemma dilemma) {
        System.out.println("DilemmaScreen: constructor called");
        this.game = game;
        this.gameLevel = game.getCurrentLevel();
        this.dilemma = dilemma;
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin();
        initializeUI();
    }
    public static DilemmaScreen getInstance(ZeppelinGame game, Dilemma dilemma) {
        if (instance == null) {
            instance = new DilemmaScreen(game, dilemma);
        }
        return instance;
    }

    public void initializeUI(){
        System.out.println("DilemmaScreen: initializeUI() method called. gameLevel: " + gameLevel);

        stage.clear();
        Gdx.input.setInputProcessor(stage);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(scaleX, scaleY);
        // background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("main-menu-background.png"))));
        Color fontColor = Color.WHITE;
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = fontColor;
        skin.add("default", textFieldStyle, TextField.TextFieldStyle.class);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, fontColor);
        Label questionLabel = new Label(dilemma.getQuestion(), labelStyle);
        questionLabel.setPosition(100, Gdx.graphics.getHeight() - 200);
        questionLabel.setSize(1000, 150);
        questionLabel.setWrap(true); // Enable wrapping for multi-line text
        stage.addActor(questionLabel);

        TextButton.TextButtonStyle squareStyle = new TextButton.TextButtonStyle();
        squareStyle.up = background;
        squareStyle.font = font;

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        skin.add("default", buttonStyle);

        answerButtons = new TextButton[dilemma.getAnswers().size()];
        float buttonY = Gdx.graphics.getHeight() - 280; // Initial Y position for buttons

        Label.LabelStyle responseLabelStyle = new Label.LabelStyle(font, fontColor);
        Label responseLabel = new Label("", responseLabelStyle);

        for (int i = 0; i < answerButtons.length; i++) {
            String answer = dilemma.getAnswers().get(i);
            int index = i; // Declare final variable here
            TextButton button = new TextButton(answer, skin);
            button.setPosition(100, buttonY - i * 50);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    responseLabel.setText(dilemma.getResponses().get(index));
                    System.out.println("Answer button clicked");
                    // Check if the selected answer is correct
                    if (dilemma.getCorrectAnswerIndex() == index) {
                        System.out.println("Correct answer selected");
                        Dilemma nextDilemma = gameLevel.getNextDilemma();
                        if (nextDilemma != null) {
                            System.out.println("nextDilemma != null and index = " + nextDilemma.getQuestion());
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    System.out.println("not-null Timer task run()");
                                    setNextDilemma(nextDilemma);
                                  //  System.out.println("DilemmaScreen, correct answer goToNextDilemma(nextDilemma) called");
                                }
                            }, 1f);
                        } else {
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    System.out.println("null Timer task run()");
                                    Gdx.app.postRunnable(() ->  setSideScroller());
                                    }
                                }, 0.0f);
                            }
                        }
                    }
            });
            answerButtons[i] = button;
            stage.addActor(button);
        }

        responseLabel.setPosition(100, 100);
        responseLabel.setSize(1000, 150);
        responseLabel.setWrap(true);
        stage.addActor(responseLabel);

    }


    private void setNextDilemma(Dilemma nextDilemma) {
        System.out.println("DilemmaScreen: setNextDilemma() called.");
        this.dilemma = nextDilemma;
        initializeUI();
    }


    private void setSideScroller() {
        System.out.println("DilemmaScreen: setSideScroller() called" + gameLevel.getSideScroller());
        SideScrollerScreen sideScroller = gameLevel.getSideScroller();
        //sideScroller.show();
        sideScroller.initialize();
        game.switchScreen(sideScroller);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    public void setDilemma(Dilemma currentDilemma) {
        this.dilemma = currentDilemma;
    }
    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
        for (TextButton button : answerButtons) { button.clear(); }
       // Added to prevent memory leak ????
        dilemma = null;
    }
}
