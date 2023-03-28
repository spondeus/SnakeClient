package pentasnake.client.messages;

public class SnakePointChange extends Message{

    private int change;

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }
}

