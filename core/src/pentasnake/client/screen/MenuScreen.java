package pentasnake.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pentasnake.client.SnakeGame;

public class MenuScreen implements Screen {

    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 100;
    private final SnakeGame game;

    private Stage stage;
    private Viewport viewport;
    Button startButton;
    TextureAtlas atlas;
    Skin skin;

    public MenuScreen(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new ExtendViewport(800,600);
        stage = new Stage();
        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), atlas);
        Texture snakeImg = new Texture(Gdx.files.internal("littleSnake.png"));
        Image image = new Image(snakeImg);

        startButton = new Button(skin);
        startButton.add("Start");
        startButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        startButton.setPosition(viewport.getScreenWidth()+450, viewport.getScreenHeight()+350);
        startButton.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent ev, float x, float y, int p, int b) {
//                game.setScreen(new PlayScreen());
//                return true;
//            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.S) game.setScreen(new PlayScreen());
                return super.keyDown(event, keycode);
            }
        });


        Button scoreButton = new Button(skin);
        scoreButton.add("Score Board");
        scoreButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        scoreButton.setPosition(viewport.getScreenWidth() + 450, viewport.getScreenHeight() + 200);
        scoreButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent ev, float x, float y, int p, int b) {
//                game.setScreen(new ScoreScreen());
                return true;
            }
        });


        stage.addActor(startButton);
        stage.addActor(scoreButton);
        stage.addActor(image);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isButtonPressed(Input.Keys.S)) new PlayScreen();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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