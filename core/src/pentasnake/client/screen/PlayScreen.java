package pentasnake.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.Timer;
import pentasnake.client.InputHandler;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.client.entities.SnakePart;
import pentasnake.client.messages.*;
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

    SnapshotArray<Snake> diedSnakes = new SnapshotArray<>();


    private SpriteBatch batch = new SpriteBatch();
    SnapshotArray<WallPattern> wallList = new SnapshotArray<>();
    protected Wall wall;
    public boolean collidedWithWall;

    private final int gameEndCode = 999;

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

//        wallList = Wall.spawnWalls();
//        wall = new Wall(wallList);
//        mainStage.addActor(wall);
//
        if (localClient == null) {
            pickupSpawner = new PickupSpawner(mainStage, wallList);
            pickupSpawner.spawnPickups();
            pickups = (MySnapshotArray) pickupSpawner.getPickups();
        }

        for (int i = 0; i < snakeList.size(); i++) {
            Snake snake = snakeList.get(i);
//            for (SnakePart part : snake.getParts()) {
            switch (snake.getId()) {
                case 1:
                    snake.getHead().rotate(180);
                    break;
                case 2:
                    snake.getHead().rotate(-90);
                    break;
                case 3:
                    snake.getHead().rotate(90);
                    break;
                case 4:
                    snake.getHead().rotate(90);
                    break;
            }
            mainStage.addActor(snake);
        }
    }

    private void checkWallCollision(Wall wall) {
//        for (int i = 0; i < snakeList.size(); i++) {
        Snake snake = snakeList.get(myId);
        if (snake.isGhostModeActive() || snake.isDeadSnake()) {
            return;//continue;
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
                    dieMessage(myId, snake);
                    snake.setSpeed(0);
                    mainStage.getActors().removeValue(snake, true);
                    return;
                }
            }
        }
    }

    private void selfCollision() {
        // checks for self collision
        Snake snake = snakeList.get(myId);
        if (!snake.isGhostModeActive()) {
            for (int i = 0; i < snake.getParts().size; i++) {
                for (int j = 0; j < snake.getParts().size; j++) {
                    if (Math.abs(i - j) < 2) continue;
                    if (snake.getParts().get(i).overlaps(snake.getParts().get(j))) {
                        snake.getColliders().add(snake.getParts().get(i));
                        snake.getColliders().add(snake.getParts().get(j));

                        snake.setDeadSnake(true);
                        dieMessage(myId, snake);
                        snake.setSpeed(0);
                        mainStage.getActors().removeValue(snake, true);
                        return;
                    }
                }
            }
        }
    }

    private void dieMessage(int i, Snake snake) {
        ScoreMessage score = new ScoreMessage(snake.getPoints());
        socket.writeMsg(i, score);
        Message deathMsg = new Death();
        deathMsg.setId(myId);
        socket.writeMsg(i, deathMsg);
    }

    private void checkSnakeCollision() {
        for (int i = 0; i < snakeList.size(); i++) {
            Snake snake1 = snakeList.get(i);
            if (snake1.isGhostModeActive() || snake1.isDeadSnake()) continue;
            for (int j = 0; j < snakeList.size(); j++) {
                if (i == j) continue;
                Snake snake2 = snakeList.get(j);
                if (snake2.isGhostModeActive() || snake2.isDeadSnake()) continue;
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
                        if (!snake1.isDeadSnake()) dieMessage(i, snake1);
                        mainStage.getActors().removeValue(snake1, true);
                        snake1.setDeadSnake(true);
                        snake1.setSpeed(0);
                        if (snake2part == snake2.getHead()) {
//                            if(!snake2.isDeadSnake()) dieMessage(j);
                            mainStage.getActors().removeValue(snake2, true);
                            snake2.setDeadSnake(true);
                            snake2.setSpeed(0);
                        }
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
            Label label = new Label(snake.getPoints() + "p", labelStyle);
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
            label.setText(mySnake.getPoints() + "p");
            label.setColor(mySnake.getParts().get(1).getColor());
        }
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
    public void refreshPoints(String username, int pointChange) {
        // Kígyó keresése felhasználónév alapján
        Snake snake = null;
        for (Snake s : snakeList) {
            if (s.getName().equals(username)) {
                snake = s;
                break;
            }
        }
        if (snake == null) {
            // Az adott felhasználónévvel nem találtunk kígyót
            return;
        }
        // Kígyó pontjainak frissítése
        snake.setPoints(snake.getPoints() + pointChange);
        // Label frissítése
        int index = snakeList.indexOf(snake);
        Label optionalLabel = pointsLabel.get(index);
        optionalLabel.setText(snake.getName() + ": " + snake.getPoints() + "p");
    }

    public void refreshPoints(int id, int pointChange) {
        // Kígyó keresése felhasználónév alapján
        Snake snake = null;
        for (Snake s : snakeList) {
            if (s.getId() == id) {
                snake = s;
                break;
            }
            /*if (s.getName().equals(username)) {
                snake = s;
                break;
            }*/
        }
        if (snake == null) {
            // Az adott felhasználónévvel nem találtunk kígyót
            return;
        }
        // Label frissítése
        int index = snakeList.indexOf(snake);
        Label optionalLabel = pointsLabel.get(index);
        optionalLabel.setText(snake.getPoints() + "p");
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
        myPoints.setText(snakeList.get(myId).getPoints() + " p");
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
                } else if (msg instanceof Pickup) putNewPickup((Pickup) msg);
                else if (msg instanceof PickupRemove) removePickup((PickupRemove) msg);
                else if (msg instanceof WallMessage) placeWall((WallMessage) msg);
                else if (msg instanceof TimedPickup) {
                    TimedPickup tp = (TimedPickup) msg;
                    for (Snake snake : snakeList) {
                        if (snake.getId() == tp.getId())
                            if (tp.isGhost()) {
                                snake.setGhostModeActive(tp.isEffect());
                                if (snake.isGhostModeActive()) {
                                    System.out.println("SNAKE HEAD COLOR: " + snake.getHead().getColor());
                                    snake.getHead().setColor(Color.FIREBRICK);
                                } else {
                                    snake.getHead().setColor(Color.ORANGE);
                                }
                            } else {
                                if (tp.isEffect()) {
                                    snake.setSpeed(0);
                                    snake.getHead().setColor(Color.CYAN);
                                } else {
                                    snake.setSpeed(Snake.getDefaultSpeed());
                                    snake.getHead().setColor(Color.ORANGE);
                                }
                            }
                    }
                } else if (msg.getId() == gameEndCode) {
                    for (Snake snake : snakeList) {
                        snake.setSpeed(0);
                    }
//                        dieMessage(myId, snakeList.get(myId));   // kill the last snake
//                        for (Snake snake : snakeList) {
//                            snake.setSpeed(0);
//                        }
                } else if (msg.getId() == gameEndCode + 1) {   // after 5 sec mainmenu
                    if (mainStage.getActors().contains(snakeList.get(myId), true))
                        dieMessage(myId, snakeList.get(myId));
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    game.setScreen(new MenuScreen(game));
                    return;
                } else if (msg instanceof Death) {  // kill the snake
                    int snakeId = msg.getId();
                    Snake snake = snakeList.get(snakeId);
                    snake.setSpeed(0);
                    snake.setDeadSnake(true);
                    uiStage.addActor(snake);
                    diedSnakes.add(snake);
                    mainStage.getActors().removeValue(snake, true);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            uiStage.getActors().removeValue(diedSnakes.get(0), true);
                        }
                    }, 3);
                } else {
                    System.out.println("unknown playscreen msg type");
                }
            }
            if (wall != null) checkWallCollision(wall);
            checkSnakeCollision();
            selfCollision();
        } else {
            if (snakeList.get(0).isLeftMove()) snakeList.get(0).turnLeft();
            else if (snakeList.get(0).isRightMove()) snakeList.get(0).turnRight();

        }
