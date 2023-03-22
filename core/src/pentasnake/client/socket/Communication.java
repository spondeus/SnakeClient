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

    private SnakeGame game;

    public Communication(SnakeGame game) {
        this.game = game;

        try{
            URI uri = new URI("ws://192.168.18.8:8080");
            websocketClient = new ClientSocket(uri, game);
        } catch (URISyntaxException e){
            throw new RuntimeException(e);
        }
        this.create();
    }

    @Getter
    private static ClientSocket websocketClient;

    @Override
    public void create(){
        super.create();

        websocketClient.connect();

    }
}
