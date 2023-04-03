package pentasnake.client.messages;

public class TimedPickup extends Message {
    private boolean isGhost;

    private boolean effect;

    public boolean isGhost()
    {
        return isGhost;
    }

    public boolean isEffect()
    {
        return effect;
    }

    public TimedPickup(boolean isGhost, boolean effect)
    {
        this.isGhost = isGhost;
        this.effect = effect;
    }
}
