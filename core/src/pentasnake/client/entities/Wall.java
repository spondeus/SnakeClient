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
    SnapshotArray<WallParts> parts;
    public SnapshotArray<WallParts> getParts() {
        return parts;
    }
    private final ShapeRenderer sr = new ShapeRenderer();

    public Wall(SnapshotArray<WallParts> parts){
        this.parts = parts;
    }

    public void addWallPart(float x, float y, float width, float height, Color color) {
        WallParts part = new WallParts(x, y, width, height, color);
        parts.add(part);
    }

    public void drawWall(Batch batch, float parentAlpha){
        sr.setAutoShapeType(true);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(getColor());
        batch.begin();
        for (WallParts part : parts) {
            sr.rect(part.getX(), part.getY(), part.getWidth(), part.getHeight());
        }
        sr.end();
        batch.end();
    }
}
