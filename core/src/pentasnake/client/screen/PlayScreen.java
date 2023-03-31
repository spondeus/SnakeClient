package pentasnake.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.SnapshotArray;
import pentasnake.client.InputHandler;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.client.entities.SnakePart;
import pentasnake.client.messages.Message;
import pentasnake.client.messages.Pickup;
import pentasnake.client.messages.PickupRemove;
import pentasnake.client.messages.SnakeMove;
import pentasnake.client.socket.ClientSocket;
import pentasnake.client.entities.*;
import pentasnake.client.socket.Communication;
import pentasnake.pointsystem.*;
import pentasnake.pointsystem.PickupItems;

import java.util.*;

public class PlayScreen implements Screen {

    private AssetManager assetManager;
    public Label.LabelStyle labelStyle;
    protected Stage mainStage;
    protected Stage uiStage;
    private Label myPoints;
    private List<Label> pointsLabel = new ArrayList<>();
    private ArrayList<Snake> snakeList;
    private Table table;

    //private PickupSpawner pickupSpawner;

    private PickupSpawner pickupSpawner;
    private final SnakeGame game;
    List<Integer> points = new ArrayList<>();
    int counter = 0;

    private final Communication localClient;
    private final int myId;
    private boolean single;
    MySnapshotArray pickups = new MySnapshotArray();
    List<String> pickupCons;

    ClientSocket socket;

    int pickupUnderPicking = -1;
    private SpriteBatch batch = new SpriteBatch();
    SnapshotArray<WallPattern> wallList = new SnapshotArray<>();
    protected Wall wall;
    public boolean collidedWithWall;


    public PlayScreen(SnakeGame game, List<Snake> snakes, Communication localClient, boolean single) {
        this.single = single;
        mainStage = new Stage();
        uiStage = new Stage();

        this.pickupCons = pickupCons;
//        Gdx.app.log("PlayScreen", pickupCons.toString());

        this.localClient = localClient;
        if (localClient != null) {
            socket = localClient.getWebsocketClient();
            myId = socket.getId();
        } else myId = 0;

        this.game = game;
        snakeList = new ArrayList<>(snakes);
        if (snakeList.size() == 0)
            if (!single) Gdx.app.error("Server", "No snake found");

//                    snakeList.add(new Snake(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 20, game.getColor(), i));

        initialize();
    }

    public void initialize() {
        Gdx.app.log("Client/ snakeList", snakeList.toString());
        System.out.println("myid?" + myId);
        Snake x = snakeList.get(myId);
        Gdx.input.setInputProcessor(new InputHandler(x, localClient));
//        mainStage.addActor(x);
        labelInitialize();
        labelSort(sortPoints());
        // refreshPoints();
        wallList = Wall.spawnWalls();
        wall = new Wall(wallList);
        mainStage.addActor(wall);

        if (localClient == null) {
            pickupSpawner = new PickupSpawner(mainStage, wallList);
            pickupSpawner.spawnPickups();
            pickups = (MySnapshotArray) pickupSpawner.getPickups();
        }

        for (int i = 0; i < snakeList.size(); i++) {
            Snake snake = snakeList.get(i);
////            for (SnakePart part : snake.getParts()) {
//                switch (myId) {
//                    case 1:
////                        part.setDirectionVector(new Vector2(-1,0));
//                        break;
//                    case 2:
////                        part.rotate(-90);
//                        break;
//                    case 3:
////                        part.rotate(90);
//                        break;
//                }
//            }
            mainStage.addActor(snake);
        }
    }

    private void checkWallCollision(Wall wall) {
        for (Snake snake : snakeList) {
            if (snake.isGhostModeActive()) {
                continue;
            }
            for (WallPattern pattern : wall.getParts()) {
                for (WallPart part : pattern.getParts()) {
                    float x2 = snake.getHead().x % Gdx.graphics.getWidth();
                    if (x2 < 0) x2 += Gdx.graphics.getWidth();
                    float y2 = snake.getHead().y % Gdx.graphics.getHeight();
                    if (y2 < 0) y2 += Gdx.graphics.getHeight();
                    if ((Intersector.overlaps(new Circle(x2, y2, snake.getHead().radius), part))) {
                        collidedWithWall = true;
                        snake.setDeadSnake(true);
                        snake.setSpeed(0);
                        return;
                    }
                }
            }
        }
    }

