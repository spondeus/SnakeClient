package pentasnake.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import pentasnake.client.entities.Snake;
import pentasnake.client.entities.SnakePart;
import pentasnake.client.messages.SnakeMove;
import pentasnake.client.socket.ClientSocket;
import pentasnake.client.socket.Communication;

public class InputHandler extends InputAdapter {

    private final Communication localClient;
    Snake snake;

    public InputHandler(Snake snake, Communication localClient) {
        this.snake = snake;
        this.localClient = localClient;
    }

    @Override
    public boolean keyDown(int keycode) {
        SnakePart head = snake.getHead();
        SnakePart neck = snake.getParts().get(1);
        if (keycode == Input.Keys.A) snake.setLeftMove(true);
        else if (keycode == Input.Keys.D) snake.setRightMove(true);
        if(localClient!=null) {
            ClientSocket socket=localClient.getWebsocketClient();
            if (keycode == Input.Keys.A) {
                if (socket.isClosed()) Gdx.app.error("Client", "Connection closed");
                socket.writeMsg(socket.getId(),new SnakeMove(true));
            } else if (keycode == Input.Keys.D) {
                if (socket.isClosed()) Gdx.app.error("Client", "Connection closed");
                socket.writeMsg(socket.getId(),new SnakeMove(false));
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A) snake.setLeftMove(false);
        else if (keycode == Input.Keys.D) snake.setRightMove(false);
        return false;
    }

}
