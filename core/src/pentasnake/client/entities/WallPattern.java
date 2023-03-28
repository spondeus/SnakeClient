package pentasnake.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.ArrayList;
import java.util.List;

public class WallPattern {

    private SnapshotArray<WallParts> parts;

    public WallPattern(SnapshotArray<WallParts> parts) {
        this.parts = parts;
    }

    public SnapshotArray<Wall> createWalls(){

        SnapshotArray<WallPattern> wallPatterns = new SnapshotArray<>();

        // bottom left corner wall
        SnapshotArray<WallParts> bottomLeftCorner = new SnapshotArray<>();
        WallParts bottomLeft1 = new WallParts(100, 100, 200, 50, Color.FIREBRICK);
        WallParts bottomLeft2 = new WallParts(100, 150, 50, 150, Color.FIREBRICK);
        wallPatterns.add(new WallPattern(bottomLeftCorner));

        // bottom right corner wall
        SnapshotArray<WallParts> bottomRightCorner = new SnapshotArray<>();
        WallParts bottomRight1 = new WallParts(800, 100, 200, 50, Color.FIREBRICK);
        WallParts bottomRight2 = new WallParts(800, 150, 50, 150, Color.FIREBRICK);
        wallPatterns.add(new WallPattern(bottomRightCorner));

        SnapshotArray<Wall> walls = new SnapshotArray<>();
        for (WallParts part : parts) {
            walls.add(new Wall(parts));
        }
        return walls;
    }
}
