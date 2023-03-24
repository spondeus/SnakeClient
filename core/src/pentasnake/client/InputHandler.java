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
        if (isTurning(head,neck) && tooCloseX(head,neck)) return false;
        if (isTurning(head,neck) && tooCloseY(head,neck)) return false;
        if(localClient==null){
            if (keycode == Input.Keys.A) snake.turnLeft();
            else if (keycode == Input.Keys.D) snake.turnRight();
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

    private static boolean tooCloseY(SnakePart head, SnakePart neck) {
        return Math.abs(head.y - neck.y) <= head.radius * 2;
    }

    private static boolean tooCloseX(SnakePart head, SnakePart neck) {
        return Math.abs(head.x - neck.x) <= head.radius * 2;
    }

    private static boolean isTurning(SnakePart head, SnakePart neck) {
        return head.getDirection() != neck.getDirection();
    }
}
