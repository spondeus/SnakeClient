package pentasnake.pointsystem;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Food extends PickupItems {
    public Food(Stage stage){
        super(50);
        loadTexture("assets/food.png");
        setBoundaryPolygon(8);
    }

    @Override
    public void applyEffect(/*Snake snake*/) {
        // snake.grow(); - Increases the length of the snake
    }

    @Override
    public void collect() {
        collected = true;
        clearActions();
        addAction(Actions.after(Actions.removeActor()));
        addPointsToPlayerScore();
    }

    private void addPointsToPlayerScore() {
        long updatedScore = getPlayerScore() + getPoints();
    }
}
