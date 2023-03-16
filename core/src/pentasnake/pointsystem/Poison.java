package pentasnake.pointsystem;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Poison extends PickupItems {
    public Poison(float x, float y, Stage stage, int points) {
        super(x, y, stage, -100);
        loadTexture("assets/poison.png");
        setBoundaryPolygon(8);
    }

    @Override
    public void applyEffect(/*Snake snake*/) {
        // snake.shrink(); - Decreases the length of the snake
    }
}
