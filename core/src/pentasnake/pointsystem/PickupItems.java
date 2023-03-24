package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import pentasnake.client.entities.Snake;
import pentasnake.temporaryclasses.BaseActorTEMP;

public abstract class PickupItems extends BaseActorTEMP {

    // For the MVP, only 4 pickup types, food and poison for score, energy drink and spider web for speed change
    // Need to come up with more pickup types for end product
    private int points;
    boolean collected;

    float spawnRate;

    public void setSpawnRate(float spawnRate) {
        this.spawnRate = spawnRate;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    private int size=40;

    protected TextureRegion region;

    public PickupItems() {
    }

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

    public abstract void applyEffect(Snake snake);

    public void collectItem(Snake snake) {
        collected = true;
        clearActions();
        addAction(Actions.after(Actions.removeActor()));
        updatePlayerScore(snake);
    }

    public void updatePlayerScore(Snake snake){
        if (collected) {
            snake.setPoints(snake.getPoints()+getPoints());
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, getX(), getY(), size, size);
    }

    public void setBoundaryRectangle() {
        boundaryRectangle = new Rectangle(getX(), getY(), size, size);
        ;
    }

}
