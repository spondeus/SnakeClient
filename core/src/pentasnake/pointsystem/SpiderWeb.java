package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import lombok.Getter;
import lombok.Setter;
import pentasnake.client.entities.Snake;

@Getter
@Setter
public class SpiderWeb extends PickupItems {

    public SpiderWeb(float x, float y, Stage stage){
        super(x,y,stage);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("spiderweb.png")));
//        float webSize = 100;
//        region.setRegionWidth( (int) webSize);
//        region.setRegionHeight( (int) webSize);
//        loadTexture("spiderweb.png");
        setBoundaryRectangle();
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.slowDown();
    }
}
