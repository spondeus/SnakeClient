package pentasnake.client.messages;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import pentasnake.pickups.Type;

public class Pickup extends Message{
    private Type type;
    private int pickUpId;
    private Vector2 position;

    private int height = 60;

    private int width = 60;

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    private Rectangle boundaryRectangle;
    public Rectangle getBoundaryRectangle() {
        return boundaryRectangle;
    }
    public void setBoundaryRectangle(){
        this.boundaryRectangle = new Rectangle(getPosition().x, getPosition().y, getWidth(), getHeight());
    }

    public Pickup(Type type, int id, Vector2 position){
        this.type = type;
        this.position = position;
    }
//
//    @Override
//    public String toString(){
//        return String.format(
//                "%s#%s#%d#%d#%d",msg,type,id, (int) position.x(),(int) position.y()
//        );
//    }


    public void setType(Type type) {
        this.type = type;
    }

    public int getPickUpId() {
        return pickUpId;
    }

    public void setPickUpId(int pickUpId) {
        this.pickUpId = pickUpId;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Pickup{" +
                ", type=" + type +
                ", pickupId=" + pickUpId +
                ", position=" + position +
                '}';
    }

    public Type getType() {
        return type;
    }

    public Vector2 getPosition() {
        return position;
    }
}
