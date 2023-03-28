package pentasnake.client.socket;

import com.badlogic.gdx.ApplicationAdapter;
import lombok.Getter;
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
            URI uri = new URI("ws://192.168.1.130:8080"); // Tamás home IP
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

    public ClientSocket getWebsocketClient() {
        return websocketClient;
    }

}
