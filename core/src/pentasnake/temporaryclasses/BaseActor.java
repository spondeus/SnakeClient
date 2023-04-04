package pentasnake.temporaryclasses;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BaseActor extends Actor {

    public BaseActor() {
    }

    protected Rectangle boundaryRectangle;

    public Rectangle getBoundaryRectangle() {
        return boundaryRectangle;
    }

    public BaseActor(float x, float y, Stage stage){
        super();
        setPosition(x, y);
        stage.addActor(this);
    }

    public void setBoundaryRectangle() {
        float w = getWidth();
        float h = getHeight();
        boundaryRectangle = new Rectangle(0,0,w,h);
    }
}
