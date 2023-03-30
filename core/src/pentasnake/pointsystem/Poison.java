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
        this.region = new TextureRegion(new Texture(Gdx.files.internal("poison.png")));
        setBoundaryRectangle();
        Type type = Type.POISON;
    }
    private static int count = 0;
    public static int getCount() {
        return count;
    }

    public Poison(float x, float y, Stage stage) {
        super(x, y, stage,0);
        setPoints(-100);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("poison.png")));
        setBoundaryRectangle();

        type = Type.POISON;
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.shrink();
    }
}
