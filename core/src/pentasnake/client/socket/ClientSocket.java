package pentasnake.client.socket;


import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.utils.Timer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.pointsystem.Food;
import pentasnake.pointsystem.PickupItems;

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

    public List<String> getPickups(){
        return pickups;
    }

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

        if(s.startsWith("pickup")){
            Gdx.app.log("Pickup",msg);
            msg = s.substring("pickup#".length());

            Timer.schedule(new Timer.Task()
            {
                @Override
                public void run()
                {
                    pickups.add(msg);
                    System.out.println(pickups);
                }
            }, 100).run();
        }

        if (s.startsWith("id")) {
            String[] msgSPlt = s.split("#");
            id = Integer.parseInt(msgSPlt[1]);
        }

        if (s.startsWith("input")) {
            Gdx.app.log("Input", s);
            final String[] split = s.split("#");
            String input = split[1];
            int id = Integer.parseInt(split[2]);
            Map<Integer, String> newInput = new HashMap<>();
            newInput.put(id, input);

            currentInputs.add(newInput);
        }

        if (!s.contains("#"))
            Gdx.app.log("Server", s);
        if (s.startsWith("cons")) {
            constMsg = s;
            cons = true;
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

