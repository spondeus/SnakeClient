package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pentasnake.client.entities.Snake;

@Getter
@Setter
public class Food extends PickupItems {

    public Food(float x, float y, Stage stage, int points){
        super(x,y,stage,50);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("food.png")));
//        float foodSize = 100;
//        region.setRegionWidth( (int) foodSize);
//        region.setRegionHeight( (int) foodSize);
//        loadTexture("assets/food.png");
        setBoundaryRectangle();
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.grow();
        snake.addPoints(this.getPoints());
    }
}
