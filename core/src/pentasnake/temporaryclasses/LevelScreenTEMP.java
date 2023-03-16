package pentasnake.temporaryclasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pentasnake.pointsystem.PickupItems;

@Getter
@Setter
@NoArgsConstructor
public class LevelScreenTEMP extends BaseScreenTEMP{
    @Override
    public void initialize() {
        BaseActorTEMP field = new BaseActorTEMP(0, 0, mainStage);
        field.loadTexture("assets/blackscreen.jpg");
        field.setSize(1280, 720);

        // food resize
        float foodSize = 32;
        Texture foodTexture = new Texture("food.png");
        TextureRegion foodRegion = new TextureRegion(foodTexture);
        foodRegion.setRegionWidth( (int) foodSize);
        foodRegion.setRegionHeight( (int) foodSize);

        // poison resize
        float poisonSize = 32;
        Texture poisonTexture = new Texture("posion.png");
        TextureRegion poisonRegion = new TextureRegion(poisonTexture);
        poisonRegion.setRegionWidth( (int) poisonSize);
        poisonRegion.setRegionHeight( (int) poisonSize);

        // energy drink resize
        float drinkSize = 32;
        Texture drinkTexture = new Texture("energydrink.png");
        TextureRegion drinkRegion = new TextureRegion(drinkTexture);
        drinkRegion.setRegionWidth( (int) drinkSize);
        drinkRegion.setRegionHeight( (int) drinkSize);

        // spider web resize
        float webSize = 32;
        Texture webTexture = new Texture("spiderweb.png");
        TextureRegion webRegion = new TextureRegion(webTexture);
        webRegion.setRegionWidth( (int) webSize);
        webRegion.setRegionHeight( (int) webSize);

        /*SpriteBatch batch = new SpriteBatch();
        batch.begin();
        for (PickupItems item : pickups) {
            batch.draw(foodRegion, item.getX(), item.getY());
        }
        batch.end();*/

    }

    @Override
    public void update(float dt) {

    }
}
