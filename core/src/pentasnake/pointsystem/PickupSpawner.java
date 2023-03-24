package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import lombok.Getter;

public class PickupSpawner implements PickupHandler {

    @Getter
    private final SnapshotArray<PickupItems> pickups;
    private final Stage mainStage;
    public final int MAX_TOTAL_PICKUPS = 10;
    public final int MAX_FOOD = 6;
    public final int MAX_POISON = 4;
    public final int MAX_DRINK = 4;
    public final int MAX_ICE = 3;
    public final int MAX_WEB = 3;
    public final int MAX_GHOST = 2;
    public static int numFood = 0;
    public static int numPoison = 0;
    public static int numEnergyDrink = 0;
    public static int numSpiderWeb = 0;
    public static int numIceBlock = 0;
    public static int numGhost = 0;
    public int currentPickupsOnScreen;
    private final float foodSpawnRate = 0.0002f;
    private final float poisonSpawnRate = 0.0001f;
    private final float energyDrinkSpawnRate = 0.0001f;
    private final float spiderWebSpawnRate = 0.0001f;
    private final float iceBlockSpawnRate = 0.0001f;
    private final float ghostSpawnRate = 0.0001f;

    public PickupSpawner(Stage mainStage) {
        this.mainStage = mainStage;
        pickups = new SnapshotArray<>();
    }

    @Override
    public void spawnPickups() {

        final float height = Gdx.graphics.getHeight();
        final float width = Gdx.graphics.getWidth();

        do {
            if (numFood < MAX_FOOD && MathUtils.randomBoolean(foodSpawnRate)) {
                pickups.add(new Food(MathUtils.random(0, width), MathUtils.random(0, height), mainStage));
                currentPickupsOnScreen++;
                numFood++;
            }
            if (numPoison < MAX_POISON && MathUtils.randomBoolean(poisonSpawnRate)) {
                pickups.add(new Poison(MathUtils.random(0, width), MathUtils.random(0, height), mainStage));
                numPoison++;
                currentPickupsOnScreen++;
            }
            if (numEnergyDrink < MAX_DRINK && MathUtils.randomBoolean(energyDrinkSpawnRate)) {
                pickups.add(new EnergyDrink(MathUtils.random(0, width), MathUtils.random(0, height), mainStage));
                numEnergyDrink++;
                currentPickupsOnScreen++;
            }
            if (numSpiderWeb < MAX_WEB && MathUtils.randomBoolean(spiderWebSpawnRate)) {
                pickups.add(new SpiderWeb(MathUtils.random(0, width), MathUtils.random(0, height), mainStage));
                numSpiderWeb++;
                currentPickupsOnScreen++;
            }
            if (numIceBlock < MAX_ICE && MathUtils.randomBoolean(iceBlockSpawnRate)) {
                pickups.add(new IceBlock(MathUtils.random(0, width), MathUtils.random(0, height), mainStage));
                numIceBlock++;
                currentPickupsOnScreen++;
            }
            if (numGhost < MAX_GHOST && MathUtils.randomBoolean(ghostSpawnRate)) {
                pickups.add(new Ghost(MathUtils.random(0, width), MathUtils.random(0, height), mainStage));
                numGhost++;
                currentPickupsOnScreen++;
            }
        } while (currentPickupsOnScreen <= MAX_TOTAL_PICKUPS);
    }
       /* }
    }, 0, 1f / 60); // Spawn pickups at 60 frames per second*/


    // Should spawn pickups in the game world randomly
    // Spawn rate will be different for each pickup


    // will try this

        /*// define an ArrayList to store the locations of all existing pickup items
        ArrayList<Vector2> pickupItemLocations = new ArrayList<Vector2>();

// generate a new pickup item
        public void spawnPickupItem() {
            Vector2 newLocation;
            boolean locationOccupied;

            // repeat until an unoccupied location is found
            do {
                // generate a random location for the new pickup item
                newLocation = new Vector2(MathUtils.random(0, screenWidth - pickupItemSize),
                        MathUtils.random(0, screenHeight - pickupItemSize));
                locationOccupied = false;

                // check if the generated location is already occupied by an existing pickup item
                for (Vector2 existingLocation : pickupItemLocations) {
                    if (existingLocation.equals(newLocation)) {
                        locationOccupied = true;
                        break;
                    }
                }
            } while (locationOccupied);

            // create and spawn the new pickup item at the unoccupied location
            PickupItem newPickupItem = new PickupItem(newLocation);
            pickupItemLocations.add(newLocation);
        }*/

}
