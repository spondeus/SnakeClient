package pentasnake.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import lombok.val;
import pentasnake.client.InputHandler;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.client.socket.Communication;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LobbyScreen implements Screen{

    private final SnakeGame game;

    SpriteBatch batch = new SpriteBatch();
    private List<Snake> snakes= new ArrayList<>();

    Label waiting;

    public LobbyScreen(SnakeGame game){
        this.game = game;
    }

    @Override
    public void show(){
         waiting = new Label("Waiting", new Label.LabelStyle(new BitmapFont(), Color.GOLD));
         waiting.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
         Communication com = new Communication(game);
         val snake = com.getSnake();
         snakes.add(snake);

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
            waiting.draw(batch, 1);
        batch.end();

        if(snakes != null && snakes.size() == 1){
            game.setScreen(new PlayScreen(game, snakes));
        }
    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){

    }
}
