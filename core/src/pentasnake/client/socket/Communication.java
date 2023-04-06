package pentasnake.client.socket;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.SnapshotArray;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.client.screen.PlayScreen;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

public class Communication extends ApplicationAdapter {

    public void send(String message) {
        websocketClient.send(message);
    }

    private ClientSocket websocketClient;

    private SnakeGame game;

    private Snake snake;

    public Communication(SnakeGame game) {
        this.game = game;

        try {
//            URI uri = new URI("ws://192.168.18.8:8080"); // Bálint Progmatic IP
           URI uri = new URI("ws://192.168.18.4:8080"); // Tamás Progmatic IP
//            URI uri = new URI("ws://192.168.1.130:8080"); // Tamás home IP
//            URI uri = new URI("ws://pentasnake.ddns.net:8080"); // Tamás dinamikus DNS IP
//            URI uri = new URI("ws://192.168.1.84:8080");
     //       URI uri = new URI("ws://192.168.18.18:8080");  //Ricsi IP
    //        URI uri = new URI("ws://192.168.0.192:8080"); // Ricsi home IP
            websocketClient = new ClientSocket(uri, game);
            snake = websocketClient.getSnake();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        this.create();
    }


    @Override
    public void create() {
        super.create();
        websocketClient.connect();
    }

    public ClientSocket getWebsocketClient(){
        return websocketClient;
    }

    public Snake getSnake(){
        return snake;
    }
}
