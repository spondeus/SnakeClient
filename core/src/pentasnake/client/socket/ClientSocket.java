package pentasnake.client.socket;


import com.badlogic.gdx.Gdx;
import lombok.Getter;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import pentasnake.client.SnakeGame;
import pentasnake.client.screen.MenuScreen;

import java.net.URI;

public class ClientSocket extends WebSocketClient{

    private final SnakeGame game;

    public ClientSocket(URI uri, SnakeGame game){
        super(uri);

        this.game = game;
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
    }

    @Override
    public void onError(Exception e){

    }

}

