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
import pentasnake.client.messages.SnakeConstruct;
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
        JSONObject jsonObject = new JSONObject(s);
        int clientId = jsonObject.getInt("id");
        String type = jsonObject.getString("type");
        if (type.startsWith("snake")) handleSnakeMsg(jsonObject);
        else if (type.startsWith("pickup")) handlePickupMsg(jsonObject)
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

    private void handleWallMsg(JSONObject jsonObject) {
    }

    private void handlePickupMsg(JSONObject jsonObject) {

    }

    private void handleSnakeMsg(JsonObject jsonObject) {
        Gson gson = new Gson();
        JsonElement element=jsonObject.get("type");
        switch (element.getAsString()) {
            case "snakeConstruct":
                JsonObject innerJSON=jsonObject.getAsJsonObject("data");
                SnakeConstruct snakeConstruct= gson.fromJson(innerJSON.toString(), SnakeConstruct.class);
                break;
            case "snakeCollision":
                break;
            case "snakeMove":
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
}

