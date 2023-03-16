package pentasnake.client.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import pentasnake.client.SnakeGame;


public class MenuScreen implements Screen {

    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 100;
    private final SnakeGame game;

    private Stage stage;
    OrthographicCamera camera = new OrthographicCamera();
    FitViewport viewport = new FitViewport(800, 600, camera);
    Button startButton, scoreButton, settingsButton;
    TextureAtlas atlas;
    Skin skin;

    public MenuScreen(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), atlas);
        Texture snakeImg = new Texture(Gdx.files.internal("littleSnake.png"));
        Image image = new Image(snakeImg);


        startButton = new Button(skin);
        startButton.add("START (press S)");
        startButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        startButton.setPosition(viewport.getScreenWidth() + 450, viewport.getScreenHeight() + 450);
        startButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent ev, float x, float y, int p, int b) {
                game.setScreen(new PlayScreen());
                return true;
            }
        });


        scoreButton = new Button(skin);
        scoreButton.add("SCOREBOARD (press B)");
        scoreButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        scoreButton.setPosition(viewport.getScreenWidth() + 450, viewport.getScreenHeight() + 300);
        scoreButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent ev, float x, float y, int p, int b) {
                game.setScreen(new ScoreboardScreen());
                return true;
            }
        });

        settingsButton = new Button(skin);
        settingsButton.add("SETTIGNS (press T)");
        settingsButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        settingsButton.setPosition(viewport.getScreenWidth() + 450, viewport.getScreenHeight() + 150);
        settingsButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent ev, float x, float y, int p, int b) {
//                game.setScreen(new SettingsScreen());
                return true;
            }
        });


        stage.addActor(startButton);
        stage.addActor(scoreButton);
        stage.addActor(settingsButton);
        stage.addActor(image);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    public void update(){
        if (Gdx.input.isKeyPressed(Input.Keys.S)) game.setScreen(new PlayScreen());
        if (Gdx.input.isKeyPressed(Input.Keys.B)) game.setScreen(new ScoreboardScreen());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        stage.dispose();
    }
}