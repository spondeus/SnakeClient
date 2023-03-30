package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pentasnake.client.entities.Snake;

public class Food extends PickupItems {
    public final int MAX_FOOD = 6;

    public int getMAX_FOOD() {
        return MAX_FOOD;
    }

    Type type = Type.FOOD;
    private static int count = 0;
    public static int getCount() {
        return count;
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
