package pentasnake.pointsystem;

import pentasnake.client.messages.Pickup;

public interface PickupHandler {

    Pickup getNewPickup();
    void pickupCollected(PickupItems item);
}
