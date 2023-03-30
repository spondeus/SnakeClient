package pentasnake.client.messages;

public class PickupRemove extends Message{

    int pickupId;

    public int getPickupId() {
        return pickupId;
    }

    public void setPickupId(int pickupId) {
        this.pickupId = pickupId;
    }

    @Override
    public String toString() {
        return "PickupRemove{" +
                "pickupId=" + pickupId +
                '}';
    }
}
