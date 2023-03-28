package pentasnake.client.messages;

public class SnakeCollision extends Message{
    private boolean isPickup;

    private int pickupId;

    public boolean isPickup() {
        return isPickup;
    }

    public void setPickup(boolean pickup) {
        isPickup = pickup;
    }

    public int getPickupId() {
        return pickupId;
    }

    public void setPickupId(int pickupId) {
        this.pickupId = pickupId;
    }
}
