package pentasnake.client.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import pentasnake.client.InputHandler;

import java.util.List;

public class Player_SnakeHead extends Actor {
    Circle headCollision;

    Texture head = new Texture("head2.png");
    public Sprite sprite = new Sprite(head);

    public Vector2 position;
    public Vector2 turning;
    int rotation;

    int rotationSpeed = 240;
    public float speed = 100;

    public int points;

    List<?> tails;

    boolean leftMove;
    boolean rightMove;

    private InputHandler handler;


    public Player_SnakeHead() {
        sprite.setScale(1f);
        position = new Vector2(0,Gdx.graphics.getHeight()/2);
        rotation = 0;
        handler = new InputHandler(this);
    }

    public InputHandler getHandler() {
        return handler;
    }

    public void _update(float deltaTime) {
        position.add(new Vector2(speed * deltaTime, speed * deltaTime).setAngleDeg(rotation));

        if (Gdx.input.isKeyPressed(Input.Keys.D))
            rotation -= rotationSpeed * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            rotation += rotationSpeed * deltaTime;

        if (rotation >= 360 || rotation <= -360)
            rotation = 0;

    }


    public void draw(SpriteBatch batch) {
//        _update(Gdx.graphics.getDeltaTime());
        update();
        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);
//        sprite.draw(batch);
        batch.draw(new TextureRegion(sprite.getTexture(), 0, 0, 64, 64),sprite.getX(),sprite.getY(),0,0,
                sprite.getWidth()*sprite.getScaleX(),sprite.getHeight()*sprite.getScaleY(),1,1,sprite.getRotation()
                );
    }

    public void setLeftMove(boolean b) {
        if (rightMove && b) rightMove = false;
        leftMove = b;
    }

    public void setRightMove(boolean b) {
        if (leftMove && b) leftMove = false;
        rightMove = b;
    }

    public void update() {
        float dt = Gdx.graphics.getDeltaTime();
        position.add(new Vector2(speed * dt, speed * dt).setAngleDeg(rotation));
        if (leftMove) {
            rotation += rotationSpeed * dt;
        }
        if (rightMove) {
            rotation -= rotationSpeed * dt;
        }
        if (rotation >= 360 || rotation <= -360)
            rotation = 0;
    }
}
