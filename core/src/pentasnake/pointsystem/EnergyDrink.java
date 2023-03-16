package pentasnake.pointsystem;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnergyDrink extends PickupItems {

    public EnergyDrink(float x, float y, Stage stage) {
        super(x,y,stage);
        loadTexture("energydrink.png");
        setBoundaryPolygon(8);
    }

    @Override
    public void applyEffect(/*Snake snake*/) {
        // snake.speedUp(); - Speeds up snake movement
    }
}
