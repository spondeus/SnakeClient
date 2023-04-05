package pentasnake.client.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static pentasnake.client.entities.WallPattern.createWallPatterns;

public class Wall extends Actor {
    SnapshotArray<WallPattern> patterns;

    public SnapshotArray<WallPattern> getParts() {
        return patterns;
    }

    private final ShapeRenderer sr = new ShapeRenderer();

    public Wall(SnapshotArray<WallPattern> patterns, int w, int h) {
        this.patterns = convertToRel(patterns, w, h);
//        this.patterns = patterns;
    }

    private SnapshotArray<WallPattern> convertToRel(SnapshotArray<WallPattern> patterns, int w, int h) {
        SnapshotArray<WallPattern> newList = new SnapshotArray<>();
        for (WallPattern pattern : patterns) {
            SnapshotArray newPattern = new SnapshotArray<WallPart>();
            for (WallPart wallPart : pattern.getParts()) {
                newPattern.add(new WallPart(wallPart.x / 1200 * w, wallPart.y / 800 * h, wallPart.width / 1200 * w, wallPart.height / 800 * h, wallPart.getWallColor()));
            }
            WallPattern newWallPattern = new WallPattern(newPattern);
            newList.add(newWallPattern);
        }
        return newList;
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        sr.setAutoShapeType(true);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (WallPattern pattern : patterns) {
            for (WallPart part : pattern.getParts()) {
                sr.setColor(part.getWallColor());
                sr.rect(part.getX(), part.getY(), part.getWidth(), part.getHeight());
            }
        }
        sr.end();
        batch.begin();
    }

    public static SnapshotArray<WallPattern> spawnWalls() {

        final int MAX_WALLS_TO_SPAWN = 4;
        final int NUM_WALL_PATTERNS = 8;

        SnapshotArray<WallPattern> wallPatterns = createWallPatterns();
        SnapshotArray<WallPattern> randomlySelectedWalls = new SnapshotArray<>();

        List<Integer> selectedIndices = new ArrayList<>();
        Random random = new Random();
        while (selectedIndices.size() < MAX_WALLS_TO_SPAWN) {
            int i = random.nextInt(NUM_WALL_PATTERNS);
            if (!selectedIndices.contains(i)) {
                selectedIndices.add(i);
            }
        }
        randomlySelectedWalls.add(wallPatterns.get(selectedIndices.get(0)));
        randomlySelectedWalls.add(wallPatterns.get(selectedIndices.get(1)));
        randomlySelectedWalls.add(wallPatterns.get(selectedIndices.get(2)));
        randomlySelectedWalls.add(wallPatterns.get(selectedIndices.get(3)));

        return randomlySelectedWalls;
    }

}
