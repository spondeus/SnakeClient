package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import pentasnake.client.entities.Wall;
import pentasnake.client.entities.WallPart;
import pentasnake.client.entities.WallPattern;
import pentasnake.client.screen.MySnapshotArray;
import pentasnake.client.screen.PlayScreen;

import java.util.Random;

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

    Random random=new Random();

    public PickupSpawner(Stage mainStage, SnapshotArray<WallPattern> wallList) {
        this.mainStage = mainStage;
        this.wallList = wallList;
        pickups = new MySnapshotArray();
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
                pickups.add(new Food(x, y, mainStage,0));
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
                pickups.add(new IceBlock(x, y, mainStage,0));
                numIceBlock++;
                currentPickupsOnScreen++;
            }
            if (Ghost.getCount() < Type.GHOST.getMaxAmount() && MathUtils.randomBoolean(Type.GHOST.getSpawnRate())) {
                do {
                    x = MathUtils.random(padding, WIDTH - padding);
                    y = MathUtils.random(padding, HEIGHT - padding);
                } while (isOverlapping(x, y));
                pickups.add(new Ghost(x, y, mainStage,0));
                numGhost++;
                currentPickupsOnScreen++;
            }
        }
    }

    public SnapshotArray<PickupItems> getPickups(){
        return pickups;
    }

    /* }
    }, 0, 1f / 60); // Spawn pickups at 60 frames per second*/


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
                float distanceSquared = (x - wallPart.getX()) * (x - wallPart.getX()) + (y - wallPart.getY()) * (y - wallPart.getY());
                if (distanceSquared < (radius + wallPart.getRadius()) * (radius + wallPart.getRadius())) {
                    return true;
                }
            }
        }
        return false;
    }
}