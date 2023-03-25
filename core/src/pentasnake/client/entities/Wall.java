package pentasnake.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import lombok.Getter;

public class Wall extends Actor {

    public SnapshotArray<WallParts> getParts() {
        return parts;
    }

    SnapshotArray<WallParts> parts;


    private final ShapeRenderer sr = new ShapeRenderer();
    public Wall(float x, float y, int width, int height){
    }

    public void drawWall(Batch batch, float parentAlpha){
        batch.end();
        sr.setAutoShapeType(true);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(getX(), getY(), getWidth(), getHeight());
        sr.end();
        batch.begin();
    }

    private boolean wallCollision() {
        return false;
    }


}
