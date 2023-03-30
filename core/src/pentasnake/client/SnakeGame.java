package pentasnake.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import pentasnake.client.entities.Snake;
import com.badlogic.gdx.graphics.Color;
import pentasnake.client.screen.ColorSetting;
import pentasnake.client.screen.MenuScreen;
import pentasnake.client.screen.PlayScreen;

public class SnakeGame extends Game {

    public MenuScreen getMenu(){
        return menu;
    }

    public MenuScreen menu;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

  private Color color=Color.GREEN;
    @Override
    public void create() {
        menu = new MenuScreen(this);
        setScreen(menu);
    }

    @Override
    public void dispose() {
    }
}
