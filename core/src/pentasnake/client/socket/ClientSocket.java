package pentasnake.client.socket;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import lombok.Getter;
import lombok.val;
import lombok.var;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.client.screen.MenuScreen;

import java.net.URI;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ClientSocket extends WebSocketClient{

    private final SnakeGame game;
    public static Set<Integer> ids = new HashSet<>();

    @Getter
    private Snake snake;

    @Getter
    private int id;

    public ClientSocket(URI uri, SnakeGame game){
        super(uri);

        this.game = game;
        {
            while(true){
                Integer random = new Random().nextInt(1,10);
                if(!ids.contains(random)){
                    ids.add(random);
                    id= random;
                    break;
                }
            }
            snake = new Snake(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 20, Color.GREEN, id);
        }
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake){
        send("xd");
        System.out.println("connected");

    }

    @Override
    public void onMessage(String s){
        Gdx.app.log("server",s);
    }

    @Override
    public void onClose(int i, String s, boolean b){
        System.out.println("disconnected");
        Gdx.app.error("Client","no server");
        game.setScreen(game.menu);
        ids.remove(id);
    }

    @Override
    public void onError(Exception e){

    }

}

