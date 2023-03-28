package pentasnake.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import pentasnake.client.screen.ColorSetting;
import pentasnake.client.screen.MenuScreen;
import pentasnake.client.screen.PlayScreen;

public class SnakeGame extends Game {
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

  private Color color;
    @Override
    public void create () {
        setScreen(new PlayScreen());
    }
}
