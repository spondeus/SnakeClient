package pentasnake.pickups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
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
   // private static final float SPAWN_INTERVAL = 10f; // 10 seconds between pickups spawn
    private static final int MAX_SPAWN_PER_INTERVAL = 1; // 1 pickup at a time
    private float spawnDelay = INITIAL_SPAWN_DELAY;

    private static int pickupId = 0;
    private static int nextPickupId = pickupId++;

    public PickupSpawner(Stage mainStage, SnapshotArray<WallPattern> wallList) {
        this.mainStage = mainStage;
        this.wallList = wallList;
        pickups = new MySnapshotArray();
    }

    @Override
    public Pickup getNewPickup(SnapshotArray<Pickup> pickupList) {

        final float HEIGHT = Gdx.graphics.getHeight();
        final float WIDTH = Gdx.graphics.getWidth();
        float x,y;

        do {
            x = MathUtils.random(padding, WIDTH - padding);
            y = MathUtils.random(padding, HEIGHT - padding);

        } while (isOverlapping(x, y, pickupList));


        return PickupFactory.createRandomPickup(x, y, mainStage, pickups);
    }

    public SnapshotArray<Pickup> getPickups() {
        return items;
    }

    @Override
    public void pickupCollected(PickupItems item) {
        pickups.removeValue(item, true);
        currentPickupsOnScreen--;
    }

    public boolean isOverlapping(float x, float y, SnapshotArray<Pickup> pickupList) {
        // checking pickup overlap
        float radius = 50f;
        for (Pickup pickup : pickupList) {
            Rectangle rectangle = new Rectangle(pickup.getPosition().x, pickup.getPosition().y, pickup.getHeight(), pickup.getWidth());

            if (distanceSquared < (radius + 30f) * (radius + 30f)) {
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
        public static Pickup createRandomPickup(float x, float y, Stage mainStage, MySnapshotArray pickups) {
            Type type = Type.getRandomType();
            Vector2 position = new Vector2(x, y);
            return new Pickup(type, nextPickupId, position);
        }
    }
}

