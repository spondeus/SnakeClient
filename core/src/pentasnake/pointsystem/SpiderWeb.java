package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import pentasnake.client.entities.Snake;


public class SpiderWeb extends PickupItems {
    public SpiderWeb(float x, float y, Stage stage, int id) {
        super(x, y, stage, id);
        setPoints(0);
        setSpawnRate(0.5f);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("spiderweb.png")));
        setBoundaryRectangle();

        type = Type.WEB;
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.slowDown();
    }
}
