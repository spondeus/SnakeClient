package pentasnake.client.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.SnapshotArray;

import static pentasnake.client.entities.Snake.SnakeDirection.*;

public class Snake extends SnapshotArray<SnakePart> {

    private final SnakePart head;

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
    }

    public void draw() {
        if (!selfCollision()) update();
        sr.setAutoShapeType(true);
        for (SnakePart part : this) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(part.getColor());
            sr.circle(part.x, part.y, part.radius, 100);
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

        float movement = head.radius * 2 * dt * 3;

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
                head.setDirection(SnakeDirection.E);
                break;
            case E:
                head.setDirection(SnakeDirection.S);
                break;
            case S:
                head.setDirection(W);
                break;
            case W:
                head.setDirection(SnakeDirection.N);
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


