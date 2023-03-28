package pentasnake.client.messages;

public class SnakeMove extends Message {
    private boolean isLeft;

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }
}
