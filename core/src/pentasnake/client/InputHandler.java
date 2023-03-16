package pentasnake.client;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import pentasnake.client.entities.Snake;
import pentasnake.client.entities.SnakePart;

public class InputHandler extends InputAdapter {

    Snake snake;

    public InputHandler(Snake snake) {
        this.snake = snake;
    }

    @Override
    public boolean keyDown(int keycode) {
        SnakePart head = snake.getHead();
        SnakePart neck = snake.get(1);
        if (head.getDirection()!=neck.getDirection() && Math.abs(head.x - neck.x) <= head.radius * 2) return false;
        if (head.getDirection()!=neck.getDirection() && Math.abs(head.y - neck.y) <= head.radius * 2) return false;
        if (keycode == Input.Keys.A) snake.turnLeft();
        else if (keycode == Input.Keys.D) snake.turnRight();
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
