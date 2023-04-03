package pentasnake.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;

import com.google.gson.JsonObject;
import org.java_websocket.client.WebSocketClient;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.Snake;
import pentasnake.client.messages.Message;
import pentasnake.client.messages.SnakeColorChange;
import pentasnake.client.messages.SnakeConstruct;
import pentasnake.client.socket.ClientSocket;
import pentasnake.client.entities.Wall;
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

    Queue<Message> queue;

    int ind=0;

    public LobbyScreen(SnakeGame game, boolean single) {
        this.game = game;
        this.single = single;

    }

    @Override
    public void show() {
        if (single) {
            game.setScreen(new PlayScreen(game, snakes, com, single));
            return;
        }
        waiting = new Label("Waiting", new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        waiting.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        com = new Communication(game);
        final ClientSocket client = com.getWebsocketClient();
        queue = client.getMsgQueue();
        sendMyColor(client);
//        Timer.schedule(new Timer.Task() {
//            @Override
//            public void run() {
//                if (com.getWebsocketClient().isOpen()) {
//                    com.send("cons?1,?2,20,ORANGE," + com.getWebsocketClient().getId());
//                }
//            }
//        }, 1);
    }

    private void sendMyColor(final ClientSocket client) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (client.isOpen()) {
                    SnakeColorChange snakeColorChange = new SnakeColorChange(game.getColor(), -1, -1);
                    client.writeMsg(client.getId(), snakeColorChange);
//                    com.send(msg);
                }
            }
        }, 2);
    }

    @Override
    public void render(float delta) {
        processMsg();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        waiting.draw(batch, 1);
        batch.end();

//        if (com.getWebsocketClient().isCons()) {
//            Gdx.app.log("Client-state", String.valueOf(com.getWebsocketClient().getReadyState()));
//
//            String s = com.getWebsocketClient().getConstMsg();
//            String[] msg = s.split("#");
//            for (String value : msg) {
//                if (!value.equals("cons")) {
//                    String[] parts = value.split(",");
//                    Snake newSnake = new Snake(
//                            Integer.parseInt(parts[0]),
//                            Integer.parseInt(parts[1]),
//                            20,
//                            Color.GREEN,
//                            Integer.parseInt(parts[4])
//                    );
//                    snakes.add(newSnake);
//                }
//            }
//            com.getWebsocketClient().setCons(false);
//            game.setScreen(new PlayScreen(game, snakes, com,false));
//        }
    }

    private void processMsg() {
        while (!queue.isEmpty()) {
//            Gdx.app.log("Client-state", String.valueOf(com.getWebsocketClient().getReadyState()));
            Message message = queue.poll();
            if (message instanceof SnakeConstruct) {
                SnakeConstruct snakeConstruct = (SnakeConstruct) message;
                Snake newSnake = new Snake(snakeConstruct.getX(), snakeConstruct.getY(), snakeConstruct.getRadius(),
                        snakeConstruct.getColor(), ind++);
                snakes.add(newSnake);
            }
            if (message.getId() == -1){
                game.setScreen(new PlayScreen(game, snakes, com, false));
                break;
            }
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
