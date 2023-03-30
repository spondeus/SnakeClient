package pentasnake.client.messages;

import com.badlogic.gdx.math.Vector2;
import pentasnake.pointsystem.Type;

public class Pickup extends Message{
    private final String msg = "pickup";
    private Type type;

    private int pickupId;

    private Vector2 position;

    public int getPickupId() {
        return pickupId;
    }

    public Pickup(Type type, int id, Vector2 position){
        this.type = type;
        this.pickupId = id;
        this.position = position;
    }
//
//    @Override
//    public String toString(){
//        return String.format(
//                "%s#%s#%d#%d#%d",msg,type,id, (int) position.x(),(int) position.y()
//        );
//    }


    @Override
    public String toString() {
        return "Pickup{" +
                "msg='" + msg + '\'' +
                ", type=" + type +
                ", pickupId=" + pickupId +
                ", position=" + position +
                '}';
    }
}
