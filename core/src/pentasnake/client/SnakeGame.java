package pentasnake.client;

import com.badlogic.gdx.Game;
import pentasnake.client.screen.MenuScreen;

public class SnakeGame extends Game {
    @Override
    public void create () {
        setScreen(new MenuScreen());
    }

}
