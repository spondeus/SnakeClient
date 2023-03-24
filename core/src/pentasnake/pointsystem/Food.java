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

    public final int MAX_FOOD = 6;

    public int getMAX_FOOD() {
        return MAX_FOOD;
    }

    public Food(float x, float y, Stage stage) {
        super(x, y, stage);
        setPoints(50);
        setSpawnRate(1f);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("food.png")));
        setBoundaryRectangle();
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.grow();
    }
}
