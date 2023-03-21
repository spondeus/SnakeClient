package pentasnake.client.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;

import static pentasnake.client.entities.Snake.SnakeDirection.*;

public class Snake extends Actor {

    public SnapshotArray<SnakePart> getParts() {
        return parts;
    }

    SnapshotArray<SnakePart> parts;

    private final SnakePart head;

    private int speed = 120;

    private int points;

    private final int initialParts = 4;

    private Circle eye1, eye2;
    private Circle innerEye1, innerEye2;

    private Color eyeColor = Color.WHITE;
    private Color innerEyeColor = Color.BLACK;
    private final ShapeRenderer sr = new ShapeRenderer();

    private final float angle = (float) (360.0 / 4.0);

    private SnapshotArray<SnakePart> colliders = new SnapshotArray<SnakePart>();


    public Snake(int x, int y, int radius, Color bodyColor) {
        head = new SnakePart(x, y, radius, Color.ORANGE, E);
        this.parts = new SnapshotArray<>();
        this.parts.begin();
        this.parts.add(head);
        for (int i = 1; i <= initialParts; i++) {
            x -= 2 * radius;
            y = y;
            SnakePart body = new SnakePart(x, y, radius, bodyColor, E);
            this.parts.add(body);
        }
        x -= 1.5 * radius;
        y = y;
        SnakePart tail = new SnakePart(x, y, radius / 2.0f, bodyColor, E);
        this.parts.add(tail);
        this.parts.end();
        eye1 = new Circle();
        eye2 = new Circle();
        eye1.radius = eye2.radius = head.radius / 4;
        innerEye1 = new Circle();
        innerEye2 = new Circle();
        innerEye1.radius = innerEye2.radius = eye1.radius / 2;
        points = 0;
    }


    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        sr.setAutoShapeType(true);
        for (SnakePart part : this.parts) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(part.getColor());
            if (colliders.contains(part, true)) sr.setColor(Color.RED);
            sr.circle(part.x, part.y, part.radius, 100);
//            if (part == head) {
//                switch (head.getDirection()) {
//                    case N:
//                    case S:
//                        eye1.x = head.x - eye1.radius - 2;
//                        eye1.y = eye2.y = head.y + (head.getDirection() == N ? 3 : -3);
//                        eye2.x = head.x + eye2.radius + 2;
//                        innerEye1.x = eye1.x;
//                        innerEye2.x = eye2.x;
//                        innerEye1.y = innerEye2.y = eye1.y;
//                        break;
//                    case E:
//                    case W:
//                        eye1.x = eye2.x = head.x + (head.getDirection() == E ? -3 : 3);
//                        eye1.y = head.y - eye1.radius - 2;
//                        eye2.y = head.y + eye2.radius + 2;
//                        innerEye1.x = innerEye2.x = eye1.x;
//                        innerEye1.y = eye1.y;
//                        innerEye2.y = eye2.y;
//                        break;
//                }
//                sr.setColor(eyeColor);
//                sr.circle(eye1.x, eye1.y, eye1.radius);
//                sr.circle(eye2.x, eye2.y, eye2.radius);
//                sr.setColor(innerEyeColor);
//                sr.circle(innerEye1.x, innerEye1.y, innerEye1.radius);
//                sr.circle(innerEye2.x, innerEye2.y, innerEye2.radius);
//            }
            sr.end();
        }
        batch.begin();
    }

    private boolean selfCollision() {
        for (int i = 0; i < this.parts.size; i++) {
            for (int j = 0; j < this.parts.size; j++) {
                if (Math.abs(i - j) < 2) continue;
                if (this.parts.get(i).overlaps(this.parts.get(j))) {
                    colliders.add(parts.get(i));
                    colliders.add(parts.get(j));
                    return true;
                }
            }
        }
        return false;
    }

    public void act(float delta) {
        if (selfCollision()) return;
        float movement = 1 / 60f * speed;
        for (int i = 0; i < this.parts.size; i++) {
            SnakePart part = this.parts.get(i);
            SnakePart prev = (i == 0) ? null : this.parts.get(i - 1);
            if (prev != null) {
                SnakeDirection prevDir = prev.getDirection();
                float deltaX = Math.abs(part.x - prev.x);
                float deltaY = Math.abs(part.y - prev.y);
                float radius2=part.radius+ prev.radius;
                if (prevDir == N || prevDir == S) {         // ha elkanyarodott felfele
                    if (deltaX < 1) {                       // ha egy vonalba kerülnek
                        part.x = prev.x;                    // legyenek teljesen egy vonalban
                        part.setDirection(prevDir);         // és váltson irányt a hátsó tag is
                    }
                } else if (prevDir == E || prevDir == W) {
                    if (deltaY < 1) {
                        part.y = prev.y;
                        part.setDirection(prevDir);
                    }
                } else if (prevDir == NE || prevDir == SW) {
                    if (deltaX*deltaX + deltaY*deltaY > radius2*radius2) {
                        part.y = prev.y - (radius2) / (float) Math.pow(2, 0.5);
                        part.x = prev.x - (radius2) / (float) Math.pow(2, 0.5);
                        part.setDirection(prevDir);
                    }
                } else if (prevDir == NW || prevDir == SE) {
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
                case NE:
                    part.x += movement / 2;
                    part.y += movement / 2;
                    break;
                case E:
                    part.x += movement;
                    break;
                case SE:
                    part.x += movement / 2;
                    part.y -= movement / 2;
                    break;
                case S:
                    part.y -= movement;
                    break;
                case SW:
                    part.x -= movement / 2;
                    part.y -= movement / 2;
                    break;
                case W:
                    part.x -= movement;
                    break;
                case NW:
                    part.x -= movement / 2;
                    part.y += movement / 2;
                    break;
            }
            if (part.x < 0) part.x = Gdx.graphics.getWidth();
            if (part.x > Gdx.graphics.getWidth()) part.x = 0;
            if (part.y < 0) part.y = Gdx.graphics.getHeight();
            if (part.y > Gdx.graphics.getHeight()) part.y = 0;
        }

    }

    public void turnRight() {
        switch (head.getDirection()) {
            case N:
                head.setDirection(NE);
                break;
            case NE:
                head.setDirection(E);
                break;
            case E:
                head.setDirection(SE);
                break;
            case SE:
                head.setDirection(S);
                break;
            case S:
                head.setDirection(SW);
                break;
            case SW:
                head.setDirection(W);
                break;
            case W:
                head.setDirection(NW);
                break;
            case NW:
                head.setDirection(N);
                break;
        }
    }

    public void turnLeft() {
        float dt = Gdx.graphics.getDeltaTime();
        switch (head.getDirection()) {
            case N:
                head.setDirection(NW);
                break;
            case NW:
                head.setDirection(W);
                break;
            case W:
                head.setDirection(SW);
                break;
            case SW:
                head.setDirection(S);
                break;
            case S:
                head.setDirection(SE);
                break;
            case SE:
                head.setDirection(E);
                break;
            case E:
                head.setDirection(NE);
                break;
            case NE:
                head.setDirection(N);
                break;
        }
    }

    public void slowDown() {
        if (speed > 20) speed -= 20;
    }

    public void speedUp() {
        speed += 20;
    }

    public void grow() {
        parts.begin();
        SnakePart beforeTail = parts.get(parts.size - 2);
        SnakePart tail = parts.get(parts.size - 1);
        SnakePart newBeforeTail = new SnakePart(
                parts.get(parts.size - 2).x,
                parts.get(parts.size - 2).y,
                parts.get(parts.size - 2).radius,
                parts.get(parts.size - 2).getColor(),
                parts.get(parts.size - 2).getDirection());
        switch (newBeforeTail.getDirection()) {
            case N:
                beforeTail.y -= 2 * beforeTail.radius;
                tail.y -= 2 * beforeTail.radius;
                break;
            case S:
                beforeTail.y += 2 * beforeTail.radius;
                tail.y += 2 * beforeTail.radius;
                break;
            case E:
                beforeTail.x += 2 * beforeTail.radius;
                tail.x += 2 * beforeTail.radius;
                break;
            case W:
                beforeTail.x -= 2 * beforeTail.radius;
                tail.x -= 2 * beforeTail.radius;
                break;
        }
        this.parts.insert(parts.size - 2, newBeforeTail);
        parts.end();
    }

    public void shrink() {
        parts.begin();
        SnakePart beforeTail = parts.get(parts.size - 2);
        SnakePart tail = parts.get(parts.size - 1);
        beforeTail.radius = tail.radius;
        switch (beforeTail.getDirection()) {
            case N:
                beforeTail.y += beforeTail.radius;
                break;
            case S:
                beforeTail.y -= beforeTail.radius;
                break;
            case E:
                beforeTail.x -= beforeTail.radius;
                break;
            case W:
                beforeTail.x += beforeTail.radius;
                break;
        }
        parts.removeValue(tail, true);
        parts.end();
    }

    enum SnakeDirection {N, NE, E, SE, S, SW, W, NW}

    public SnakePart getHead() {
        return head;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}


