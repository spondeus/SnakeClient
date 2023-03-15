package pentasnake.client.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.SnapshotArray;

import static pentasnake.client.entities.Snake.SnakeDirection.*;

public class Snake extends SnapshotArray<SnakePart> {

    private final SnakePart head;

    private int speed=120;

    private Circle eye1, eye2;
    private Circle innerEye1, innerEye2;

    private Color eyeColor = Color.WHITE;
    private Color innerEyeColor=Color.BLACK;

    private final ShapeRenderer sr = new ShapeRenderer();

    private final float angle = (float) (360.0 / 4.0);


    public Snake(int x, int y, int radius, Color bodyColor) {
        head = new SnakePart(x, y, radius, Color.ORANGE, W);
        this.add(head);
        for (int i = 1; i <= 4; i++) {
            x -= 2 * radius;
            y = y;
            SnakePart body = new SnakePart(x, y, radius, bodyColor, W);
            this.add(body);
        }
        x -= 1.5 * radius;
        y = y;
        SnakePart tail = new SnakePart(x, y, radius / 2.0f, bodyColor, W);
        this.add(tail);
        eye1 = new Circle();
        eye2 = new Circle();
        eye1.radius = eye2.radius = head.radius / 4;
        innerEye1=new Circle();
        innerEye2=new Circle();
        innerEye1.radius= innerEye2.radius=eye1.radius/2;
    }

    public void draw() {
        if (!selfCollision()) update();
        sr.setAutoShapeType(true);
        for (SnakePart part : this) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(part.getColor());
            sr.circle(part.x, part.y, part.radius, 100);
            if (part == head) {
                switch (head.getDirection()) {
                    case N:
                    case S:
                        eye1.x = head.x - eye1.radius - 2;
                        eye1.y = eye2.y = head.y + (head.getDirection() == N ? 3 : -3);
                        eye2.x = head.x + eye2.radius + 2;
                        innerEye1.x = eye1.x;
                        innerEye2.x = eye2.x;
                        innerEye1.y = innerEye2.y = eye1.y;
                        break;
                    case E:
                    case W:
                        eye1.x = eye2.x = head.x + (head.getDirection() == E ? -3 : 3);
                        eye1.y = head.y - eye1.radius - 2;
                        eye2.y = head.y + eye2.radius + 2;
                        innerEye1.x = innerEye2.x = eye1.x;
                        innerEye1.y = eye1.y;
                        innerEye2.y = eye2.y;
                        break;
                }
                sr.setColor(eyeColor);
                sr.circle(eye1.x, eye1.y, eye1.radius);
                sr.circle(eye2.x, eye2.y, eye2.radius);
                sr.setColor(innerEyeColor);
                sr.circle(innerEye1.x, innerEye1.y, innerEye1.radius);
                sr.circle(innerEye2.x, innerEye2.y, innerEye2.radius);
            }
            sr.end();
        }
    }

    private boolean selfCollision() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (Math.abs(i - j) < 2) continue;
                if (this.get(i).overlaps(this.get(j))) return true;
            }
        }
        return false;
    }

    private void update() {
        float dt = Gdx.graphics.getDeltaTime();

        float movement = dt * speed;

        for (int i = 0; i < this.size; i++) {
            SnakePart part = this.get(i);
            SnakePart prev = (i == 0) ? null : this.get(i - 1);
            if (prev != null) {
                SnakeDirection prevDir = prev.getDirection();
                if (prevDir == N || prevDir == S) {
                    if (Math.abs(part.x - prev.x) < 1) {
                        part.x = prev.x;
                        part.setDirection(prevDir);
                    }
                } else {
                    if (Math.abs(part.y - prev.y) < 1) {
                        part.y = prev.y;
                        part.setDirection(prevDir);
                    }
                }
            }
            switch (part.getDirection()) {
                case N:
                    part.y += movement;
                    break;
                case S:
                    part.y -= movement;
                    break;
                case E:
                    part.x -= movement;
                    break;
                case W:
                    part.x += movement;
                    break;
            }
        }

    }

    public void turnLeft() {
        switch (head.getDirection()) {
            case N:
                head.setDirection(E);
                break;
            case E:
                head.setDirection(S);
                break;
            case S:
                head.setDirection(W);
                break;
            case W:
                head.setDirection(N);
                break;
        }
    }

    public void turnRight() {
        float dt = Gdx.graphics.getDeltaTime();
        switch (head.getDirection()) {
            case N:
                head.setDirection(W);
                break;
            case E:
                head.setDirection(N);
                break;
            case S:
                head.setDirection(E);
                break;
            case W:
                head.setDirection(S);
                break;
        }
    }

    enum SnakeDirection {N, E, S, W;}

    public SnakePart getHead() {
        return head;
    }
}


