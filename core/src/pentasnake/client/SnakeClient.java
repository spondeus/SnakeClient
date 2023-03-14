package pentasnake.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import pentasnake.client.entities.Player_SnakeHead;

public class SnakeClient extends ApplicationAdapter {
	SpriteBatch batch;
	Player_SnakeHead playerSnakeHead;

	@Override
	public void create () {
		batch = new SpriteBatch();
		playerSnakeHead = new Player_SnakeHead();
		Gdx.input.setInputProcessor(playerSnakeHead.getHandler());
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		playerSnakeHead.draw(batch);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
