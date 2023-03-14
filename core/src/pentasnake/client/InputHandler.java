package pentasnake.client;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import pentasnake.client.entities.Player_SnakeHead;

public class InputHandler extends InputAdapter {

    private Player_SnakeHead head;

    public InputHandler(Player_SnakeHead head) {
        this.head = head;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.A:
                head.setLeftMove(true);
                break;
            case Input.Keys.D:
                head.setRightMove(true);
                break;
        }
        return true;
    }
    @Override
    public boolean keyUp(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.A:
                head.setLeftMove(false);
                break;
            case Input.Keys.D:
                head.setRightMove(false);
                break;
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button==Input.Buttons.LEFT){
            head.turning=new Vector2(screenX-head.position.x,screenY-head.position.y);
            float radians = (float)Math.atan2(head.turning.y - head.position.y, head.turning.x - head.position.x);
            head.setRotation((float)Math.toDegrees(radians));
            head.position.add(new Vector2(head.turning.x/10, head.turning.y/10).setAngle(180));
        }
        return false;
    }


}
