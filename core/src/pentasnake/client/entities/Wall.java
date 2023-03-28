package pentasnake.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.ArrayList;
import java.util.List;

public class Wall extends Actor {
    SnapshotArray<WallParts> parts;
    public SnapshotArray<WallParts> getParts() {
        return parts;
    }
    private final ShapeRenderer sr = new ShapeRenderer();

    public Wall(SnapshotArray<WallParts> parts){
        this.parts = parts;
    }


    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        sr.setAutoShapeType(true);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (WallParts part : parts) {
            sr.setColor(part.getWallColor());
            sr.rect(part.getX(), part.getY(), part.getWidth(), part.getHeight());
        }
        sr.end();
        batch.begin();
    }
}
