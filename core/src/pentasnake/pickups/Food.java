package pentasnake.pickups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pentasnake.client.entities.Snake;

public class Food extends PickupItems {

    Type type;

    public Food(float x, float y, Stage stage) {
        super(x, y, stage, 0);
        setPoints(50);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("food.png")));
        setBoundaryRectangle();
        type= Type.FOOD;
    }

    public Food(float x, float y, Stage stage, int id) {
        super(x, y, stage, id);
        setPoints(50);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("food.png")));
        setBoundaryRectangle();
        type= Type.FOOD;
    }
    @Override
    public void applyEffect(Snake snake) {
        snake.grow();
    }

}