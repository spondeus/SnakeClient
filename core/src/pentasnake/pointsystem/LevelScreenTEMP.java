package pentasnake.pointsystem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.Action;

public class LevelScreenTEMP extends BaseScreenTEMP{
    @Override
    public void initialize() {
        BaseActorTEMP field = new BaseActorTEMP(0, 0, mainStage);
        field.loadTexture("assets/blackscreen.jpg");
        field.setSize(1280, 720);
    }

    @Override
    public void update(float dt) {

    }

}
