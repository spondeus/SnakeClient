package pentasnake.client;

import pentasnake.client.Screen.MenuScreen;

public class SnakeGame extends BaseGame{

    public void create()
    {
        setActiveScreen( new MenuScreen() );
    }
}
