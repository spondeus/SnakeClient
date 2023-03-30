package pentasnake.client.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

import static com.badlogic.gdx.Input.*;


public class Player_SnakeHead extends Actor {
    Circle headCollision;

    Texture head = new Texture("head.png");
    public Sprite sprite = new Sprite(head);

    public Vector2 position;
    int rotation;

    int rotationSpeed = 120;
    public float speed = 200;

    public int points;

    List<?> tails;


    public Player_SnakeHead() {
        sprite.setScale(0.25f);
        position = new Vector2(0, 0);
        rotation = 0;
    }

    public void _update(float deltaTime) {
        position.add(new Vector2(speed * deltaTime, speed * deltaTime).setAngleDeg(rotation));

        if (Gdx.input.isKeyPressed(Keys.D))
            rotation -= rotationSpeed * deltaTime;
        if (Gdx.input.isKeyPressed(Keys.A))
            rotation += rotationSpeed * deltaTime;

        if (rotation >= 360 || rotation <= -360)
            rotation = 0;

    }

    public void draw(SpriteBatch batch) {
        _update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);
        sprite.draw(batch);
    }

}
