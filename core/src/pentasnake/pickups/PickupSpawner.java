package pentasnake.pickups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
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
    float padding = 60f;

    private static int pickupId = 0;
    private static int nextPickupId = pickupId++;

    public PickupSpawner(Stage mainStage, SnapshotArray<WallPattern> wallList) {
        this.mainStage = mainStage;
        this.wallList = wallList;
        pickups = new MySnapshotArray();
    }

    @Override
    public Pickup getNewPickup() {

        final float HEIGHT = Gdx.graphics.getHeight();
        final float WIDTH = Gdx.graphics.getWidth();
        float x,y;

            x = MathUtils.random(padding, WIDTH - padding);
            y = MathUtils.random(padding, HEIGHT - padding);

        return PickupFactory.createRandomPickup(x, y, mainStage, pickups);
    }

    public SnapshotArray<Pickup> getPickups() {
        return items;
    }

    /*public boolean isOverlapping(float x, float y) {
        // checking pickup overlap
        // checking pickup overlap
        float radius = 50f;
        for (Pickup pickup : pickupList) {
            float distanceSquared = (x - pickup.getPosition().x) * (x - pickup.getPosition().x)
                    + (y - pickup.getPosition().y) * (y - pickup.getPosition().y);
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
    }*/

    private static class PickupFactory {
        public static Pickup createRandomPickup(float x, float y, Stage mainStage, MySnapshotArray pickups) {
            Type type = Type.getRandomType();
            Vector2 position = new Vector2(x, y);
            Pickup pickup = new Pickup(type, nextPickupId, position);
            return pickup;
        }
    }
}

