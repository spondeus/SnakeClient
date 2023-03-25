package pentasnake.client.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import pentasnake.client.InputHandler;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.client.entities.Wall;
import pentasnake.pointsystem.Food;
import pentasnake.pointsystem.PickupItems;
import pentasnake.pointsystem.PickupSpawner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;

public class PlayScreen implements Screen {

    private AssetManager assetManager;
    public Label.LabelStyle labelStyle;
    protected Stage mainStage;
    protected Stage uiStage;
    private Label myPoints;
    private List<Label> pointsLabel;
    private ArrayList<Snake> snakeList;
    private Table table;

    private PickupSpawner pickupSpawner;


    public PlayScreen() {
        mainStage = new Stage();
        uiStage = new Stage();
        initialize();
    }

    /*private boolean pickupCollision() {
        for (int i = 0; i < pickupSpawner.getPickups().size; i++) {
            for (int j = 0; j < pickupSpawner.getPickups().size; j++) {
                if (Math.abs(i - j) < 2) continue;
                if (pickupSpawner.getPickups().get(i).overlaps(pickupSpawner.getPickups().get(j))) {
                    return true;
                }
            }
        }
        return false;
    }*/

    public void initialize() {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);
        snakeList = new ArrayList<Snake>();
        Snake snake = new Snake(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 20, Color.GREEN);
        Wall wall = new Wall(200, 200, 300,50);
        snakeList.add(snake);
        mainStage.addActor(snake);
        mainStage.addActor(wall);
        Gdx.input.setInputProcessor(new InputHandler(snake));
        pickupSpawner = new PickupSpawner(mainStage);
        pickupSpawner.spawnPickups();
        labelInitialize();
    }

    public void labelInitialize() {
        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        table = new Table();
        labelStyle.fontColor = Color.GOLD;
        myPoints = new Label(snakeList.get(0).getPoints() + " p", labelStyle);
        pointsLabel = new LinkedList<>();
        pointsLabel.add(new Label(snakeList.get(0).getPoints() + " p", labelStyle));
        pointsLabel.add(new Label("10 p", labelStyle));
        pointsLabel.add(new Label("20 p", labelStyle));
        pointsLabel.add(new Label("30 p", labelStyle));
        Table pointTable = new Table();
        for (Label label : pointsLabel) {
            pointTable.add(label).row();
        }
        table.add(myPoints).top();
        table.add().expand();
        table.add(pointTable).top();
        table.setFillParent(true);
        uiStage.addActor(table);
    }

    public void update(float dt) {
        for (PickupItems pickup: pickupSpawner.getPickups()  ) {
            if(Intersector.overlaps(snakeList.get(0).getHead(),pickup.getBoundaryRectangle())){
                pickup.collectItem(snakeList.get(0));
                pickup.applyEffect(snakeList.get(0));
                pickupSpawner.getPickups().removeValue(pickup,true);
            }
        }
        pickupSpawner.spawnPickups();
        myPoints.setText(snakeList.get(0).getPoints() + " p");
    }

    public void render(float dt) {
        uiStage.act(dt);
        mainStage.act(dt);
        update(dt);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainStage.draw();
        uiStage.draw();
    }



    @Override
    public void show() {
        //  InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(uiStage);
        im.addProcessor(mainStage);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        InputProcessor inputProcessor = Gdx.input.getInputProcessor();
        if (inputProcessor instanceof InputMultiplexer) {
            InputMultiplexer inputMultiplexer = (InputMultiplexer) inputProcessor;
            inputMultiplexer.removeProcessor(uiStage);
            inputMultiplexer.removeProcessor(mainStage);
        }
    }

    @Override
    public void dispose() {
    }
}
