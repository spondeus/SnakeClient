package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import lombok.Getter;
import pentasnake.client.entities.Snake;

public class Food extends PickupItems {

    @Getter
    Type type = Type.FOOD;
    private static int count = 0;
    public static int getCount() {
        return count;
    }

    public Food(float x, float y, Stage stage) {
        super(x, y, stage);
        setPoints(50);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("food.png")));
        setBoundaryRectangle();
    }
    @Override
    public void applyEffect(Snake snake) {
        snake.grow();
    }

}
