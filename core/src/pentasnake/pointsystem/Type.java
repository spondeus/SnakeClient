package pentasnake.pointsystem;

import lombok.Getter;

@Getter
public enum Type {
    FOOD(6, 0.005f),
    POISON(4, 0.0025f),
    DRINK(4, 0.002f),
    WEB(3, 0.002f),
    ICE(3, 0.0015f),
    GHOST(2, 0.001f);

    private final int maxAmount;
    private final float spawnRate;

    Type(int maxAmount, float spawnRate) {
        this.maxAmount = maxAmount;
        this.spawnRate = spawnRate;
    }
    public int getMaxAmount() {
        return maxAmount;
    }

    public float getSpawnRate() {
        return spawnRate;
    }
}
