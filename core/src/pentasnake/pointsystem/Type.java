package pentasnake.pointsystem;

import lombok.Getter;

@Getter
public enum Type {
    FOOD, POISON, DRINK, WEB, ICE, GHOST;

    public String getImagePath(Type pickupType) {
        switch (pickupType) {
            case FOOD:
                return "food.png";
            case POISON:
                return "poison.png";
            case WEB:
                return "spiderweb.png";
            case ICE:
                return "iceblock.png";
            case DRINK:
                return "energydrink.png";
            case GHOST:
                return "ghost.png";
            default:
                throw new IllegalArgumentException("Invalid pickup type: " + pickupType);
        }
    }

}
