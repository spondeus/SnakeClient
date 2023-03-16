package pentasnake.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import pentasnake.client.screen.PlayScreen;

public class SnakeClient extends Game {
	SpriteBatch batch;
	Texture img;

	
	@Override
	public void create () {
		PlayScreen screen1 = new PlayScreen();
		this.setScreen(screen1);
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
	}
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
