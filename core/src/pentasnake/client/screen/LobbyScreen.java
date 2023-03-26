package pentasnake.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;

import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.client.socket.Communication;

import java.util.*;
import java.util.List;

public class LobbyScreen implements Screen {

    private final SnakeGame game;

    SpriteBatch batch = new SpriteBatch();

    private List<Snake> snakes = new ArrayList<>();

    Label waiting;

    private Communication com;
    private int myId;

    private boolean single;

    public LobbyScreen(SnakeGame game,boolean single) {
        this.game=game;
        this.single=single;

    }

    @Override
    public void show() {
        if(single) {
            if(single) game.setScreen(new PlayScreen(game, snakes, com,single));
            return;
        }
        waiting = new Label("Waiting", new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        waiting.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);

        com = new Communication(game);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (com.getWebsocketClient().isOpen()) {
                    com.send("cons?1,?2,20,ORANGE," + com.getWebsocketClient().getId());
                }
            }
        }, 1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        waiting.draw(batch, 1);
        batch.end();

        if (com.getWebsocketClient().isCons()) {
            Gdx.app.log("Client-state", String.valueOf(com.getWebsocketClient().getReadyState()));

            String s = com.getWebsocketClient().getConstMsg();
            String[] msg = s.split("#");
            for (String value : msg) {
                if (!value.equals("cons")) {
                    String[] parts = value.split(",");
                    Snake newSnake = new Snake(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            20,
                            Color.GREEN,
                            Integer.parseInt(parts[4])
                    );
                    snakes.add(newSnake);
                }
            }
            com.getWebsocketClient().setCons(false);
            game.setScreen(new PlayScreen(game, snakes, com,false));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}