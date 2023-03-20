package pentasnake.client.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pentasnake.client.SnakeGame;

import java.awt.*;

public class TutorialScreen implements Screen {

    private final SnakeGame game;
    private final Viewport viewport = new ScreenViewport();

    private final Stage stage = new Stage(viewport);

    private final Stage stageSnakes = new Stage(viewport);

    private final Table table = new Table();

    private final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/dark-hdpi/Holo-dark-hdpi.atlas"));
    private final Skin skin = new Skin(Gdx.files.internal("skin/dark-hdpi/Holo-dark-hdpi.json"), atlas);

    private static final int iconHeight = 100;

    private static final int iconWidth = 100;

    public TutorialScreen(SnakeGame game) {
        this.game = game;
    }


    @Override
    public void show() {

        Label aimOfTheGame = new Label();
        Label controls = new Label();
        Label pickups = new Label();
        addButton("Got it, let's play! (Press P)").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen());
            }
        });
        addButton("Back to main menu (Press B)").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }
    private TextButton addButton(String name) {
        TextButton button = new TextButton(name, skin);
        table.add(button).width(500).height(500).padBottom(20);
        table.row();
        return button;
    }

    private void textureToImgGetInStage(Texture texture, float x, float y) {
        Image result = new Image(texture);
        result.setPosition(x, y);
        stageSnakes.addActor(result);
    }

    private void printPickupIcons(int x, int y) {
        textureToImgGetInStage(new Texture(Gdx.files.internal("food.png")), x * 0.1f - iconWidth / 2f, y * 0.5f - iconHeight / 2f);
        textureToImgGetInStage(new Texture(Gdx.files.internal("poison.png")), x * 0.1f - iconWidth / 2f, y * 0.6f - iconHeight / 2f);
        textureToImgGetInStage(new Texture(Gdx.files.internal("energydrink.png")), x * 0.1f - iconWidth / 2f, y * 0.7f - iconHeight / 2f);
        textureToImgGetInStage(new Texture(Gdx.files.internal("spiderweb.png")), x * 0.1f - iconWidth / 2f, y * 0.8f - iconHeight / 2f);
        textureToImgGetInStage(new Texture(Gdx.files.internal("iceblock.png")), x * 0.1f - iconWidth / 2f, y * 0.9f - iconHeight / 2f);
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.P)) game.setScreen(new PlayScreen());
        if (Gdx.input.isKeyPressed(Input.Keys.B)) game.setScreen(new MenuScreen(game));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update();
        stage.act(Gdx.graphics.getDeltaTime());
        stageSnakes.act(Gdx.graphics.getDeltaTime());
        stageSnakes.draw();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stageSnakes.clear();
        printPickupIcons(width, height);
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
