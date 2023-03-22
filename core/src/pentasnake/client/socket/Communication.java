package pentasnake.client.socket;

import com.badlogic.gdx.ApplicationAdapter;
import lombok.Getter;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.client.screen.PlayScreen;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

public class Communication extends ApplicationAdapter{

    @Getter
    private static ClientSocket websocketClient;

    private SnakeGame game;

    @Getter
    private Snake snake;

    public Communication(SnakeGame game) {
        this.game = game;

        try{
            URI uri = new URI("ws://192.168.18.8:8080");
            websocketClient = new ClientSocket(uri, game);
            snake = websocketClient.getSnake();
        } catch (URISyntaxException e){
            throw new RuntimeException(e);
        }
        this.create();
    }


    @Override
    public void create(){
        super.create();

        websocketClient.connect();

    }
}
