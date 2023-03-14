package pentasnake.pointsystem;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public abstract class PickupItems extends BaseActorTEMP {

    // For the MVP, only 2 pickup types, food and poison
    // Need to come up with more pickup types for end product
    public int points;
    boolean collected = false;

    public PickupItems(int points){
        this.points = points;
    }
    public abstract void applyEffect(/*Snake snake*/);

    public abstract void collect();

}
