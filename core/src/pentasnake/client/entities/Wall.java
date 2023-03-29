package pentasnake.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Wall extends Actor {
    SnapshotArray<WallParts> parts;
    public SnapshotArray<WallParts> getParts() {
        return parts;
    }
    private final ShapeRenderer sr = new ShapeRenderer();

    private final WallPattern pattern = new WallPattern(getParts());

    public Wall(SnapshotArray<WallPattern> patterns){

    }

    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        sr.setAutoShapeType(true);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (WallParts part : parts) {
            sr.setColor(part.getWallColor());
            sr.rect(part.getX(), part.getY(), part.getWidth(), part.getHeight());
        }
        sr.end();
        batch.begin();
    }

    public SnapshotArray<WallPattern> spawnWalls(){

        final int MAX_WALLS_TO_SPAWN = 4;

        SnapshotArray<WallPattern> wallPatterns = pattern.createWallPatterns();
        SnapshotArray<WallPattern> randomlySelectedWalls = new SnapshotArray<>();

        Random random = new Random();
        Collections.shuffle((List<?>) wallPatterns, random);
        for (int i = 0; i < MAX_WALLS_TO_SPAWN; i++) {
            randomlySelectedWalls.add(wallPatterns.get(i));
        }
        return randomlySelectedWalls;
    }

}
