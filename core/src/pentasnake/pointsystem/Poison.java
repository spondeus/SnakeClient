package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pentasnake.client.entities.Snake;

public class Poison extends PickupItems {
    public Poison(float x, float y, Stage stage, int id) {
        super(x, y, stage, id);
        setPoints(-100);
        setSpawnRate(0.5f);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("poison.png")));
        setBoundaryRectangle();

        type = Type.POISON;
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.shrink();
    }
}
