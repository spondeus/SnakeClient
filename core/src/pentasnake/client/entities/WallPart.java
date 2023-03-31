package pentasnake.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class WallPart extends Rectangle {

    private Color wallColor;

    private Rectangle boundaryRectangle;

    public Rectangle getBoundaryRectangle() {
        return boundaryRectangle;
    }
    public void setBoundaryRectangle(float x, float y, float width, float height) {
        this.boundaryRectangle = new Rectangle(x,y,width,height);
    }

    public WallPart() {
    }
    public WallPart(float x, float y, float width, float height, Color color){
        super(x,y, width, height);
        this.wallColor = color;
        this.boundaryRectangle = new Rectangle(x, y, width, height);
    }

    public Color getWallColor(){
        return this.wallColor;
    }
}
