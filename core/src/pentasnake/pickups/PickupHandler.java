package pentasnake.pickups;

import com.badlogic.gdx.utils.SnapshotArray;
import pentasnake.client.messages.Pickup;

public interface PickupHandler {

    Pickup getNewPickup(SnapshotArray<Pickup> pickupList);
    void pickupCollected(PickupItems item);
}
