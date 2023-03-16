package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Poison extends PickupItems {
    public Poison(float x, float y, Stage stage, int points) {
        super(x, y, stage, -100);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("poison.png")));
        float poisonSize = 32;
        TextureRegion poisonRegion = new TextureRegion(region);
        poisonRegion.setRegionWidth( (int) poisonSize);
        poisonRegion.setRegionHeight( (int) poisonSize);
        loadTexture("assets/poison.png");
        setBoundaryPolygon(8);
    }

    @Override
    public void applyEffect(/*Snake snake*/) {
        // snake.shrink(); - Decreases the length of the snake
    }
}
