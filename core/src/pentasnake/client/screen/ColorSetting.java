package pentasnake.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import pentasnake.client.SnakeGame;

import java.util.Arrays;
import java.util.List;

public class ColorSetting implements Screen  {
    SnakeGame game;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    SpriteBatch spriteBatch;
    float horizontalUnit;
    float verticalUnit;
    float buttonWeight;
    float buttonHeight;
    Stage stage = new Stage();


    public ColorSetting(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0, 2, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        addSettingsButton(Color.BLUE, "Collors/blue2.png",  0.25,0.2);
        addSettingsButton(Color.BROWN, "Collors/brown2.png", 0.4, 0.2);
        addSettingsButton(Color.GRAY, "Collors/gray2.png", 0.55, 0.2);
        addSettingsButton(Color.GREEN, "Collors/green2.png", 0.25, 0.4);
        addSettingsButton(Color.ORANGE, "Collors/orange3.png", 0.4, 0.4);
        addSettingsButton(Color.PINK, "Collors/pink2.png", 0.55, 0.4);
        addSettingsButton(Color.PURPLE, "Collors/purple2.png", 0.25, 0.6);
        addSettingsButton(Color.RED, "Collors/red2.jpg", 0.4, 0.6);
        addSettingsButton(Color.YELLOW, "Collors/yellow.png", 0.55, 0.6);
        stage.draw();
        Gdx.input.setInputProcessor(stage);
    }

    private void addSettingsButton(final Color color, final String colorPath, double x, double y){
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(colorPath)));
        ImageButton settingsButton = new ImageButton(drawable);
        settingsButton.setSize(buttonWeight,buttonHeight);
        settingsButton.setPosition((float)(stage.getWidth() * x), (float)(stage.getHeight() * y));

//        settingsButton.setPosition((Gdx.graphics.getWidth() - sprite.getRegionWidth()) / 2.0f,400);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(color);
                game.setColor(color);
            }
        });
        stage.addActor(settingsButton);
    }

    @Override
    public void resize(int width, int height) {
        horizontalUnit = (width)/8;
        verticalUnit = (height)/13.0f;
        buttonHeight = verticalUnit * 3;
        buttonWeight = horizontalUnit*2;
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

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.X)) game.setScreen(new MenuScreen(game));
    }
}
