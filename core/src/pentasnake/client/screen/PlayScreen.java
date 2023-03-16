package pentasnake.client.screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import pentasnake.client.entities.Snake;
import pentasnake.pointsystem.Food;

import java.util.ArrayList;

public class PlayScreen extends Game implements Screen,InputProcessor  { //InputProcessor
    private AssetManager assetManager;
    public Label.LabelStyle labelStyle;
    protected Stage mainStage;
    protected Stage uiStage;

    private ArrayList<Snake> snakeList;


    public PlayScreen()
    {
        mainStage = new Stage();
        uiStage = new Stage();
        initialize();
    }
    public void initialize()
    {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor( im );
        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        AssetManager assetManager = new AssetManager();
        snakeList = new ArrayList<Snake>();
        Snake snake = new  Snake(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,20, Color.BLUE);
        snakeList.add(snake);
        mainStage.addActor(snake);
        System.out.println( mainStage.getActors().size);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
    public  void update(float dt){}
    public void render(float dt)
    {
        uiStage.act(dt); // actorokat frissiti és update
        mainStage.act(dt); // -.-
       // update(dt); //
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainStage.draw();
        uiStage.draw();
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
    @Override
    public void show() {
      //  InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();
     InputMultiplexer   im = new InputMultiplexer();
        im.addProcessor(this);
        im.addProcessor(uiStage);
        im.addProcessor(mainStage);
    }

    @Override
    public void create() {
        
    }

    //    @Override
//    public void render(float delta) {
//
//    }
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {

    }
    @Override
    public void hide() {
        InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();
        im.removeProcessor(this);
        im.removeProcessor(uiStage);
        im.removeProcessor(mainStage);
    }
    @Override
    public void dispose() {
    }
}
