package pentasnake.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import lombok.Getter;
import pentasnake.client.entities.Snake;
import pentasnake.client.screen.MenuScreen;

public class SnakeGame extends Game {

    @Getter
    public MenuScreen menu;

    @Override
    public void create() {
        menu = new MenuScreen(this);
        setScreen(menu);
    }

    @Override
    public void dispose() {
    }
}
