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
    public static int numFood = 0;
    public static int numPoison = 0;
    public static int numEnergyDrink = 0;
    public static int numSpiderWeb = 0;
    public static int numIceBlock = 0;
    public static int numGhost = 0;
    public int currentPickupsOnScreen;
    float padding = 60f;

    public PickupSpawner(Stage mainStage) {
        this.mainStage = mainStage;
        pickups = new SnapshotArray<>();
    }

    @Override
    public void spawnPickups() {

        final float HEIGHT = Gdx.graphics.getHeight();
        final float WIDTH = Gdx.graphics.getWidth();
        float x,y;


        while (currentPickupsOnScreen <= MAX_TOTAL_PICKUPS) {
            if (Food.getCount() < Type.FOOD.getMaxAmount() && MathUtils.randomBoolean(Type.FOOD.getSpawnRate())) {
                do {
                    x = MathUtils.random(padding, WIDTH - padding);
                    y = MathUtils.random(padding, HEIGHT - padding);
                } while (isOverlapping(x, y));
                pickups.add(new Food(x, y, mainStage));
                numFood++;
                currentPickupsOnScreen++;
            }
            if (Poison.getCount() < Type.POISON.getMaxAmount() && MathUtils.randomBoolean(Type.POISON.getSpawnRate())) {
                do {
                    x = MathUtils.random(padding, WIDTH - padding);
                    y = MathUtils.random(padding, HEIGHT - padding);
                } while (isOverlapping(x, y));
                pickups.add(new Poison(x, y, mainStage));
                numPoison++;
                currentPickupsOnScreen++;
            }
            if (EnergyDrink.getCount() < Type.DRINK.getMaxAmount() && MathUtils.randomBoolean(Type.DRINK.getSpawnRate())) {
                do {
                    x = MathUtils.random(padding, WIDTH - padding);
                    y = MathUtils.random(padding, HEIGHT - padding);
                } while (isOverlapping(x, y));
                pickups.add(new EnergyDrink(x, y, mainStage));
                numEnergyDrink++;
                currentPickupsOnScreen++;
            }
            if (SpiderWeb.getCount() < Type.WEB.getMaxAmount() && MathUtils.randomBoolean(Type.WEB.getSpawnRate())) {
                do {
                    x = MathUtils.random(padding, WIDTH - padding);
                    y = MathUtils.random(padding, HEIGHT - padding);
                } while (isOverlapping(x, y));
                pickups.add(new SpiderWeb(x, y, mainStage));
                numSpiderWeb++;
                currentPickupsOnScreen++;
            }
            if (IceBlock.getCount() < Type.ICE.getMaxAmount() && MathUtils.randomBoolean(Type.ICE.getSpawnRate())) {
                do {
                    x = MathUtils.random(padding, WIDTH - padding);
                    y = MathUtils.random(padding, HEIGHT - padding);
                } while (isOverlapping(x, y));
                pickups.add(new IceBlock(x, y, mainStage));
                numIceBlock++;
                currentPickupsOnScreen++;
            }
            if (Ghost.getCount() < Type.GHOST.getMaxAmount() && MathUtils.randomBoolean(Type.GHOST.getSpawnRate())) {
                do {
                    x = MathUtils.random(padding, WIDTH - padding);
                    y = MathUtils.random(padding, HEIGHT - padding);
                } while (isOverlapping(x, y));
                pickups.add(new Ghost(x, y, mainStage));
                numGhost++;
                currentPickupsOnScreen++;
            }
        }
    }

    public boolean isOverlapping(float x, float y) {
        float radius = 50f;
        for (PickupItems pickup : pickups) {
            float distanceSquared = (x - pickup.getX()) * (x - pickup.getX()) + (y - pickup.getY()) * (y - pickup.getY());
            if (distanceSquared < (radius + pickup.getRadius()) * (radius + pickup.getRadius())) {
                return true;
            }
        }
        return false;
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
