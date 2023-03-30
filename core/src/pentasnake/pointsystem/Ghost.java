package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pentasnake.client.entities.Snake;

public class Ghost extends PickupItems {

    public Ghost(float x, float y, Stage stage, int id) {
        super(x, y, stage, id);
        setPoints(0);
        setSpawnRate(0.1f);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("ghost.png")));
        setBoundaryRectangle();

        type = Type.GHOST;
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.ghostMode();
    }
}
