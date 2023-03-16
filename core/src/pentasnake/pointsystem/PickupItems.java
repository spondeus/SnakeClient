package pentasnake.pointsystem;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import lombok.*;
import pentasnake.temporaryclasses.BaseActorTEMP;

@NoArgsConstructor
public abstract class PickupItems extends BaseActorTEMP {

    // For the MVP, only 4 pickup types, food and poison for score, energy drink and spider web for speed change
    // Need to come up with more pickup types for end product
    private int points;
    boolean collected;

    public PickupItems(float x, float y, Stage stage){
        super(x,y,stage);
        points = 0; // default value
    }

    public PickupItems(float x, float y, Stage stage, int points){
        super(x,y,stage);
        this.points = points;
    }

    public int getPoints(){
        return this.points;
    }

    public abstract void applyEffect(/*Snake snake*/);

    public void collectItem() {
        collected = true;
        clearActions();
        addAction(Actions.after(Actions.removeActor()));
        updatePlayerScore();
    }

    public void updatePlayerScore(){
        if (collected) {
            long updatedPlayerScore = getPlayerScore() + (long) getPoints();
        }
    }

}
