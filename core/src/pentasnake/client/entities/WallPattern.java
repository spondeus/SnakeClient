package pentasnake.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.SnapshotArray;

public class WallPattern {

    public SnapshotArray<WallPart> getParts() {
        return parts;
    }
    private final SnapshotArray<WallPart> parts;

    public WallPattern(SnapshotArray<WallPart> parts) {
        this.parts = parts;
    }
    public static SnapshotArray<WallPattern> createWallPatterns() {

        SnapshotArray<WallPattern> wallPatterns = new SnapshotArray<>();

        SnapshotArray<WallPart> bottomLeftCorner = new SnapshotArray<>();
        WallPart bottomLeft1 = new WallPart(100, 100, 200, 50, Color.FIREBRICK);
        bottomLeft1.setBoundaryRectangle(100, 100, 200, 50);
        WallPart bottomLeft2 = new WallPart(100, 150, 50, 150, Color.FIREBRICK);
        bottomLeft2.setBoundaryRectangle(100, 150, 50, 150);
        bottomLeftCorner.add(bottomLeft1);
        bottomLeftCorner.add(bottomLeft2);
        wallPatterns.add(new WallPattern(bottomLeftCorner));

        SnapshotArray<WallPart> bottomRightCorner = new SnapshotArray<>();
        WallPart bottomRight1 = new WallPart(900, 100, 200, 50, Color.FIREBRICK);
        bottomRight1.setBoundaryRectangle(900, 100, 200, 50);
        WallPart bottomRight2 = new WallPart(1050, 150, 50, 150, Color.FIREBRICK);
        bottomRight2.setBoundaryRectangle(1050, 150, 50, 150);
        bottomRightCorner.add(bottomRight1);
        bottomRightCorner.add(bottomRight2);
        wallPatterns.add(new WallPattern(bottomRightCorner));

        SnapshotArray<WallPart> upperRightCorner = new SnapshotArray<>();
        WallPart upperRight1 = new WallPart(900, 650, 200, 50, Color.FIREBRICK);
        upperRight1.setBoundaryRectangle(900, 650, 200, 50);
        WallPart upperRight2 = new WallPart(1050, 500, 50, 150, Color.FIREBRICK);
        upperRight2.setBoundaryRectangle(1050, 500, 50, 150);
        upperRightCorner.add(upperRight1);
        upperRightCorner.add(upperRight2);
        wallPatterns.add(new WallPattern(upperRightCorner));

        SnapshotArray<WallPart> upperLeftCorner = new SnapshotArray<>();
        WallPart upperLeft1 = new WallPart(100, 650, 200, 50, Color.FIREBRICK);
        upperLeft1.setBoundaryRectangle(100, 650, 200, 50);
        WallPart upperLeft2 = new WallPart(100, 500, 50, 150, Color.FIREBRICK);
        upperLeft2.setBoundaryRectangle(100, 500, 50, 150);
        upperLeftCorner.add(upperLeft1);
        upperLeftCorner.add(upperLeft2);
        wallPatterns.add(new WallPattern(upperLeftCorner));

        SnapshotArray<WallPart> straightLong = new SnapshotArray<>();
        WallPart straight = new WallPart(450, 500, 350, 50, Color.FIREBRICK);
        straight.setBoundaryRectangle(450, 500, 350, 50);
        straightLong.add(straight);
        wallPatterns.add(new WallPattern(straightLong));

        /*SnapshotArray<WallPart> crossShaped = new SnapshotArray<>();
        WallPart crossVertical = new WallPart(550, 630, 50, 150, Color.FIREBRICK);
        WallPart crossHorizontal = new WallPart(500, 650, 150, 50, Color.FIREBRICK);
        crossShaped.add(crossVertical);
        crossShaped.add(crossHorizontal);
        wallPatterns.add(new WallPattern(crossShaped));*/

        SnapshotArray<WallPart> uShaped = new SnapshotArray<>();
        WallPart uLeft = new WallPart(500, 700, 50, 30, Color.FIREBRICK);
        uLeft.setBoundaryRectangle(500, 700, 50, 30);
        WallPart uMiddle = new WallPart(500, 650, 250, 50, Color.FIREBRICK);
        uMiddle.setBoundaryRectangle(500, 650, 250, 50);
        WallPart uRight = new WallPart(700, 700, 50, 30, Color.FIREBRICK);
        uRight.setBoundaryRectangle(700, 700, 50, 30);
        uShaped.add(uLeft);
        uShaped.add(uMiddle);
        uShaped.add(uRight);
        wallPatterns.add(new WallPattern(uShaped));

        SnapshotArray<WallPart> diagonalPieces = new SnapshotArray<>();
        WallPart diagonal1 = new WallPart(220, 450, 50, 50, Color.FIREBRICK);
        diagonal1.setBoundaryRectangle(220, 450, 50, 50);
        WallPart diagonal2 = new WallPart(270, 400, 50, 50, Color.FIREBRICK);
        diagonal2.setBoundaryRectangle(270, 400, 50, 50);
        WallPart diagonal3 = new WallPart(320, 350, 50, 50, Color.FIREBRICK);
        diagonal3.setBoundaryRectangle(320, 350, 50, 50);
        WallPart diagonal4 = new WallPart(370, 300, 50, 50, Color.FIREBRICK);
        diagonal4.setBoundaryRectangle(370, 300, 50, 50);
        diagonalPieces.add(diagonal1);
        diagonalPieces.add(diagonal2);
        diagonalPieces.add(diagonal3);
        diagonalPieces.add(diagonal4);
        wallPatterns.add(new WallPattern(diagonalPieces));

        SnapshotArray<WallPart> landingPad = new SnapshotArray<>();
        WallPart landingPad1 = new WallPart(600, 300, 150, 50, Color.FIREBRICK);
        landingPad1.setBoundaryRectangle(600, 300, 150, 50);
        WallPart landingPad2 = new WallPart(650, 150, 50, 200, Color.FIREBRICK);
        landingPad2.setBoundaryRectangle(650, 150, 50, 200);
        WallPart landingPad3 = new WallPart(600, 100, 150, 50, Color.FIREBRICK);
        landingPad3.setBoundaryRectangle(600, 100, 150, 50);
        landingPad.add(landingPad1);
        landingPad.add(landingPad2);
        landingPad.add(landingPad3);
        wallPatterns.add(new WallPattern(landingPad));

        return wallPatterns;
    }
}