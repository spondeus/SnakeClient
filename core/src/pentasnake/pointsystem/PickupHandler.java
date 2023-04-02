package pentasnake.pointsystem;

public interface PickupHandler {

    void spawnPickups(float delta);
    void pickupCollected(PickupItems item);
}
