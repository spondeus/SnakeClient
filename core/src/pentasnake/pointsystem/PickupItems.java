package pentasnake.pointsystem;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import pentasnake.client.entities.Snake;
import pentasnake.temporaryclasses.BaseActor;

public abstract class PickupItems extends BaseActor {

    protected Type type;

    public Type getType(){
        return type;
    }

    private int id;

    public int getId(){
        return id;
    }

    // For the MVP, only 4 pickup types, food and poison for score, energy drink and spider web for speed change
    // Need to come up with more pickup types for end product
    private int points;
    boolean collected;

    public void setPoints(int points) {
        this.points = points;
    }

    private final int SIZE=60;

    protected TextureRegion region;

    public PickupItems() {}

    public PickupItems(float x, float y, Stage stage, int id){
        super(x,y,stage);
        points = 0; // default value
        this.id = id;
    }

    public PickupItems(float x, float y, Stage stage, int points, int id){
        super(x,y,stage);
        this.points = points;
        this.id = id;
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
            if(this.getPoints()!=0) snake.setPoints(snake.getPoints()+getPoints());
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, getX(), getY(), SIZE, SIZE);
    }

    public void setBoundaryRectangle() {
        boundaryRectangle = new Rectangle(getX(), getY(), SIZE, SIZE);
    }

    public float getRadius() {
        return 30f;
    }
}