//        checkWallCollision(wall);
    }

    private void placeWall(WallMessage msg) {
        wallList = msg.getWallList();
        wall = new Wall(wallList, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mainStage.addActor(wall);
    }

    private void putNewPickup(Pickup msg) {
        Pickup pickup = msg;
        Type type = pickup.getType();
        PickupItems newPickup = null;
        int w=Gdx.graphics.getWidth();
        int h=Gdx.graphics.getHeight();
        switch (type) {
            case FOOD:
//                newPickup = new Food(pickup.getPosition().x, pickup.getPosition().y,
//                        mainStage, pickup.getPickUpId());
                newPickup = new Food(pickup.getPosition().x/1200*w, pickup.getPosition().y/800*h,
                        mainStage, pickup.getPickUpId());
                break;
            case POISON:
//                newPickup = new Poison(pickup.getPosition().x, pickup.getPosition().y,
//                        mainStage, pickup.getPickUpId());
                newPickup = new Poison(pickup.getPosition().x/1200*w, pickup.getPosition().y/800*h,
                        mainStage, pickup.getPickUpId());
                break;
            case WEB:
//                newPickup = new SpiderWeb(pickup.getPosition().x, pickup.getPosition().y,
//                        mainStage, pickup.getPickUpId());
            newPickup = new SpiderWeb(pickup.getPosition().x/1200*w, pickup.getPosition().y/800*h,
                        mainStage, pickup.getPickUpId());
                break;
            case DRINK:
//                newPickup = new EnergyDrink(pickup.getPosition().x, pickup.getPosition().y,
//                        mainStage, pickup.getPickUpId());
                newPickup = new EnergyDrink(pickup.getPosition().x/1200*w, pickup.getPosition().y/800*h,
                        mainStage, pickup.getPickUpId());
                break;
            case ICE:
//                newPickup = new IceBlock(pickup.getPosition().x, pickup.getPosition().y,
//                        mainStage, pickup.getPickUpId());
                newPickup = new IceBlock(pickup.getPosition().x/1200*w, pickup.getPosition().y/800*h,
                        mainStage, pickup.getPickUpId());
                break;
            case GHOST:
//                newPickup = new Ghost(pickup.getPosition().x, pickup.getPosition().y,
//                        mainStage, pickup.getPickUpId());
                newPickup = new Ghost(pickup.getPosition().x/1200*w, pickup.getPosition().y/800*h,
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
        int whichSnake = pickupRemove.getSnakeId();
        if (whichSnake == myId) return;
        PickupItems pickup = pickups.getById(pickupId);
//        for (Snake snake:snakeList) {
//            if(snake.isUnderPicking(pickup.getId())) return;
//        }
//        snakeList.get(cId).addPickupUnderPicking(pickup.getId());
        pickup.collectItem(snakeList.get(whichSnake));
        pickup.applyEffect(snakeList.get(whichSnake));
        pickups.removeValue(pickup, true);
    }

    private void checkPickupCollision() {
        for (PickupItems pickup : pickups) {
//            for (Snake snake : snakeList) {
            Snake snake = snakeList.get(myId);
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
                    pickup.collectItem(snake);
                    pickup.applyEffect(snake);
                    if (pickup instanceof Food || pickup instanceof Poison) {
                        refreshPoints(snake.getId(), snake.getPoints());
                        labelSort(sortPoints());
                    }
                    pickups.removeValue(pickup, true);
                    socket.writeMsg(myId,
                            new PickupRemove(pickup.getId(), myId));
                }
//                    System.out.println("Pickup:" + pickup);
//                }
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
