package pentasnake.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class WallParts extends Rectangle {

    private Color wallColor;
    public WallParts() {
    }
    public WallParts(float x, float y, float width, float height, Color color){
        super(x,y, width, height);
        this.wallColor = color;
    }

    public Color getWallColor(){
        return this.wallColor;
    }
}
