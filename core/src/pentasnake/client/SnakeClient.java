package pentasnake.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import pentasnake.temporaryclasses.PlayScreenTEMP;

public class SnakeClient extends Game {
	SpriteBatch batch;
	Texture img;

	public void create(){
		setScreen(new PlayScreenTEMP());
	}

	/*public void create () {
		batch = new SpriteBatch();
		img = new Texture("blackscreen.jpg");
	}*/

	/*@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}*/
	
	/*@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}*/
}
