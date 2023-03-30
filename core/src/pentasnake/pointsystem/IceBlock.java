package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pentasnake.client.entities.Snake;

public class IceBlock extends PickupItems {
    public IceBlock(float x, float y, Stage stage, int id) {
        super(x, y, stage, id);
        setPoints(0);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("iceblock.png")));
        setBoundaryRectangle();
        Type type = Type.ICE;
    }
    private static int count = 0;
    public static int getCount() {
        return count;
    }

    public IceBlock(float x, float y, Stage stage) {
        super(x, y, stage,0);
        setPoints(0);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("iceblock.png")));
        setBoundaryRectangle();

        type = Type.ICE;
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.freeze();
    }
}
