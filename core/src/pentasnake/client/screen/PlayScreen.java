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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import pentasnake.client.entities.Snake;
import pentasnake.pointsystem.Food;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayScreen extends Game implements Screen,InputProcessor  { //InputProcessor
    private AssetManager assetManager;
    public Label.LabelStyle labelStyle;
    protected Stage mainStage;
    protected Stage uiStage;
    private Label myPoints;
    private List<Label> pointsLabel;
    private ArrayList<Snake> snakeList;
    private Table table;

    public PlayScreen()
    {
        mainStage = new Stage();
        uiStage = new Stage();
        initialize();
    }
    public void initialize() {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor( im );
        snakeList = new ArrayList<Snake>();
        Snake snake = new  Snake(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,20, Color.GREEN);
        snakeList.add(snake);
        mainStage.addActor(snake);
        labelInitialize();
    }
    public void labelInitialize(){
        labelStyle = new Label.LabelStyle();
        labelStyle.font= new BitmapFont();
        table =new Table();
        labelStyle.fontColor=Color.GOLD;
       // pointsLabel= new Label(snakeList.get(0).getPoints()+" p",labelStyle);
        pointsLabel = new LinkedList<>();
        myPoints = new Label(snakeList.get(0).getPoints()+" p",labelStyle);
        pointsLabel.add(myPoints);
        pointsLabel.add(new Label ("10 p",labelStyle));
        pointsLabel.add(new Label ("20 p",labelStyle));
        pointsLabel.add(new Label ("30 p",labelStyle));
        // pointsLabel.setPosition(Gdx.graphics.getWidth()-40,Gdx.graphics.getHeight()-30);
        table.add(myPoints);
        table.add().expandX();
        table.add(pointsLabel.get(0)).row();
        table.add();
        table.add();
        table.add(pointsLabel.get(1)).row();
        table.add();
        table.add();
        table.add(pointsLabel.get(2)).row();
        table.add();
        table.add();
        table.add(pointsLabel.get(3)).row();
        table.add();
        table.add();
        table.setFillParent(true);
       uiStage.addActor(table);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
    public  void update(float dt){}
    public void render(float dt)
    {
        uiStage.act(dt);
        mainStage.act(dt);
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
