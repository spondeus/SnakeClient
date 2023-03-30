package pentasnake.client.socket;


import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.graphics.Color;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.client.messages.*;
import pentasnake.pointsystem.Food;
import pentasnake.pointsystem.PickupItems;
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

    public List<String> getPickups(){
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
//        writeMsg(1, new SnakeMove(true));

//        if (s.startsWith("id")) {
//            String[] msgSPlt = s.split("#");
//            id = Integer.parseInt(msgSPlt[1]);
//        }
//
//        if (s.startsWith("input")) {
//            Gdx.app.log("Input", s);
//            final String[] split = s.split("#");
//            String input = split[1];
//            int id = Integer.parseInt(split[2]);
//            Map<Integer, String> newInput = new HashMap<>();
//            newInput.put(id, input);
//
//            currentInputs.add(newInput);
//        }
//
//        if (!s.contains("#"))
//            Gdx.app.log("Server", s);
//        if (s.startsWith("cons")) {
//            constMsg = s;
//            cons = true;
//        }

//        if (s.startsWith("pickup")) {
//            Gdx.app.log("Pickup", msg);
//            msg = s.substring("pickup#".length());
//
//            Timer.schedule(new Timer.Task() {
//                @Override
//                public void run() {
//                    pickups.add(msg);
//                    System.out.println(pickups);
//                }
//            }, 100).run();
//        }
//
//        if (s.startsWith("id")) {
//            String[] msgSPlt = s.split("#");
//            id = Integer.parseInt(msgSPlt[1]);
//        }
    }

    public void writeMsg(int id, Message msg) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("id", new JsonPrimitive(id));
        String type;
        if (msg instanceof SnakeMove) type = "snakeMove";
        else if (msg instanceof SnakeConstruct) type = "snakeConstruct";
        else if (msg instanceof SnakeColorChange) type = "snakeColorChange";
        else type = "id";
        jsonObject.add("type", new JsonPrimitive(type));
        String innerJson = gson.toJson(msg);
        jsonObject.add("data", new JsonPrimitive(innerJson));
        String outerJson = gson.toJson(jsonObject);
        send(outerJson);
        System.out.println("sent:" + jsonObject);
    }

    public void readMsg(String s) {
        System.out.println(" got:" + s);
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        JsonElement cId = jsonObject.get("id");
        int clientId = cId.getAsInt();
        JsonElement msgType = jsonObject.get("type");
        String type = msgType.getAsString();
        if (type.startsWith("snake")) handleSnakeMsg(jsonObject);
        else if (type.startsWith("pickup")) handlePickupMsg(jsonObject);
        else if (type.startsWith("wall")) handleWallMsg(jsonObject);
        else {
            switch (type) {
                case "id":
                    id = clientId;
                    break;
                case "die":
                    break;
                default:
                    System.err.println("Unknown message type!");

            }
        }
    }

    private void handleWallMsg(JsonObject jsonObject) {
    }

    private void handlePickupMsg(JsonObject jsonObject) {
        JsonElement type = jsonObject.get("type");
        JsonObject innerJson;
        String dataStr;
        switch (type.getAsString()) {
            case "pickupConst":
                dataStr = jsonObject.get("data").getAsString();
                Pickup pickup = gson.fromJson(dataStr, Pickup.class);
                pickup.setId(id);
                msgQueue.add(pickup);
                break;
            case "pickupRemove":

                break;
            case "snakeMove":
                dataStr = jsonObject.get("data").getAsString();
                SnakeMove snakeMove = gson.fromJson(dataStr, SnakeMove.class);
                snakeMove.setId(id);
                msgQueue.add(snakeMove);
                break;
            case "snakePointChange":
                break;
            case "snakeSpeedChange":
                break;
            case "snakeColorChange":
                dataStr = jsonObject.get("data").getAsString();
                SnakeColorChange snakeColorChange = gson.fromJson(dataStr, SnakeColorChange.class);
                System.out.println(snakeColorChange);
                break;
            default:
                System.err.println("Unknown snake message!");
                break;
        }

    }

    private void handleSnakeMsg(JsonObject jsonObject) {
        JsonElement type = jsonObject.get("type");
        JsonObject innerJson;
        String dataStr;
        switch (type.getAsString()) {
            case "snakeConstruct":
                dataStr = jsonObject.get("data").getAsString();
                SnakeConstruct snakeConstruct = gson.fromJson(dataStr, SnakeConstruct.class);
                snakeConstruct.setId(id);
                msgQueue.add(snakeConstruct);
                break;
            case "snakeCollision":
                break;
            case "snakeMove":
                dataStr = jsonObject.get("data").getAsString();
                SnakeMove snakeMove = gson.fromJson(dataStr, SnakeMove.class);
                snakeMove.setId(id);
                msgQueue.add(snakeMove);
                break;
            case "snakePointChange":
                break;
            case "snakeSpeedChange":
                break;
            case "snakeColorChange":
                dataStr = jsonObject.get("data").getAsString();
                SnakeColorChange snakeColorChange = gson.fromJson(dataStr, SnakeColorChange.class);
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
        game.setScreen(game.menu);
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

