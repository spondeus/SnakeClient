package pentasnake.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import pentasnake.client.InputHandler;
import pentasnake.client.entities.Snake;
import pentasnake.pointsystem.PickupItems;
import pentasnake.pointsystem.PickupSpawner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PlayScreen implements Screen {

    AssetManager assetManager;
    public Label.LabelStyle labelStyle;
    protected Stage mainStage;
    protected Stage uiStage;
    private Label myPoints;
    private List<Label> pointsLabel;
    private ArrayList<Snake> snakeList;
    private Table table;
    List<Integer> points = new ArrayList<>();
    private PickupSpawner pickupSpawner;

    public PlayScreen() {
        mainStage = new Stage();
        uiStage = new Stage();
        initialize();
    }

    public void initialize() {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);
        snakeList = new ArrayList<Snake>();
        Snake snake = new Snake(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 20, Color.GREEN);
        snakeList.add(snake);
        mainStage.addActor(snake);
        Gdx.input.setInputProcessor(new InputHandler(snake));
        pickupSpawner = new PickupSpawner(mainStage);
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

        pointsLabel.add(myPoints);
        Table pointTable = new Table();
        for (Snake snake : snakeList) {
            Label label = new Label(snake.getName() + ":" + snake.getPoints() + "p", labelStyle);
            label.setColor(snake.getColor());
            pointsLabel.add(label);
            pointTable.add(label).row();
        }

        table.add(myPoints).top();
        table.add().expand();
        table.add(pointTable).top();
        table.setFillParent(true);
        uiStage.addActor(table);
    }
    public void labelSort(){
        for (int i = 0; i <pointsLabel.size(); i++) {
                Label label=pointsLabel.get(i);
                Integer index=points.get(i);
               Snake mySnake = snakeList.get(index);
               label.setText(mySnake.getName()+":"+mySnake.getPoints()+"p");
               label.setColor(mySnake.getColor());
        }
    }
    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void refreshPoints(int id, int pointChange) {
        // Kígyó keresése azonosító alapján
        Optional<Snake> optionalSnake = snakeList.stream().filter(snake -> snake.getId() == id).findFirst();
        if (optionalSnake.isPresent()) {
            Snake snake = optionalSnake.get();
            // Kígyó pontjainak frissítése
            snake.setPoints(snake.getPoints() + pointChange);
            // Label frissítése
            int index = 0;
            for (int i = 0; i < snakeList.size(); i++) {
                if (snakeList.get(i).equals(snake)) {
                    index = i;
                }
            }
            Label optionalLabel = pointsLabel.get(index);
            optionalLabel.setText(snake.getName() + ": " + snake.getPoints() + "p");
        }
    }

    public void sortPoints() {
        int maximum = Integer.MIN_VALUE;
        int index = 0;
        List<Snake> copyList = new ArrayList<>(snakeList);
        while (!copyList.isEmpty()) {
            for (int i = 0; i < copyList.size(); i++) {
                if (copyList.get(i).getPoints() > maximum) {
                    maximum = copyList.get(i).getPoints();
                    index = i;
                }
            }
            points.add(index);
            copyList.remove(index);
        }
    }
    public void update(float dt) {
        for (PickupItems pickup : pickupSpawner.getPickups()) {
            if (Intersector.overlaps(snakeList.get(0).getHead(), pickup.getBoundaryRectangle())) {
                pickup.collectItem(snakeList.get(0));
                pickup.applyEffect(snakeList.get(0));
                pickupSpawner.getPickups().removeValue(pickup, true);
            }
        }
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
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.removeProcessor(uiStage);
        im.removeProcessor(mainStage);
    }

    @Override
    public void dispose() {
    }
}
