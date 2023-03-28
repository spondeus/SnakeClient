package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import lombok.Getter;
import lombok.Setter;
import pentasnake.client.entities.Snake;


public class EnergyDrink extends PickupItems {

    @Getter
    Type type = Type.DRINK;
    private static int count = 0;
    public static int getCount() {
        return count;
    }

    public EnergyDrink(float x, float y, Stage stage) {
        super(x, y, stage);
        setPoints(0);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("energydrink.png")));
        setBoundaryRectangle();
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.speedUp();
    }
}
