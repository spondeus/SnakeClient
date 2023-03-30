package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pentasnake.client.entities.Snake;

public class Ghost extends PickupItems {

    Type type = Type.GHOST;
    private static int count = 0;
    public static int getCount() {
        return count;
    }

    public Ghost(float x, float y, Stage stage, int id) {
        super(x, y, stage, id);
        setPoints(0);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("ghost.png")));
        setBoundaryRectangle();

        type = Type.GHOST;
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.ghostMode();
    }
}
