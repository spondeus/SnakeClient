package pentasnake.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import pentasnake.client.entities.Player_SnakeHead;
import pentasnake.client.entities.Snake;

public class SnakeClient extends ApplicationAdapter {
	private SpriteBatch batch;

	private Snake snake;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		snake=new Snake(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2, 20, Color.GREEN);
		Gdx.input.setInputProcessor(new InputHandler(snake));
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		snake.draw();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
