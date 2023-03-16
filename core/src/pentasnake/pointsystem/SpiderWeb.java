package pentasnake.pointsystem;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpiderWeb extends PickupItems {

    public SpiderWeb(float x, float y, Stage stage){
        super(x,y,stage);
        loadTexture("spiderweb.png");
        setBoundaryPolygon(8);
    }

    @Override
    public void applyEffect(/*Snake snake*/) {
        // snake.slowDown(); - Slows down snake movement
    }
}