    private void checkSnakeCollision() {
        for (int i = 0; i < snakeList.size(); i++) {
            Snake snake1 = snakeList.get(i);
            if (snake1.isGhostModeActive()) continue;
            for (int j = i + 1; j < snakeList.size(); j++) {
                Snake snake2 = snakeList.get(j);
                if (snake2.isGhostModeActive()) continue;
                SnakePart snake1head = snake1.getHead();
                for (SnakePart snake2part : snake2.getParts()) {
                    float x = snake1head.x % Gdx.graphics.getWidth();
                    if (x < 0) x += Gdx.graphics.getWidth();
                    float y = snake1head.y % Gdx.graphics.getHeight();
                    if (y < 0) y += Gdx.graphics.getHeight();
                    float x2 = snake2part.x % Gdx.graphics.getWidth();
                    if (x2 < 0) x2 += Gdx.graphics.getWidth();
                    float y2 = snake2part.y % Gdx.graphics.getHeight();
                    if (y2 < 0) y2 += Gdx.graphics.getHeight();
                    if ((Intersector.overlaps(new Circle(x, y, snake1head.radius),
                            new Circle(x2, y2, snake2part.radius)))) {
                        snake1.setDeadSnake(true);
                        snake1.setSpeed(0);
                        return;
                    }
                }
            }
        }
    }


