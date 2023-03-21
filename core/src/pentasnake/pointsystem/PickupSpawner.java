package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.Timer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PickupSpawner implements PickupHandler {

    @Getter
    private final SnapshotArray<PickupItems> pickups;
    private final Stage mainStage;
    private final float foodSpawnRate = 0.0008f;
    private final float poisonSpawnRate = 0.0008f;
    private final float energyDrinkSpawnRate = 0.0008f;
    private final float spiderWebSpawnRate = 0.0008f;
    private final float iceBlockSpawnRate = 0.0008f;
    private final float ghostSpawnRate = 0.0008f;

    public PickupSpawner(Stage mainStage){
        this.mainStage = mainStage;
        pickups = new SnapshotArray<>();
    }

    @Getter
    public static final Type[] pickupTypes = {
            Type.FOOD,
            Type.POISON,
            Type.DRINK,
            Type.WEB,
            Type.ICE,
            Type.GHOST
    };

    @Override
    public void spawnPickups() {

        // count the number of pickups currently on the stage
        int numPickups = 0;

        // check if the number of pickups is below the threshold
        final int MAX_PICKUPS = 10;
        if (numPickups < MAX_PICKUPS) {
            // spawn a new pickup with a random type and position
        }
        Timer.schedule(new Timer.Task() {
            final float height = Gdx.graphics.getHeight();
            final float width = Gdx.graphics.getWidth();
            @Override
            public void run() {
                /*if (MathUtils.randomBoolean(foodSpawnRate)) {
                    pickups.add(new Food(MathUtils.random(0, width), MathUtils.random(0, height), mainStage));
                }*/
                if (MathUtils.randomBoolean(poisonSpawnRate)) {
                    pickups.add(new Poison(MathUtils.random(0, width), MathUtils.random(0, height), mainStage));
                }
                if (MathUtils.randomBoolean(energyDrinkSpawnRate)) {
                    pickups.add(new EnergyDrink(MathUtils.random(0, width), MathUtils.random(0, height), mainStage));
                }
                if (MathUtils.randomBoolean(spiderWebSpawnRate)) {
                    pickups.add(new SpiderWeb(MathUtils.random(0, width), MathUtils.random(0, height), mainStage));
                }
                if (MathUtils.randomBoolean(iceBlockSpawnRate)) {
                    pickups.add(new IceBlock(MathUtils.random(0, width), MathUtils.random(0, height), mainStage));
                }
                if (MathUtils.randomBoolean(iceBlockSpawnRate)) {
                    pickups.add(new IceBlock(MathUtils.random(0, width), MathUtils.random(0, height), mainStage));
                }
                if (MathUtils.randomBoolean(ghostSpawnRate)) {
                    pickups.add(new Ghost(MathUtils.random(0, width), MathUtils.random(0, height), mainStage));
                }
            }
        }, 0, 1f/60); // Spawn pickups at 60 frames per second

        // Should spawn pickups in the game world randomly
        // Spawn rate will be different for each pickup
    }
}
