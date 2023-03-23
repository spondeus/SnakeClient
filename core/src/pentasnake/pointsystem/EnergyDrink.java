package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import lombok.Getter;
import lombok.Setter;
import pentasnake.client.entities.Snake;


public class EnergyDrink extends PickupItems {

    public EnergyDrink(float x, float y, Stage stage) {
        super(x,y,stage);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("energydrink.png")));
//        float drinkSize = 100;
//        region.setRegionWidth( (int) drinkSize);
//        region.setRegionHeight( (int) drinkSize);
//        loadTexture("energydrink.png");
        setBoundaryRectangle();
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.speedUp();
    }
}
