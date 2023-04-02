package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import pentasnake.client.entities.WallPart;
import pentasnake.client.entities.WallPattern;
import pentasnake.client.messages.Pickup;
import pentasnake.client.screen.MySnapshotArray;

public class PickupSpawner implements PickupHandler {
    private final MySnapshotArray pickups;
    private static final SnapshotArray<Pickup> items = new SnapshotArray<>();
    private SnapshotArray<WallPattern> wallList;
    private final Stage mainStage;
    public final int MAX_TOTAL_PICKUPS = 10;
    public static int currentPickupsOnScreen;
    public static int foodCount = 0;
    public static int poisonCount = 0;
    public static int drinkCount = 0;
    public static int webCount = 0;
    public static int iceCount = 0;
    public static int ghostCount = 0;

    float padding = 60f;
    private static final float INITIAL_SPAWN_DELAY = 3f; // first spawn in 3 seconds after game starts
    private static final float SPAWN_INTERVAL = 10f; // 10 seconds between pickups spawn
    private static final int MAX_SPAWN_PER_INTERVAL = 1; // 1 pickup at a time
    private float spawnDelay = INITIAL_SPAWN_DELAY;
    private float timeSinceLastSpawn = 0f;
    private int numPickupsToSpawn = 1;

    public PickupSpawner(Stage mainStage, SnapshotArray<WallPattern> wallList) {
        this.mainStage = mainStage;
        this.wallList = wallList;
        pickups = new MySnapshotArray();
    }

    @Override
    public void getNewPickup() {

        final float HEIGHT = Gdx.graphics.getHeight();
        final float WIDTH = Gdx.graphics.getWidth();
        float x, y;

        if (timeSinceLastSpawn >= spawnDelay && currentPickupsOnScreen < MAX_TOTAL_PICKUPS) {
            for (int i = 0; i < numPickupsToSpawn; i++) {
                x = MathUtils.random(padding, WIDTH - padding);
                y = MathUtils.random(padding, HEIGHT - padding);
                Pickup item = PickupFactory.createRandomPickup(x, y, mainStage, pickups);
                if (item != null) {
                    items.add(item);
                    currentPickupsOnScreen++;
                }
                if (currentPickupsOnScreen >= MAX_TOTAL_PICKUPS) {
                    break;
                }
                timeSinceLastSpawn = 0f;
                numPickupsToSpawn = Math.min(numPickupsToSpawn + 1, MAX_SPAWN_PER_INTERVAL);
                spawnDelay = SPAWN_INTERVAL;


            }
            timeSinceLastSpawn = 0f;
            numPickupsToSpawn = Math.min(numPickupsToSpawn + 1, MAX_SPAWN_PER_INTERVAL);
            spawnDelay = SPAWN_INTERVAL;
        }
    }

    public SnapshotArray<PickupItems> getPickups() {
        return pickups;
    }

    @Override
    public void pickupCollected(PickupItems item) {
        pickups.removeValue(item, true);
        currentPickupsOnScreen--;
    }

    public boolean isOverlapping(float x, float y) {
        // checking pickup overlap
        float radius = 50f;
        for (PickupItems pickup : pickups) {
            float distanceSquared = (x - pickup.getX()) * (x - pickup.getX()) + (y - pickup.getY()) * (y - pickup.getY());
            if (distanceSquared < (radius + pickup.getRadius()) * (radius + pickup.getRadius())) {
                return true;
            }
        }
        // checking wall and pickup overlap
        for (WallPattern wallPattern : wallList) {
            for (WallPart wallPart : wallPattern.getParts()) {
                if (wallPart.getBoundaryRectangle().contains(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static class PickupFactory {
        public static final int MAX_TOTAL_PICKUPS = 10;

        public static Pickup createRandomPickup(float x, float y, Stage mainStage, MySnapshotArray pickups) {
            if (currentPickupsOnScreen >= MAX_TOTAL_PICKUPS) {
                return null;
            }
            Type type = Type.getRandomType();
            switch (type) {
                case FOOD:
                    if (foodCount > Type.FOOD.getMaxAmount() || !MathUtils.randomBoolean(Type.FOOD.getSpawnRate())) {
                        return null;
                    }
                    break;
                case POISON:
                    if (poisonCount > Type.POISON.getMaxAmount() || !MathUtils.randomBoolean(Type.POISON.getSpawnRate())) {
                        return null;
                    }
                    break;
                case DRINK:
                    if (drinkCount > Type.DRINK.getMaxAmount() || !MathUtils.randomBoolean(Type.DRINK.getSpawnRate())) {
                        return null;
                    }
                    break;
                case WEB:
                    if (webCount > Type.WEB.getMaxAmount() || !MathUtils.randomBoolean(Type.WEB.getSpawnRate())) {
                        return null;
                    }
                    break;
                case ICE:
                    if (iceCount > Type.ICE.getMaxAmount() || !MathUtils.randomBoolean(Type.ICE.getSpawnRate())) {
                        return null;
                    }
                    break;
                case GHOST:
                    if (ghostCount > Type.GHOST.getMaxAmount() || !MathUtils.randomBoolean(Type.GHOST.getSpawnRate())) {
                        return null;
                    }
                    break;
                default:
                    return null;
            }
            Vector2 position = new Vector2(x, y);
            Pickup pickup = new Pickup(type, 1, position);
            items.add(pickup);
            return pickup;
        }
    }
}

