package pentasnake.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.SnapshotArray;

public class WallPattern {

    private SnapshotArray<WallParts> parts;

    public WallPattern(SnapshotArray<WallParts> parts) {
        this.parts = parts;
    }

    public SnapshotArray<WallPattern> createWallPatterns() {

        SnapshotArray<WallPattern> wallPatterns = new SnapshotArray<>();

        SnapshotArray<WallParts> bottomLeftCorner = new SnapshotArray<>();
        WallParts bottomLeft1 = new WallParts(100, 100, 200, 50, Color.FIREBRICK);
        WallParts bottomLeft2 = new WallParts(100, 150, 50, 150, Color.FIREBRICK);
        bottomLeftCorner.add(bottomLeft1);
        bottomLeftCorner.add(bottomLeft2);
        wallPatterns.add(new WallPattern(bottomLeftCorner));

        SnapshotArray<WallParts> bottomRightCorner = new SnapshotArray<>();
        WallParts bottomRight1 = new WallParts(900, 100, 200, 50, Color.FIREBRICK);
        WallParts bottomRight2 = new WallParts(1050, 150, 50, 150, Color.FIREBRICK);
        bottomRightCorner.add(bottomRight1);
        bottomRightCorner.add(bottomRight2);
        wallPatterns.add(new WallPattern(bottomRightCorner));

        SnapshotArray<WallParts> upperRightCorner = new SnapshotArray<>();
        WallParts upperRight1 = new WallParts(900, 650, 200, 50, Color.FIREBRICK);
        WallParts upperRight2 = new WallParts(1050, 500, 50, 150, Color.FIREBRICK);
        upperRightCorner.add(upperRight1);
        upperRightCorner.add(upperRight2);
        wallPatterns.add(new WallPattern(upperRightCorner));

        SnapshotArray<WallParts> upperLeftCorner = new SnapshotArray<>();
        WallParts upperLeft1 = new WallParts(100, 650, 200, 50, Color.FIREBRICK);
        WallParts upperLeft2 = new WallParts(100, 500, 50, 150, Color.FIREBRICK);
        upperLeftCorner.add(upperLeft1);
        upperLeftCorner.add(upperLeft2);
        wallPatterns.add(new WallPattern(upperLeftCorner));

        SnapshotArray<WallParts> straightLong = new SnapshotArray<>();
        WallParts straight = new WallParts(450, 500, 350, 50, Color.FIREBRICK);
        straightLong.add(straight);
        wallPatterns.add(new WallPattern(straightLong));

        SnapshotArray<WallParts> crossShaped = new SnapshotArray<>();
        WallParts crossVertical = new WallParts(550, 600, 50, 150, Color.FIREBRICK);
        WallParts crossHorizontal = new WallParts(500, 650, 150, 50, Color.FIREBRICK);
        crossShaped.add(crossVertical);
        crossShaped.add(crossHorizontal);
        wallPatterns.add(new WallPattern(crossShaped));

        SnapshotArray<WallParts> diagonalPieces = new SnapshotArray<>();
        WallParts diagonal1 = new WallParts(220, 450, 50, 50, Color.FIREBRICK);
        WallParts diagonal2 = new WallParts(270, 400, 50, 50, Color.FIREBRICK);
        WallParts diagonal3 = new WallParts(320, 350, 50, 50, Color.FIREBRICK);
        WallParts diagonal4 = new WallParts(370, 300, 50, 50, Color.FIREBRICK);
        diagonalPieces.add(diagonal1);
        diagonalPieces.add(diagonal2);
        diagonalPieces.add(diagonal3);
        diagonalPieces.add(diagonal4);
        wallPatterns.add(new WallPattern(diagonalPieces));

        SnapshotArray<WallParts> landingPad = new SnapshotArray<>();
        WallParts landingPad1 = new WallParts(600, 300, 150, 50, Color.FIREBRICK);
        WallParts landingPad2 = new WallParts(650, 150, 50, 200, Color.FIREBRICK);
        WallParts landingPad3 = new WallParts(600, 100, 150, 50, Color.FIREBRICK);
        landingPad.add(landingPad1);
        landingPad.add(landingPad2);
        landingPad.add(landingPad3);
        wallPatterns.add(new WallPattern(landingPad));

        return wallPatterns;
    }
}
