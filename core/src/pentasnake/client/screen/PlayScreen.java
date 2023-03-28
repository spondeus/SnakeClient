package pentasnake.client.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import pentasnake.client.InputHandler;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.client.entities.SnakePart;
import pentasnake.client.socket.Communication;
import pentasnake.pointsystem.PickupItems;
import pentasnake.pointsystem.PickupSpawner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


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

    private final SnakeGame game;

    private final Communication localClient;
    private final int myId;

    private boolean single;

    public PlayScreen(SnakeGame game, List<Snake> snakes, Communication localClient, boolean single) {
        this.single = single;
        mainStage = new Stage();
        uiStage = new Stage();

        this.localClient = localClient;
        if (localClient != null) myId = localClient.getWebsocketClient().getId();
        else myId = 0;

        this.game = game;
        snakeList = new ArrayList<>(snakes);
        if (snakeList.size() == 0)
            if (!single) Gdx.app.error("Server", "No snake found");
            else
                snakeList.add(new Snake(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 20, Color.GREEN, 0));

        initialize();
    }

    public void initialize() {
        /*InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);

         */

        Gdx.app.log("Client/ snakeList", snakeList.toString());
        System.out.println("myid?" + myId);
        for (Snake x : snakeList) {
            if (x.getId() == myId) {
                for (SnakePart p : x.getParts())
                    p.setColor(Color.BLUE);
                Gdx.input.setInputProcessor(new InputHandler(x, localClient));
            }
            mainStage.addActor(x);
        }

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

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void update(float dt) {
        pickupSpawner.getPickups().begin();
        for (PickupItems pickup : pickupSpawner.getPickups()) {
            Snake snake = snakeList.get(0);
            float x2 = snake.getHead().x % Gdx.graphics.getWidth();
            if (x2 < 0) x2 += Gdx.graphics.getWidth();
            float y2 = snake.getHead().y % Gdx.graphics.getHeight();
            if (y2 < 0) y2 += Gdx.graphics.getHeight();
            if (Intersector.overlaps(new Circle(x2, y2, snake.getHead().radius),
                    pickup.getBoundaryRectangle())) {
                pickup.collectItem(snake);
                pickup.applyEffect(snake);
                System.out.println("Pickup:" + pickup);
                pickupSpawner.getPickups().removeValue(pickup, true);
            }
        }
        pickupSpawner.spawnPickups();
        myPoints.setText(snakeList.get(0).getPoints() + " p");
        pickupSpawner.getPickups().end();

        if (localClient != null) {

            if (snakeList.get(0).isLeftMove()) {
                if (localClient.getWebsocketClient().isClosed()) Gdx.app.error("Client", "Connection closed");
                localClient.send("inputA," + localClient.getWebsocketClient().getId());

                //snake.turnLeft();
            } else if (snakeList.get(0).isRightMove()) {
                if (localClient.getWebsocketClient().isClosed()) Gdx.app.error("Client", "Connection closed");
                localClient.send("inputD," + localClient.getWebsocketClient().getId());
            }


            for (Map<Integer, String> inputs : localClient.getWebsocketClient().getCurrentInputs()) {
                for (Snake snake : snakeList) {
                    if (inputs.get(snake.getId()) != null) {
                        switch (inputs.get(snake.getId())) {
                            case "A":
                                snakeList.get(0).turnLeft();
                                break;
                            case "D":
                                snakeList.get(0).turnRight();
                                break;
                            default:
                                Gdx.app.error("Inputs", "Unknown input");
                                break;
                        }
                        inputs.remove(snake.getId());
                    }
                }
            }
        } else {
            if (snakeList.get(0).isLeftMove()) snakeList.get(0).turnLeft();
            else if (snakeList.get(0).isRightMove()) snakeList.get(0).turnRight();
        }




    }

    public void render(float dt) {
        uiStage.act(dt);
        update(dt);
        mainStage.act(dt);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainStage.draw();
        uiStage.draw();
    }


    @Override
    public void show() {
        for (Snake snake : snakeList) {
            Gdx.app.log("Snake", String.valueOf(snake.getId()));
        }
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
    }

    @Override
    public void dispose() {
    }
}
