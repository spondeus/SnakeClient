package pentasnake.pickups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pentasnake.client.entities.Snake;

public class SpiderWeb extends PickupItems {
    public SpiderWeb(float x, float y, Stage stage, int id) {
        super(x, y, stage, id);
        setPoints(0);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("spiderweb.png")));
        setBoundaryRectangle();
        Type type = Type.WEB;
    }
    public SpiderWeb(float x, float y, Stage stage) {
        super(x, y, stage,0);
        setPoints(0);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("spiderweb.png")));
        setBoundaryRectangle();

        type = Type.WEB;
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.slowDown();
    }
}
