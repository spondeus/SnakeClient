package pentasnake.client.screen;

import com.badlogic.gdx.utils.SnapshotArray;
import pentasnake.pointsystem.PickupItems;

public class MySnapshotArray extends SnapshotArray<PickupItems> {

    public PickupItems getById(int x){
        for (PickupItems pi:this) {
            if(pi.getId()==x) return pi;
        }
        return null;
    }

}
