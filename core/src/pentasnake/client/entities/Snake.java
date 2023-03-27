package pentasnake.client.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.ArrayList;
import java.util.List;

//import static pentasnake.client.entities.Snake.SnakeDirection.*;

public class Snake extends Actor {

    public SnapshotArray<SnakePart> getParts() {
        return parts;
    }

    SnapshotArray<SnakePart> parts;

    private final SnakePart head;
    private int speed;
    private int newSpeed;
    private static final int DEFAULT_SPEED = 400;
    private static final int MAX_SPEED = 800;

    private static final int MIN_SPEED = 10;
    private static final int SPEED_CHANGE = 50;
    private int points;

    private static final int initialParts = 4;
    private static final int minParts = 3;

    private Circle eye1, eye2;
    private Circle innerEye1, innerEye2;

    private Color eyeColor = Color.WHITE;
    private Color innerEyeColor = Color.BLACK;
    private final ShapeRenderer sr = new ShapeRenderer();

    private SnapshotArray<SnakePart> colliders = new SnapshotArray<>();

    private static final float sqrt2 = (float) Math.pow(2, 0.5);

    private static final Vector2 initialDirection = new Vector2(1, 0);

    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private boolean leftMove, rightMove;

    private int isGrowing = 0;

    public Snake(int x, int y, int radius, Color bodyColor, int id) {
        head = new SnakePart(x, y, radius, Color.ORANGE, initialDirection);
        this.parts = new SnapshotArray<>();
        this.parts.begin();
        this.parts.add(head);
        for (int i = 1; i <= initialParts; i++) {
            x -= 2 * radius;
            y = y;
            SnakePart body = new SnakePart(x, y, radius, bodyColor, initialDirection);
            this.parts.add(body);
        }
        x -= 1.5 * radius;
        y = y;
        SnakePart tail = new SnakePart(x, y, radius / 2.0f, bodyColor, initialDirection);
        this.parts.add(tail);
        this.parts.end();
        eye1 = new Circle();
        eye2 = new Circle();
        eye1.radius = eye2.radius = head.radius / 4;
        innerEye1 = new Circle();
        innerEye2 = new Circle();
        innerEye1.radius = innerEye2.radius = eye1.radius / 2;
        points = 0;
        this.speed = this.newSpeed = DEFAULT_SPEED;
        points = 0;

        this.id = id;

    }


    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        sr.setAutoShapeType(true);
        for (SnakePart part : this.parts) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(part.getColor());
            if (colliders.contains(part, true)) sr.setColor(Color.RED);
            float x2 = part.x % Gdx.graphics.getWidth();
            if (x2 < 0) x2 += Gdx.graphics.getWidth();
            float y2 = part.y % Gdx.graphics.getHeight();
            if (y2 < 0) y2 += Gdx.graphics.getHeight();
            sr.circle(x2, y2, part.radius, 100);
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
//                    case NE:
//                    case SW:
//                        eye1.x = head.x - eye1.radius / sqrt2 - 1;
//                        eye1.y = head.y + eye1.radius / sqrt2 + 1;
//                        eye2.x = head.x + eye2.radius / sqrt2 + 1;
//                        eye2.y = head.y - eye2.radius / sqrt2 - 1;
//                        innerEye1.x = eye1.x;
//                        innerEye2.x = eye2.x;
//                        innerEye1.y = eye1.y;
//                        innerEye2.y = eye2.y;
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
//                    case NW:
//                    case SE:
//                        eye1.x = head.x + eye1.radius / sqrt2 + 1;
//                        eye1.y = head.y + eye1.radius / sqrt2 + 1;
//                        eye2.x = head.x - eye2.radius / sqrt2 - 1;
//                        eye2.y = head.y - eye2.radius / sqrt2 - 1;
//                        innerEye1.x = eye1.x;
//                        innerEye2.x = eye2.x;
//                        innerEye1.y = eye1.y;
//                        innerEye2.y = eye2.y;
//                        break;
//
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
        if (isGrowing > 0) {
            isGrowing--;
            return false;
        }
        for (int i = 0; i < this.parts.size; i++) {
            for (int j = 0; j < this.parts.size; j++) {
                if (Math.abs(i - j) < 2) continue;
                if (this.parts.get(i).overlaps(this.parts.get(j))) {
                    colliders.add(parts.get(i));
                    colliders.add(parts.get(j));
                    speed = 0;
                    return true;
                }
            }
        }
        return false;
    }

    public void act(float delta) {
        if (selfCollision()) return;
        if (newSpeed != speed) {
            if (newSpeed > speed) speed++;
            else speed--;
        }
        float movement = 1 / 60f * speed;
        float diagonal = movement / sqrt2;
        for (int i = 0; i < parts.size; i++) {
            SnakePart part = this.parts.get(i);
            SnakePart prev = (i == 0) ? null : this.parts.get(i - 1);
            changeDirection(part, prev);

            part.x += speed / 100 * part.getDirectionVector().x;
            part.y += speed / 100 * part.getDirectionVector().y;
        }

    }

    public void turnRight() {
        head.rotate(speed / 140);
//        if (head.getDirection() != parts.get(1).getDirection()) return;
//        switch (head.getDirection()) {
//            case N:
//                head.setDirection(NE);
//                break;
//            case NE:
//                head.setDirection(E);
//                break;
//            case E:
//                head.setDirection(SE);
//                break;
//            case SE:
//                head.setDirection(S);
//                break;
//            case S:
//                head.setDirection(SW);
//                break;
//            case SW:
//                head.setDirection(W);
//                break;
//            case W:
//                head.setDirection(NW);
//                break;
//            case NW:
//                head.setDirection(N);
//                break;
//        }
    }

    public void turnLeft() {
        head.rotate(-speed / 140);
//        if (head.getDirection() != parts.get(1).getDirection()) return;
//        switch (head.getDirection()) {
//            case N:
//                head.setDirection(NW);
//                break;
//            case NW:
//                head.setDirection(W);
//                break;
//            case W:
//                head.setDirection(SW);
//                break;
//            case SW:
//                head.setDirection(S);
//                break;
//            case S:
//                head.setDirection(SE);
//                break;
//            case SE:
//                head.setDirection(E);
//                break;
//            case E:
//                head.setDirection(NE);
//                break;
//            case NE:
//                head.setDirection(N);
//                break;
//        }
    }

    public void slowDown() {
        if (speed > MIN_SPEED) newSpeed = speed - SPEED_CHANGE;
    }

    public void speedUp() {
        if (speed < MAX_SPEED) newSpeed = speed + SPEED_CHANGE;
    }


    public void grow() {
        parts.begin();
        SnakePart tail = parts.get(parts.size - 1);
        Vector2 pos = new Vector2(tail.x, tail.y);
        Vector2 move = new Vector2(-4 * tail.getDirectionVector().x * tail.radius, -4 * tail.getDirectionVector().y * tail.radius);
        pos.add(move);
        tail.x -= tail.getDirectionVector().x * tail.radius;
        tail.y -= tail.getDirectionVector().y * tail.radius;
        SnakePart newTail = new SnakePart(pos.x, pos.y, tail.radius, tail.getColor(), tail.getDirectionVector());
        tail.radius *= 2;
        parts.add(newTail);
        parts.end();
    }

    public void shrink() {
        if (parts.size <= minParts) return;
        parts.begin();
        SnakePart beforeTail = parts.get(parts.size - 2);
        SnakePart tail = parts.get(parts.size - 1);
        beforeTail.radius = tail.radius;
        parts.removeValue(tail, true);
        parts.end();
    }

    public void freeze() {
        /*speed = 0;
        head.setColor(Color.CYAN);
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    speed = DEFAULT_SPEED;
                    head.setColor(Color.ORANGE);
                }
            }, 5, TimeUnit.SECONDS);*/
    }

    public void ghostMode() {
        // enables snake to go through obstacles, walls, other snakes, but not itself
    }

    public SnakePart getHead() {
        return head;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    private void changeDirection(SnakePart part, SnakePart prev) {
        if (prev != null) {
            float radius2 = prev.radius + part.radius;
            float deltaX = prev.x - part.x;
            float deltaY = prev.y - part.y;
            if (!part.overlaps(prev)) part.setDirectionVector(new Vector2(deltaX / radius2, deltaY / radius2));
        }
    }

    public void setLeftMove(boolean leftMove) {
        if (rightMove && leftMove) rightMove = false;
        this.leftMove = leftMove;
    }

    public void setRightMove(boolean rightMove) {
        if (rightMove && leftMove) leftMove = false;
        this.rightMove = rightMove;
    }

    public boolean isLeftMove() {
        return leftMove;
    }

    public boolean isRightMove() {
        return rightMove;
    }

    public int getSpeed() {
        return speed;
    }
}


