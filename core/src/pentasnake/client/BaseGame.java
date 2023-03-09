package pentasnake.client;

import com.badlogic.gdx.Game;
import pentasnake.client.Screen.BaseScreen;

public abstract class BaseGame extends Game
{
    private static BaseGame game;
    public BaseGame()
    {
        game = this;
    }
    public static void setActiveScreen(BaseScreen s)
    {
        game.setScreen(s);
    }
}