    public void labelInitialize() {
        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        table = new Table();
        labelStyle.fontColor = Color.GOLD;
        myPoints = new Label(snakeList.get(myId).getPoints() + " p", labelStyle);
        pointsLabel = new LinkedList<>();
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

    public void labelSort(List<Snake> snakeList) {
        for (int i = 0; i < pointsLabel.size(); i++) {
            Label label = pointsLabel.get(i);
            //   Integer index=points.get(i);
            Snake mySnake = snakeList.get(i);
            label.setText(mySnake.getName() + ":" + mySnake.getPoints() + "p");
            label.setColor(mySnake.getParts().get(1).getColor());
        }
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void refreshPoints(int id, int pointChange) {
        // Kígyó keresése azonosító alapján
        Snake snake = snakeList.get(id);
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


    public List<Snake> sortPoints() {
        points = new ArrayList<>();
//        int maximum = Integer.MIN_VALUE;
//        int index = 0;
        List<Snake> copyList = new ArrayList<>(snakeList);
        Collections.sort(copyList);
        Collections.reverse(copyList);
//        while (!copyList.isEmpty()) {
////            for (int i = 0; i < copyList.size(); i++) {
////                if (copyList.get(i).getPoints() > maximum) {
////                    maximum = copyList.get(i).getPoints();
////                    index = i;
////                }
////            }
//            points.add(index);
//            copyList.remove(index);
//        }
        return copyList;
    }

    public void update(float dt) {
//        pickupCons = localClient.getWebsocketClient().getPickups();
//        newPickup();

//        pickups.begin();
        checkPickupCollision();
        myPoints.setText(snakeList.get(0).getPoints() + " p");
//        pickups.end();

//        if(localClient!=null) {
//            ClientSocket socket=localClient.getWebsocketClient();
//            if (snake.isLeftMove()) {
//                if (socket.isClosed()) Gdx.app.error("Client", "Connection closed");
//                socket.writeMsg(socket.getId(),new SnakeMove(true));
//            } else if (snake.isRightMove()) {
//                if (socket.isClosed()) Gdx.app.error("Client", "Connection closed");
//                socket.writeMsg(socket.getId(),new SnakeMove(false));
//            }
//        }
//        pickupSpawner.getPickups().end();
        checkWallCollision(wall);

        if (localClient != null) {
            if (snakeList.get(myId).isLeftMove()) {
                if (socket.isClosed()) Gdx.app.error("Client", "Connection closed");
                socket.writeMsg(myId, new SnakeMove(true));
            } else if (snakeList.get(myId).isRightMove()) {
                if (socket.isClosed()) Gdx.app.error("Client", "Connection closed");
                socket.writeMsg(myId, new SnakeMove(false));
            }
            while (!socket.getMsgQueue().isEmpty()) {
                Message msg = socket.getMsgQueue().poll();
                if (msg instanceof SnakeMove) {
                    SnakeMove snakeMove = (SnakeMove) msg;
                    Snake snake = snakeList.get(snakeMove.getId());
                    if (snakeMove.isLeft()) snake.turnLeft();
                    else snake.turnRight();
                    for (Snake sn:snakeList ) {
                        System.out.println(sn.getId()+" "+sn.isLeftMove());
                    }
                } else if (msg instanceof Pickup) putNewPickup((Pickup) msg);
                else if (msg instanceof PickupRemove) removePickup((PickupRemove) msg);

            }
//            for (Map<Integer, String> inputs : socket.getCurrentInputs()) {
//                for (Snake snake : snakeList) {
//                    if (inputs.get(snake.getId()) != null) {
//                        switch (inputs.get(snake.getId())) {
//                            case "A":
//                                snake.turnLeft();
//                                break;
//                            case "D":
//                                snake.turnRight();
//                                break;
//                            default:
//                                Gdx.app.error("Inputs", "Unknown input");
//                                break;
//                        }
//                        inputs.remove(snake.getId());
//                    }
//                }
//            }
        } else {
            if (snakeList.get(0).isLeftMove()) snakeList.get(0).turnLeft();
            else if (snakeList.get(0).isRightMove()) snakeList.get(0).turnRight();
        }
    }

    private void putNewPickup(Pickup msg) {
        Pickup pickup = msg;
        Type type = pickup.getType();
        PickupItems newPickup = null;
        switch (type) {
            case FOOD:
                newPickup = new Food(pickup.getPosition().x, pickup.getPosition().y,
                        mainStage, pickup.getPickUpId());
                break;
            case POISON:
                newPickup = new Poison(pickup.getPosition().x, pickup.getPosition().y,
                        mainStage, pickup.getPickUpId());
                break;
            case WEB:
                newPickup = new SpiderWeb(pickup.getPosition().x, pickup.getPosition().y,
                        mainStage, pickup.getPickUpId());
                break;
            case DRINK:
                newPickup = new EnergyDrink(pickup.getPosition().x, pickup.getPosition().y,
                        mainStage, pickup.getPickUpId());
                break;
            case ICE:
                newPickup = new IceBlock(pickup.getPosition().x, pickup.getPosition().y,
                        mainStage, pickup.getPickUpId());
                break;
            case GHOST:
                newPickup = new Ghost(pickup.getPosition().x, pickup.getPosition().y,
                        mainStage, pickup.getPickUpId());
                break;
            default:
                ;
        }
        pickups.add(newPickup);
    }

    private void removePickup(PickupRemove msg) {
        PickupRemove pickupRemove = msg;
        int cId = pickupRemove.getId();
        int pickupId = pickupRemove.getPickupId();
        PickupItems pickup = pickups.getById(pickupId);
        pickup.collectItem(snakeList.get(cId));
        pickup.applyEffect(snakeList.get(cId));
        pickups.removeValue(pickup, true);
    }

    private void checkPickupCollision() {
        for (PickupItems pickup : pickups) {
            for (Snake snake : snakeList) {
                float x2 = snake.getHead().x % Gdx.graphics.getWidth();
                if (x2 < 0) x2 += Gdx.graphics.getWidth();
                float y2 = snake.getHead().y % Gdx.graphics.getHeight();
                if (y2 < 0) y2 += Gdx.graphics.getHeight();
                if (Intersector.overlaps(new Circle(x2, y2, snake.getHead().radius),
                        pickup.getBoundaryRectangle())) {
                    if (localClient == null) {
                        pickup.collectItem(snake);
                        pickup.applyEffect(snake);
                        pickups.removeValue(pickup, true);
                    } else {
                        if (pickup.getId() == pickupUnderPicking) continue;
                        pickupUnderPicking = pickup.getId();
                        socket.writeMsg(myId,
                                new PickupRemove(pickup.getId()));
                    }
//                    System.out.println("Pickup:" + pickup);
                }
            }
        }
    }

    public void render(float dt) {
        update(dt);
        uiStage.act(dt);
        mainStage.act(dt);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        mainStage.draw();
        uiStage.draw();
        batch.end();
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
