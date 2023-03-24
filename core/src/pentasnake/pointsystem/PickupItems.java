package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import lombok.*;
import pentasnake.client.entities.Snake;
import pentasnake.temporaryclasses.BaseActorTEMP;

@NoArgsConstructor
public abstract class PickupItems extends BaseActorTEMP {

    @Getter
    @Setter
    private int points;
    protected boolean collected;
    @Getter
    private final int SIZE = 60;

    @Getter
    @Setter
    private float spawnRate;
    protected TextureRegion region;

    public PickupItems(float x, float y, Stage stage) {
        super(x, y, stage);
    }

    public abstract void applyEffect(Snake snake);

    public void collectItem(Snake snake) {
        collected = true;
        clearActions();
        addAction(Actions.after(Actions.removeActor()));
        updatePlayerScore(snake);
    }

    public void updatePlayerScore(Snake snake) {
        if (collected) {
            snake.setPoints(snake.getPoints() + getPoints());
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, getX(), getY(), SIZE, SIZE);
    }

    public void setBoundaryRectangle() {
        boundaryRectangle = new Rectangle(getX(), getY(), SIZE, SIZE);
        ;
    }
}
