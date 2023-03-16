package pentasnake.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class SnakePart extends Circle {

    private Color color;

    private Snake.SnakeDirection direction;

    public SnakePart(float x, float y, float radius, Color color, Snake.SnakeDirection direction) {
        super(x, y, radius);
        this.color = color;
        this.direction = direction;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Snake.SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(Snake.SnakeDirection direction) {
        this.direction = direction;
    }
}
