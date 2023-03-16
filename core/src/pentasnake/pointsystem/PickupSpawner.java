package pentasnake.pointsystem;

import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.ArrayList;
import java.util.List;

public class PickupSpawner {

    private List<PickupItems> pickups;

    public PickupSpawner(){
        pickups = new ArrayList<>();
        pickups.add(new Food(0,0,null,50));
        pickups.add(new Poison(0,0,null, -100));
        pickups.add(new EnergyDrink(0,0,null));
        pickups.add(new SpiderWeb(0,0,null));
    }
    
    public void spawnPickups(){
        // Spawns pickups in the game world randomly
        // Spawn rate will be different for each pickup
    }
}
