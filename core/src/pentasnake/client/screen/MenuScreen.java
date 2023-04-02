package pentasnake.client.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pentasnake.client.SnakeGame;


public class MenuScreen implements Screen {

    private final SnakeGame game;

    private Viewport viewport = new ScreenViewport();
    private final Stage stage = new Stage(viewport);
    private final Stage stageSnakes = new Stage(viewport);


    private final Table table = new Table();
    private final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/dark-hdpi/Holo-dark-hdpi.atlas"));
    private final Skin skin = new Skin(Gdx.files.internal("skin/dark-hdpi/Holo-dark-hdpi.json"), atlas);

    private static final int SNAKE_HEAD_WIDTH = 200;
    private static final int SNAKE_HEAD_HEIGHT = 214;


    public MenuScreen(SnakeGame game) {
        this.game = game;
        addButton("ST(A)RT").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LobbyScreen(game,false));
            }
        });
        addButton("(T)UTORIAL").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TutorialScreen(game));
            }
        });
        addButton("S(C)OREBOARD").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScoreboardScreen(game));
            }
        });
    }

    public void show() {
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

    private void addSettingsButton() {
        ImageButton settingsButton = new ImageButton(skin);
        settingsButton.setSize(100f, 100f);
        settingsButton.setPosition(Gdx.graphics.getWidth() / 2f - settingsButton.getWidth() / 2, Gdx.graphics.getHeight() / 5f);
        settingsButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings_icon.png"))));
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ColorSetting(game));
            }
        });
        stage.addActor(settingsButton);
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
        if (Gdx.input.isKeyPressed(Input.Keys.A)) game.setScreen(new LobbyScreen(game,true));
        if (Gdx.input.isKeyPressed(Input.Keys.C)) game.setScreen(new ScoreboardScreen(game));
        if (Gdx.input.isKeyPressed(Input.Keys.T)) game.setScreen(new TutorialScreen(game));
        if (Gdx.input.isKeyPressed(Input.Keys.S)) game.setScreen(new ColorSetting(game));
    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, true);
        stageSnakes.clear();
        snakesPrint(width, height);
        if (stage.getActors().size > 1) stage.getActors().get(1).remove(); //stage.getActors().get(1) = settingsButton
        addSettingsButton();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        viewport=null;
    }

    @Override
    public void dispose() {
        stage.dispose();
        stageSnakes.dispose();
        skin.dispose();
        atlas.dispose();
    }
}

