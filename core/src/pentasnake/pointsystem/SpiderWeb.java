package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpiderWeb extends PickupItems {

    public SpiderWeb(float x, float y, Stage stage){
        super(x,y,stage);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("spiderweb.png")));
        float webSize = 32;
        TextureRegion webRegion = new TextureRegion(region);
        webRegion.setRegionWidth( (int) webSize);
        webRegion.setRegionHeight( (int) webSize);
        loadTexture("spiderweb.png");
        setBoundaryPolygon(8);
    }

    @Override
    public void applyEffect(/*Snake snake*/) {
        // snake.slowDown(); - Slows down snake movement
    }
}
