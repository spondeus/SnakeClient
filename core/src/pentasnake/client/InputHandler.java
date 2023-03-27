package pentasnake.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import pentasnake.client.entities.Snake;
import pentasnake.client.entities.SnakePart;
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
        if(localClient==null){
            if (keycode == Input.Keys.A) snake.setLeftMove(true);
            else if (keycode == Input.Keys.D) snake.setRightMove(true);
        }else{
            if (keycode == Input.Keys.A) {
                if (localClient.getWebsocketClient().isClosed()) Gdx.app.error("Client", "Connection closed");
                localClient.send("inputA," + localClient.getWebsocketClient().getId());

                //snake.turnLeft();
            } else if (keycode == Input.Keys.D) {
                if (localClient.getWebsocketClient().isClosed()) Gdx.app.error("Client", "Connection closed");
                localClient.send("inputD," + localClient.getWebsocketClient().getId());
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(localClient==null){
            if (keycode == Input.Keys.A) snake.setLeftMove(false);
            else if (keycode == Input.Keys.D) snake.setRightMove(false);
        }
        return false;
    }

}
