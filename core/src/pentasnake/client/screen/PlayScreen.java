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
import pentasnake.client.socket.Communication;
import pentasnake.pointsystem.*;

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

    //private PickupSpawner pickupSpawner;

    private final SnakeGame game;

    private final Communication localClient;
    private final int myId;

    private boolean single;
    MySnapshotArray pickups = new MySnapshotArray();
    List<String> pickupCons;

    ClientSocket socket;

    int pickupUnderPicking=-1;

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
            else
                snakeList.add(new Snake(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 20, Color.GREEN, 0));

        initialize();
    }

    public void initialize() {
        Gdx.app.log("Client/ snakeList", snakeList.toString());
        System.out.println("myid?" + myId);
        Snake x = snakeList.get(myId);
        Gdx.input.setInputProcessor(new InputHandler(x, localClient));
        mainStage.addActor(x);
        labelInitialize();

    }

    public void labelInitialize() {
        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        table = new Table();
        labelStyle.fontColor = Color.GOLD;
        myPoints = new Label(snakeList.get(myId).getPoints() + " p", labelStyle);
        pointsLabel = new LinkedList<>();
        pointsLabel.add(new Label(snakeList.get(myId).getPoints() + " p", labelStyle));
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
                    if(pickup.getId()==pickupUnderPicking) continue;
                    pickupUnderPicking=pickup.getId();
                    socket.writeMsg(myId,
                            new PickupRemove(pickup.getId()));
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

    private void newPickup() {
        for (String s : pickupCons) {
            String[] split = s.split("#");

            Gdx.app.log("PickupType", split[0]);

            PickupItems newPickup = new Food(
                    Integer.parseInt(split[3]),
                    Integer.parseInt(split[2]),
                    mainStage,
                    Integer.parseInt(split[1])
            );

            pickups.add(newPickup);


            switch (split[0]) {

            }

            localClient.getWebsocketClient().getPickups().remove(s);
        }
    }

}
