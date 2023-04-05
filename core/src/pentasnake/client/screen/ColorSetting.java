package pentasnake.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pentasnake.client.SnakeGame;

import java.util.Arrays;
import java.util.List;


public class ColorSetting implements Screen {
    SnakeGame game;
    private final Table tableScore = new Table();



    float buttonWeight = 100;
    float buttonHeight = 100;
    private final Viewport viewport = new ScreenViewport();


    private final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/dark-hdpi/Holo-dark-hdpi.atlas"));

    private final Skin skin = new Skin(Gdx.files.internal("skin/dark-hdpi/Holo-dark-hdpi.json"), atlas);

    Stage stage = new Stage(viewport);

    private final Label colorLabel = new Label("COLOR", skin, "default-font", Color.WHITE);
   // private final Label getColorLabel=new Label("Color",skin.getFont());

    public ColorSetting(SnakeGame game) {
        this.game = game;

    }

    @Override
    public void show() {
        colorLabel.setPosition(Gdx.graphics.getWidth() / 2f - colorLabel.getWidth() / 2f-20, Gdx.graphics.getHeight() - colorLabel.getHeight());
        colorLabel.setColor(game.getColor());
        colorLabel.setFontScale(1.5f,1.5f);
        stage.addActor(colorLabel);

        Gdx.input.setInputProcessor(stage);
        TextButton backButton = new TextButton("(B)ack to menu", skin);
        backButton.setPosition(Gdx.graphics.getWidth() / 2f - backButton.getWidth() / 2f, tableScore.getHeight());
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen((SnakeGame) game));
            }
        });
        stage.addActor(backButton);
        colorButtons();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void colorButtons(){
        addSettingsButton(Color.BLUE, "Collors/blue2.png", 0.25f, 0.25f);
        addSettingsButton(Color.BROWN, "Collors/brown2.png", 0.5f, 0.25f);
        addSettingsButton(Color.GRAY, "Collors/gray2.png", 0.75f, 0.25f);
        addSettingsButton(Color.GREEN, "Collors/green2.png", 0.25f, 0.5f);
        addSettingsButton(Color.ORANGE, "Collors/orange3.png", 0.5f, 0.5f);
        addSettingsButton(Color.PINK, "Collors/pink2.png", 0.75f, 0.5f);
        addSettingsButton(Color.PURPLE, "Collors/purple2.png", 0.25f, 0.75f);
        addSettingsButton(Color.RED, "Collors/red2.jpg", 0.5f, 0.75f);
        addSettingsButton(Color.YELLOW, "Collors/yellow.png", 0.75f, 0.75f);

    }

    private void addSettingsButton(final Color color, final String colorPath, float x, float y) {
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(colorPath)));
        ImageButton settingsButton = new ImageButton(drawable);
        settingsButton.setSize(buttonWeight, buttonHeight);
        settingsButton.setPosition((stage.getWidth() * x) - settingsButton.getWidth()/2,
                (stage.getHeight() * y) - settingsButton.getHeight()/2);

//        settingsButton.setPosition((Gdx.graphics.getWidth() - sprite.getRegionWidth()) / 2.0f,400);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(color);
                game.setColor(color);
                colorLabel.setColor(color);
            }
        });
        stage.addActor(settingsButton);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.clear();
        show();
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
//        spriteBatch.dispose();
        stage.dispose();
    }

}
