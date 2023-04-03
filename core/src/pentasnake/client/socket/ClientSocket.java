package pentasnake.client.socket;


import com.badlogic.gdx.Gdx;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.client.messages.*;
import pentasnake.client.screen.MenuScreen;

import java.net.URI;
import java.util.*;

public class ClientSocket extends WebSocketClient {

    private final SnakeGame game;

    private final List<String> snakeConstruct = new ArrayList<>();

    private Snake snake;

    String msg;
    private String constMsg;
    private List<Map<Integer, String>> currentInputs;
    private List<String> pickups;
    private int id;

    private boolean cons;
    private Gson gson = new Gson();

    public List<String> getPickups() {
        return pickups;
    }

    private Queue<Message> msgQueue = new ArrayDeque<>();

    public ClientSocket(URI uri, SnakeGame game) {
        super(uri);

        currentInputs = new ArrayList<>();

        this.game = game;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("connected");
    }

    @Override
    public void onMessage(String s) {
        readMsg(s);
    }

    public void writeMsg(int id, Message msg) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("id", new JsonPrimitive(id));
        String type;
        if (msg instanceof SnakeMove) type = "snakeMove";
        else if (msg instanceof SnakeConstruct) type = "snakeConstruct";
        else if (msg instanceof SnakeColorChange) type = "snakeColorChange";
        else if (msg instanceof PickupRemove) type = "pickupRemove";
        else if (msg instanceof ScoreMessage) type = "score";
        else if (msg instanceof Death) type = "death";
        else if (msg instanceof Points) type = "points";
        else type = "id";
        jsonObject.add("type", new JsonPrimitive(type));
        String innerJson = gson.toJson(msg);
        jsonObject.add("data", new JsonPrimitive(innerJson));
        String outerJson = gson.toJson(jsonObject);
        send(outerJson);
        if (msg instanceof SnakeMove) ;
        else System.out.println("sent:" + jsonObject);
    }

    public void readMsg(String s) {
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        JsonElement cId = jsonObject.get("id");
        int clientId = cId.getAsInt();
        JsonElement msgType = jsonObject.get("type");
        String type = msgType.getAsString();
        if (!type.equals("snakeMove")) System.out.println(" got:" + s);
        if (type.startsWith("snake")) handleSnakeMsg(jsonObject);
        else if (type.startsWith("pickup")) handlePickupMsg(jsonObject);
        else if (type.startsWith("wall")) handleWallMsg(jsonObject);
        else if (type.equals("death")) handleDieMsg(clientId);
        else if (type.equals("top10")) handleTopMsg(jsonObject);
        else handleIdMsg(clientId, type, jsonObject);
    }

    private void handleTopMsg(JsonObject jsonObject) {
        String dataStr = jsonObject.get("data").getAsString();
        Top10 top10Message = gson.fromJson(dataStr, Top10.class);
        msgQueue.add(top10Message);
    }

    private void handleDieMsg(int clientId) {
        Death msg = new Death();
        msg.setId(clientId);
        msgQueue.add(msg);
    }


    private void handleIdMsg(int clientId, String type, JsonObject jsonObject) {
        switch (type) {
            case "id":
                if (clientId == -1) {  // constructs sent
                    Message msg = new Message();
                    msg.setId(clientId);
                    msgQueue.add(msg);
                    return;
                } else if (clientId == 999) {   //end game
                    Message msg = new Message();
                    msg.setId(999);
                    msgQueue.add(msg);
                    return;
                } else if (clientId == 1000) {   //end game
                    Message msg = new Message();
                    msg.setId(1000);
                    msgQueue.add(msg);
                    return;
                } else {   // got id
                    id = clientId;
                }
                break;
            default:
                System.err.println("Unknown message type!");
        }
    }


    private void handleWallMsg(JsonObject jsonObject) {
        String dataStr = jsonObject.get("data").getAsString();
        WallMessage wallMessage = gson.fromJson(dataStr, WallMessage.class);
        msgQueue.add(wallMessage);
    }

    private void handlePickupMsg(JsonObject jsonObject) {
        JsonElement type = jsonObject.get("type");
        JsonObject innerJson;
        String dataStr;
        switch (type.getAsString()) {
            case "pickupConst":
                dataStr = jsonObject.get("data").getAsString();
                Pickup pickup = gson.fromJson(dataStr, Pickup.class);
                msgQueue.add(pickup);
                break;
            case "pickupRemove":
                dataStr = jsonObject.get("data").getAsString();
                PickupRemove pickupRemove = gson.fromJson(dataStr, PickupRemove.class);
                pickupRemove.setId(id);
                msgQueue.add(pickupRemove);
                break;
            case "pickupTimed":
                dataStr = jsonObject.get("data").getAsString();
                TimedPickup timedPickup = gson.fromJson(dataStr, TimedPickup.class);
                timedPickup.setId(id);
                msgQueue.add(timedPickup);
            default:
                System.err.println("Unknown pickup message!");
                break;
        }

    }

    private void handleSnakeMsg(JsonObject jsonObject) {
        JsonPrimitive id = jsonObject.getAsJsonPrimitive("id");
        int goodId = id.getAsInt();
        JsonElement type = jsonObject.get("type");
        JsonObject innerJson;
        String dataStr;
        switch (type.getAsString()) {
            case "snakeConstruct":
                dataStr = jsonObject.get("data").getAsString();
                SnakeConstruct snakeConstruct = gson.fromJson(dataStr, SnakeConstruct.class);
                snakeConstruct.setId(goodId);
                msgQueue.add(snakeConstruct);
                break;
            case "snakeCollision":
                break;
            case "snakeMove":
                dataStr = jsonObject.get("data").getAsString();
                SnakeMove snakeMove = gson.fromJson(dataStr, SnakeMove.class);
                snakeMove.setId(goodId);
                msgQueue.add(snakeMove);
                break;
            case "snakePointChange":
                break;
            case "snakeSpeedChange":
                break;
            case "snakeColorChange":
                dataStr = jsonObject.get("data").getAsString();
                SnakeColorChange snakeColorChange = gson.fromJson(dataStr, SnakeColorChange.class);
                snakeColorChange.setId(goodId);
                System.out.println(snakeColorChange);
                break;
            default:
                System.err.println("Unknown snake message!");
                break;
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("disconnected");
        Gdx.app.error("Client", "no server");
//        game.setScreen(new MenuScreen(game));
    }

    @Override
    public void onError(Exception e) {

    }

    public List<String> getSnakeConstruct() {
        return snakeConstruct;
    }

    public Snake getSnake() {
        return snake;
    }

    public String getConstMsg() {
        return constMsg;
    }

    public int getId() {
        return id;
    }

    public boolean isCons() {
        return cons;
    }

    public void setCons(boolean cons) {
        this.cons = cons;
    }

    public List<Map<Integer, String>> getCurrentInputs() {
        return currentInputs;
    }

    public Queue<Message> getMsgQueue() {
        return msgQueue;
    }

    public Gson getGson() {
        return gson;
    }
}

