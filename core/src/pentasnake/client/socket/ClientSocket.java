package pentasnake.client.socket;


import com.badlogic.gdx.Gdx;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.client.messages.Message;
import pentasnake.client.messages.SnakeConstruct;
import pentasnake.client.messages.SnakeMove;
import pentasnake.client.screen.MenuScreen;

import java.net.URI;
import java.util.*;

public class ClientSocket extends WebSocketClient {

    private final SnakeGame game;

    private final List<String> snakeConstruct = new ArrayList<>();

    private Snake snake;

    private String constMsg;
    private List<Map<Integer, String>> currentInputs;
    private int id;

    private boolean cons;
    private Gson gson = new Gson();

    private Queue<Message> msgQueue=new PriorityQueue();

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
                    System.err.println("Uknown message type!");

            }
        }

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

    }

    private void handleWallMsg(JsonObject jsonObject) {
    }

    private void handlePickupMsg(JsonObject jsonObject) {

    }

    private void handleSnakeMsg(JsonObject jsonObject) {
        Gson gson = new Gson();
        JsonElement element = jsonObject.get("type");
        JsonObject innerJson;
        switch (element.getAsString()) {
            case "snakeConstruct":
                innerJson = jsonObject.getAsJsonObject("data");
                SnakeConstruct snakeConstruct = gson.fromJson(innerJson.toString(), SnakeConstruct.class);
                msgQueue.add(snakeConstruct);
                break;
            case "snakeCollision":
                break;
            case "snakeMove":
                innerJson = jsonObject.getAsJsonObject("data");
                SnakeMove snakeMove= gson.fromJson(innerJson.toString(), SnakeMove.class);
                msgQueue.add(snakeMove);
                break;
            case "snakePointChange":
                break;
            case "snakeSpeedChange":
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
}

