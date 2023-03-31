package pentasnake.client.messages;

public class PickupRemove extends Message{

    int pickupId;

    int snakeId;

    public PickupRemove(int pickupId, int snakeId) {
        this.pickupId = pickupId;
        this.snakeId = snakeId;
    }

    public PickupRemove(int pickupId) {
        this.pickupId = pickupId;
    }

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

    public int getSnakeId() {
        return snakeId;
    }

    public void setSnakeId(int snakeId) {
        this.snakeId = snakeId;
    }
}
