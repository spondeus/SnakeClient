package pentasnake.client.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import lombok.val;
import lombok.var;

import java.util.List;

import static com.badlogic.gdx.Input.*;


public class Player_SnakeHead extends Actor{
    Circle headCollision;

    Texture head = new Texture("head.png");
    public Sprite sprite= new Sprite(head);

    public Vector2 position;
    float rotation;
    
    public Vector2 mousePosition;
    public boolean cursorRotate = false;

    int rotationSpeed = 120;
    public float speed = 100;       // base 200

    public int points;

    OrthographicCamera cam = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

    List<?> tails;


    public Player_SnakeHead(){
        sprite.setScale(0.25f);
        position = new Vector2(0,0);
        rotation = 0;
        mousePosition = new Vector2(0,0);
    }

    public void _update(float deltaTime){
        mousePosition.x = Gdx.input.getX();
        mousePosition.y = Gdx.input.getY();

        position.add(new Vector2(speed*deltaTime,speed*deltaTime).setAngleDeg(rotation));

        if(cursorRotate){

        }
        else {
            if(Gdx.input.isKeyPressed(Keys.D))
                rotation -= rotationSpeed*deltaTime;
            if(Gdx.input.isKeyPressed(Keys.A))
                rotation += rotationSpeed*deltaTime;

            if(rotation >= 360 || rotation <= -360)
                rotation = 0;
        }

    }

    public void draw(SpriteBatch batch){
        _update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);
        sprite.draw(batch);
    }

}
