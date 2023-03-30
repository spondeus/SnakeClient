package pentasnake.client.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.SnapshotArray;
import lombok.Getter;
import pentasnake.client.InputHandler;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.*;
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
    private SpriteBatch batch = new SpriteBatch();
    SnapshotArray<WallPattern> wallList = new SnapshotArray<>();
    protected Wall wall;
    public boolean collidedWithWall;


    public PlayScreen(SnakeGame game, List<Snake> snakes, Communication localClient, boolean single) {
        this.single = single;
        mainStage = new Stage();
        uiStage = new Stage();

        this.localClient = localClient;
        if(localClient!=null) myId = localClient.getWebsocketClient().getId();
        else myId=0;

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

        snakeList = new ArrayList<>();
        Snake snake = new Snake(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 20, Color.GREEN, myId);

        snakeList.add(snake);

        Gdx.input.setInputProcessor(new InputHandler(snake, localClient));
        labelInitialize();

        wallList = Wall.spawnWalls();
        wall = new Wall(wallList);
        mainStage.addActor(wall);

        pickupSpawner = new PickupSpawner(mainStage, wallList);

        mainStage.addActor(snake);
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
        for (PickupItems pickup : pickupSpawner.getPickups()) {
            if (Intersector.overlaps(snakeList.get(0).getHead(), pickup.getBoundaryRectangle())) {
                pickup.collectItem(snakeList.get(0));
                pickup.applyEffect(snakeList.get(0));
                pickupSpawner.pickupCollected(pickup);
                pickupSpawner.getPickups().removeValue(pickup, true);
            }
        }
        pickupSpawner.spawnPickups();
        myPoints.setText(snakeList.get(0).getPoints() + " p");
        pickupSpawner.getPickups().end();
        wallCollision(wall);

        if(localClient!=null){
            for (Map<Integer, String> inputs : localClient.getWebsocketClient().getCurrentInputs()) {
                for (Snake snake : snakeList) {
                    if (inputs.get(snake.getId()) != null) {
                        switch (inputs.get(snake.getId())) {
                            case "A":
                                snake.turnLeft();
                                break;
                            case "D":
                                snake.turnRight();
                                break;
                            default:
                                Gdx.app.error("Inputs", "Unknown input");
                                break;
                        }
                        inputs.remove(snake.getId());
                    }
                }
            }
        }
    }

    public void wallCollision(Wall wall) {
        // checks for wall collision
        if (!snakeList.get(0).isGhostModeActive()) {
            Circle head = snakeList.get(0).getParts().first();
            for (WallPattern pattern : wall.getParts()) {
                for (WallPart part : pattern.getParts()) {
                    if (Intersector.overlaps(head, part)) {
                        collidedWithWall = true;
                        snakeList.get(0).setDeadSnake(true);
                        snakeList.get(0).setSpeed(0);
                        return;
                    }
                }
            }
        }
    }

    public void render(float dt) {
        uiStage.act(dt);
        update(dt);
        mainStage.act(dt);
        update(dt);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        mainStage.draw();
        batch.end();
        uiStage.draw();
    }


    @Override
    public void show() {
        /*InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(uiStage);
        im.addProcessor(mainStage);

         */

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
        /*
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.removeProcessor(uiStage);
        im.removeProcessor(mainStage);

         */

    }

    @Override
    public void dispose() {
    }
}
