package pentasnake.client.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.SnapshotArray;
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
        pickupSpawner = new PickupSpawner(mainStage);
        labelInitialize();

        /*WallParts bottomLeft1 = new WallParts(100, 100, 200, 50, Color.FIREBRICK);
        WallParts bottomLeft2 = new WallParts(100, 150, 50, 150, Color.FIREBRICK);

        WallParts bottomRight1 = new WallParts(900, 100, 200, 50, Color.FIREBRICK);
        WallParts bottomRight2 = new WallParts(1050, 150, 50, 150, Color.FIREBRICK);

        WallParts upperRight1 = new WallParts(900, 650, 200, 50, Color.FIREBRICK);
        WallParts upperRight2 = new WallParts(1050, 500, 50, 150, Color.FIREBRICK);

        WallParts upperLeft1 = new WallParts(100, 650, 200, 50, Color.FIREBRICK);
        WallParts upperLeft2 = new WallParts(100, 500, 50, 150, Color.FIREBRICK);

        WallParts straightLong = new WallParts(450, 500, 350, 50, Color.FIREBRICK);

        WallParts crossVertical = new WallParts(550, 600, 50, 150, Color.FIREBRICK);
        WallParts crossHorizontal = new WallParts(500, 650, 150, 50, Color.FIREBRICK);

        WallParts diagonal1 = new WallParts(220, 450, 50, 50, Color.FIREBRICK);
        WallParts diagonal2 = new WallParts(270, 400, 50, 50, Color.FIREBRICK);
        WallParts diagonal3 = new WallParts(320, 350, 50, 50, Color.FIREBRICK);
        WallParts diagonal4 = new WallParts(370, 300, 50, 50, Color.FIREBRICK);

        WallParts landingPad1 = new WallParts(600, 300, 150, 50, Color.FIREBRICK);
        WallParts landingPad2 = new WallParts(650, 150, 50, 200, Color.FIREBRICK);
        WallParts landingPad3 = new WallParts(600, 100, 150, 50, Color.FIREBRICK);

        wallPartsList.add(bottomLeft1);
        wallPartsList.add(bottomLeft2);
        wallPartsList.add(upperLeft1);
        wallPartsList.add(upperLeft2);
        wallPartsList.add(upperRight1);
        wallPartsList.add(upperRight2);
        wallPartsList.add(bottomRight1);
        wallPartsList.add(bottomRight2);
        wallPartsList.add(straightLong);
        wallPartsList.add(crossVertical);
        wallPartsList.add(crossHorizontal);
        wallPartsList.add(diagonal1);
        wallPartsList.add(diagonal2);
        wallPartsList.add(diagonal3);
        wallPartsList.add(diagonal4);
        wallPartsList.add(landingPad1);
        wallPartsList.add(landingPad2);
        wallPartsList.add(landingPad3);*/
        wall = new Wall(wallList);
        wallList = wall.spawnWalls();
        mainStage.addActor(wall);
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
        if(!snakeList.get(0).isGhostModeActive()) {
            Circle head = snakeList.get(0).getParts().first();
            for (WallParts wallPart : wall.getParts()) {
                if (Intersector.overlaps(head, wallPart)) {
                    collidedWithWall = true;
                    snakeList.get(0).setDeadSnake(true);
                    snakeList.get(0).setSpeed(0);
                    return;
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
