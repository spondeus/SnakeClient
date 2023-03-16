package pentasnake.pointsystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Food extends PickupItems {

    public Food(float x, float y, Stage stage, int points){
        super(x,y,stage,50);
        loadTexture("assets/food.png");
        setBoundaryPolygon(8);
    }

    @Override
    public void applyEffect(/*Snake snake*/) {
        // snake.grow(); - Increases the length of the snake
    }
}
