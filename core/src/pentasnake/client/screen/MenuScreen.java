package pentasnake.client.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pentasnake.client.SnakeGame;


public class MenuScreen implements Screen {

    private final SnakeGame game;

    private Stage stage;
    private final OrthographicCamera camera = new OrthographicCamera();

    private Viewport viewport;
    private Table table;
    TextureAtlas atlas;
    Skin skin;

    public MenuScreen(SnakeGame game) {
        this.game = game;
    }

    public void show() {
        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), atlas);
        Texture snakeImg = new Texture(Gdx.files.internal("littleSnake.png"));
        Image image = new Image(snakeImg);
        viewport = new ExtendViewport(1280,720);
        stage = new Stage(viewport);

        table = new Table();
        table.setFillParent(true);

        stage.addActor(table);
        stage.addActor(image);

        addButton("START (Press S)").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new PlayScreen());
            }
        });
        addButton("SETTINGS (Press T)").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new SettingsScreen());
            }
        });
        addButton("SCOREBOARD (Press B)").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new ScoreboardScreen());
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    private TextButton addButton(String name){
        TextButton button = new TextButton(name, skin);
        table.add(button).width(200).height(80).padBottom(10);
        table.row();
        return button;
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
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) game.setScreen(new PlayScreen());
//        if (Gdx.input.isKeyPressed(Input.Keys.B)) game.setScreen(new ScoreboardScreen());
//        if (Gdx.input.isKeyPressed(Input.Keys.T)) game.setScreen(new SettingsScreen());
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

