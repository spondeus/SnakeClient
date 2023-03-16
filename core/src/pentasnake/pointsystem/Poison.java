package pentasnake.pointsystem;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Poison extends PickupItems {
    public Poison(Stage stage){
        super(-100);
        loadTexture("assets/poison.png");
        setBoundaryPolygon(8);
    }

    @Override
    public void applyEffect(/*Snake snake*/) {
        // snake.shrink(); - Decreases the length of the snake
    }

    @Override
    public void collect() {
        collected = true;
        clearActions();
        addAction(Actions.after(Actions.removeActor()));
        subtractPointsFromPlayerScore();
    }

    private void subtractPointsFromPlayerScore() {
        long updatedScore = getPlayerScore() - getPoints();
    }
}
