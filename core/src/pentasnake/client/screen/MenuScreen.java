package pentasnake.client.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pentasnake.client.SnakeGame;


public class MenuScreen implements Screen {

    private final SnakeGame game;

    private final Viewport viewport = new ScreenViewport();
    private final Stage stage = new Stage(viewport);
    private final Stage stageSnakes = new Stage(viewport);


    private final Table table = new Table();
    private final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/dark-hdpi/Holo-dark-hdpi.atlas"));
    private final Skin skin = new Skin(Gdx.files.internal("skin/dark-hdpi/Holo-dark-hdpi.json"), atlas);

    private static final int SNAKE_HEAD_WIDTH = 200;
    private static final int SNAKE_HEAD_HEIGHT = 214;


    public MenuScreen(SnakeGame game) {
        this.game = game;
    }

    public void show() {
        addButton("START (Press S)").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new PlayScreen());
            }
        });
        addButton("SETTINGS (Press T)").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new SettingsScreen());
            }
        });
        addButton("SCOREBOARD (Press B)").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new ScoreboardScreen());
            }
        });

        table.setFillParent(true);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    private TextButton addButton(String name) {
        TextButton button = new TextButton(name, skin);
        table.add(button).width(350).height(80).padBottom(10);
        table.row();
        return button;
    }

    private void textureToImgGetInStage(Texture texture, float x, float y) {
        Image result = new Image(texture);
        result.setPosition(x, y);
        stageSnakes.addActor(result);
    }

    private void snakesPrint(int x, int y) {
        textureToImgGetInStage(new Texture(Gdx.files.internal("temp/headR.png")), x * 0.5f - SNAKE_HEAD_WIDTH / 2f, y * 0.85f - SNAKE_HEAD_HEIGHT / 2f);
        textureToImgGetInStage(new Texture(Gdx.files.internal("temp/headBGray.png")), x * 0.25f - SNAKE_HEAD_WIDTH / 2f, y * 0.60f - SNAKE_HEAD_HEIGHT / 2f);
        textureToImgGetInStage(new Texture(Gdx.files.internal("temp/headBl.png")), x * 0.75f - SNAKE_HEAD_WIDTH / 2f, y * 0.60f - SNAKE_HEAD_HEIGHT / 2f);
        textureToImgGetInStage(new Texture(Gdx.files.internal("temp/headGB.png")), x * 0.375f - SNAKE_HEAD_WIDTH / 2f, y * 0.20f - SNAKE_HEAD_HEIGHT / 2f);
        textureToImgGetInStage(new Texture(Gdx.files.internal("temp/headYBr.png")), x * 0.625f - SNAKE_HEAD_WIDTH / 2f, y * 0.20f - SNAKE_HEAD_HEIGHT / 2f);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();

        stage.act(Gdx.graphics.getDeltaTime());
        stageSnakes.act(Gdx.graphics.getDeltaTime());
        stageSnakes.draw();
        stage.draw();
    }

    public void update() {
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) game.setScreen(new PlayScreen());
//        if (Gdx.input.isKeyPressed(Input.Keys.B)) game.setScreen(new ScoreboardScreen());
//        if (Gdx.input.isKeyPressed(Input.Keys.T)) game.setScreen(new SettingsScreen());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stageSnakes.clear();
        snakesPrint(width, height);
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
        stageSnakes.dispose();
        skin.dispose();
        atlas.dispose();
    }
}

