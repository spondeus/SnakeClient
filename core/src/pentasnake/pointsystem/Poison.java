package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import lombok.Getter;
import pentasnake.client.entities.Snake;

public class Poison extends PickupItems {

    @Getter
    Type type = Type.POISON;

    public Poison(float x, float y, Stage stage) {
        super(x, y, stage);
        setPoints(-100);
        setSpawnRate(0.5f);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("poison.png")));
        setBoundaryRectangle();
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.shrink();
    }
}
