package pentasnake.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import lombok.val;
import org.java_websocket.client.WebSocketClient;
import pentasnake.client.InputHandler;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.client.socket.Communication;

import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;
import java.awt.*;
import java.util.*;
import java.util.List;

public class LobbyScreen implements Screen{

    private final SnakeGame game;

    SpriteBatch batch = new SpriteBatch();

    private List<Snake> snakes= new ArrayList<>();

    Label waiting;

    private Communication com;

    public LobbyScreen(SnakeGame game){
        this.game = game;
    }

    @Override
    public void show(){
         waiting = new Label("Waiting", new Label.LabelStyle(new BitmapFont(), Color.GOLD));
         waiting.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        com = new Communication(game);
        //val snake = com.getSnake();
        //snakes.add(snake);
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        if(com.getWebsocketClient().isOpen()){
            int x = new Random().nextInt(100,300);
            int y = new Random().nextInt(100,300);
            com.send(x+","+ y +",20,ORANGE,"+com.getWebsocketClient().getId());
        }
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
            waiting.draw(batch, 1);
        batch.end();

        if(com.getWebsocketClient().isCons()){
            System.out.println(com.getWebsocketClient().getReadyState());

            String s = com.getWebsocketClient().getConstMsg();
            String[] msg = s.split("#");
            for (String value : msg){
                if (!value.equals("cons")){
                    String[] parts = value.split(",");
                    System.out.println(Arrays.toString(parts));
                    snakes.add(
                            new Snake(
                                    Integer.parseInt(parts[0]),
                                    Integer.parseInt(parts[1]),
                                    20,
                                    Color.GREEN,
                                    com.getWebsocketClient().getId()
                            ));
                }
            }
            System.out.println(snakes.toString());

            com.getWebsocketClient().setCons(false);
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
