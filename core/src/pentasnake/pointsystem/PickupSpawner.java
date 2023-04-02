package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import pentasnake.client.entities.WallPart;
import pentasnake.client.entities.WallPattern;
import pentasnake.client.screen.MySnapshotArray;

public class PickupSpawner implements PickupHandler {


    private final MySnapshotArray pickups;
    private SnapshotArray<WallPattern> wallList;
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
    private static final float INITIAL_SPAWN_DELAY = 3f; // first spawn in 3 seconds after game starts
    private static final float SPAWN_INTERVAL = 10f; // 10 seconds between pickups spawn
    private static final int MAX_SPAWN_PER_INTERVAL = 2; // 1 pickup at a time
    private float spawnDelay = INITIAL_SPAWN_DELAY;
    private float timeSinceLastSpawn = 0f;
    private int numPickupsToSpawn = 1;

    public PickupSpawner(Stage mainStage, SnapshotArray<WallPattern> wallList) {
        this.mainStage = mainStage;
        this.wallList = wallList;
        pickups = new MySnapshotArray();
    }

    @Override
    public void spawnPickups(float delta) {

        final float HEIGHT = Gdx.graphics.getHeight();
        final float WIDTH = Gdx.graphics.getWidth();
        float x, y;

        timeSinceLastSpawn += delta;

        if (timeSinceLastSpawn >= spawnDelay && currentPickupsOnScreen < MAX_TOTAL_PICKUPS) {
            for (int i = 0; i < numPickupsToSpawn; i++) {
                if (Food.getCount() < Type.FOOD.getMaxAmount() && MathUtils.randomBoolean(Type.FOOD.getSpawnRate())) {
                    do {
                        x = MathUtils.random(padding, WIDTH - padding);
                        y = MathUtils.random(padding, HEIGHT - padding);
                    } while (isOverlapping(x, y));
                    pickups.add(new Food(x, y, mainStage, 0));
                    numFood++;
                    currentPickupsOnScreen++;
                } else if (Poison.getCount() < Type.POISON.getMaxAmount() && MathUtils.randomBoolean(Type.POISON.getSpawnRate())) {
                    do {
                        x = MathUtils.random(padding, WIDTH - padding);
                        y = MathUtils.random(padding, HEIGHT - padding);
                    } while (isOverlapping(x, y));
                    pickups.add(new Poison(x, y, mainStage));
                    numPoison++;
                    currentPickupsOnScreen++;
                } else if (EnergyDrink.getCount() < Type.DRINK.getMaxAmount() && MathUtils.randomBoolean(Type.DRINK.getSpawnRate())) {
                    do {
                        x = MathUtils.random(padding, WIDTH - padding);
                        y = MathUtils.random(padding, HEIGHT - padding);
                    } while (isOverlapping(x, y));
                    pickups.add(new EnergyDrink(x, y, mainStage));
                    numEnergyDrink++;
                    currentPickupsOnScreen++;
                } else if (SpiderWeb.getCount() < Type.WEB.getMaxAmount() && MathUtils.randomBoolean(Type.WEB.getSpawnRate())) {
                    do {
                        x = MathUtils.random(padding, WIDTH - padding);
                        y = MathUtils.random(padding, HEIGHT - padding);
                    } while (isOverlapping(x, y));
                    pickups.add(new SpiderWeb(x, y, mainStage));
                    numSpiderWeb++;
                    currentPickupsOnScreen++;
                } else if (IceBlock.getCount() < Type.ICE.getMaxAmount() && MathUtils.randomBoolean(Type.ICE.getSpawnRate())) {
                    do {
                        x = MathUtils.random(padding, WIDTH - padding);
                        y = MathUtils.random(padding, HEIGHT - padding);
                    } while (isOverlapping(x, y));
                    pickups.add(new IceBlock(x, y, mainStage, 0));
                    numIceBlock++;
                    currentPickupsOnScreen++;
                } else if (Ghost.getCount() < Type.GHOST.getMaxAmount() && MathUtils.randomBoolean(Type.GHOST.getSpawnRate())) {
                    do {
                        x = MathUtils.random(padding, WIDTH - padding);
                        y = MathUtils.random(padding, HEIGHT - padding);
                    } while (isOverlapping(x, y));
                    pickups.add(new Ghost(x, y, mainStage, 0));
                    numGhost++;
                    currentPickupsOnScreen++;
                } else if (currentPickupsOnScreen >= MAX_TOTAL_PICKUPS){
                    break;
                }
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
        if (item instanceof Food) {
            numFood--;
        } else if (item instanceof Poison) {
            numPoison--;
        } else if (item instanceof EnergyDrink) {
            numEnergyDrink--;
        } else if (item instanceof SpiderWeb) {
            numSpiderWeb--;
        } else if (item instanceof IceBlock) {
            numIceBlock--;
        } else if (item instanceof Ghost) {
            numGhost--;
        }
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
}