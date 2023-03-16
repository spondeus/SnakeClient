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
public class EnergyDrink extends PickupItems {

    public EnergyDrink(float x, float y, Stage stage) {
        super(x,y,stage);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("energydrink.png")));
        float drinkSize = 32;
        TextureRegion drinkRegion = new TextureRegion(region);
        drinkRegion.setRegionWidth( (int) drinkSize);
        drinkRegion.setRegionHeight( (int) drinkSize);
        loadTexture("energydrink.png");
        setBoundaryPolygon(8);
    }

    @Override
    public void applyEffect(/*Snake snake*/) {
        // snake.speedUp(); - Speeds up snake movement
    }
}
