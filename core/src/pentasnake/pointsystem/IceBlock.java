package pentasnake.pointsystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import lombok.Getter;
import pentasnake.client.entities.Snake;

public class IceBlock extends PickupItems {

    @Getter
    Type type = Type.ICE;

    public IceBlock(float x, float y, Stage stage){
        super(x,y,stage);
        setPoints(0);
        setSpawnRate(0.25f);
        this.region = new TextureRegion(new Texture(Gdx.files.internal("iceblock.png")));
        setBoundaryRectangle();
    }

    @Override
    public void applyEffect(Snake snake) {
        snake.freeze();
    }
}
