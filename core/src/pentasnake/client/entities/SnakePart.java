package pentasnake.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class SnakePart extends Circle {

    private Color color;

//    private Snake.SnakeDirection direction;

    private Vector2 directionVector;

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    public void setDirectionVector(Vector2 directionVector) {
        this.directionVector = directionVector;
    }

    public SnakePart(float x, float y, float radius, Color color, Vector2 direction) {
        super(x, y, radius);
        this.color = color;
        this.directionVector = direction;
    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void rotate(float degrees){
        this.directionVector.rotateDeg(degrees);
    }

//    public Snake.SnakeDirection getDirection() {
//        return direction;
//    }
//
//    public void setDirection(Snake.SnakeDirection direction) {
//        this.direction = direction;
//    }
}
