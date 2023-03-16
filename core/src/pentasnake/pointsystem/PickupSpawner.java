package pentasnake.pointsystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PickupSpawner implements PickupHandler {

    private List<PickupItems> pickups;

    Stage mainStage;

    public PickupSpawner(Stage mainStage){
        this.mainStage = mainStage;
        pickups = new ArrayList<>();
        pickups.add(new Food(0,0,mainStage,50));
        pickups.add(new Poison(0,0,mainStage, -100));
        pickups.add(new EnergyDrink(0,0,mainStage));
        pickups.add(new SpiderWeb(0,0,mainStage));
    }

    public List<PickupItems> getPickups(){
        return pickups;
    }

    @Override
    public void spawnPickups(){

        // Should spawn pickups in the game world randomly

        List<PickupItems> pickups = new ArrayList<>();


        pickups.add(new Food(500,300,new Stage(),50));
        pickups.add(new Poison(0,0,new Stage(), -100));
        pickups.add(new EnergyDrink(0,0,new Stage()));
        pickups.add(new SpiderWeb(0,0,new Stage()));

        Random random = new Random();
        PickupItems item = pickups.get(random.nextInt(pickups.size()));


        // Gdx.graphics.getWidth/height

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
        for (PickupItems p : pickups) {
            batch.draw(foodRegion, item.getX(), item.getY());
        }
        batch.end();*/

        // Spawns pickups in the game world randomly
        // Spawn rate will be different for each pickup
    }
}
