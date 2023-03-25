package pentasnake.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class WallParts extends Rectangle {

    private final Rectangle wallPiece = new Rectangle();

    private final Color wallColor = Color.FIREBRICK;

    public final int WALL_HEIGHT = 100;

    public final int WALL_WIDTH = 100;

    public WallParts(float x, float y){
    }

    public WallParts() {
    }
}
