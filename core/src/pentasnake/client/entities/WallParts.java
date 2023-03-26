package pentasnake.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class WallParts extends Rectangle {

    private Color wallColor;

    protected Rectangle boundaryRectangle;

    public Rectangle getBoundaryRectangle() {
        return boundaryRectangle;
    }

    public WallParts(float x, float y, float width, float height, Color color){
        super(x,y, width, height);
        this.wallColor = color;
        boundaryRectangle = new Rectangle(getX(), getY(), width, height);
    }

    public WallParts() {
    }

    public Color getWallColor(){
        return this.wallColor;
    }

    public void setWallColor(Color color){
        this.wallColor = color;
    }
}
