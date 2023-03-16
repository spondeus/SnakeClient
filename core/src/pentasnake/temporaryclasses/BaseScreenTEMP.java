package pentasnake.temporaryclasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.awt.*;

public abstract class BaseScreenTEMP implements Screen {

    protected Stage mainStage;
    protected Stage uiStage;

    public BaseScreenTEMP()
    {
        mainStage = new Stage();
        uiStage = new Stage();
        initialize();
    }
    public abstract void initialize();
    public abstract void update(float delta);

    public void render(float delta) {
        uiStage.act(delta);
        mainStage.act(delta);
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainStage.draw();
        uiStage.draw();
    }

    // in your GameScreen constructor
    Label scoreLabel = new Label("Score: 0", 1000);
    // stage.addActor(scoreLabel);

    // in your update() method, after updating the score
    //scoreLabel.setText("Score: " + score);

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
