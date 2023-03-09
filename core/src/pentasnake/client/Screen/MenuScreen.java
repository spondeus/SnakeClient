package pentasnake.client.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class MenuScreen extends BaseScreen {

    private final Skin skin = new Skin();

    public void initialize() {
        Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        Label nameLabel = new Label("Name:", skin);
        TextField nameText = new TextField("", skin);
        Label addressLabel = new Label("Address:", skin);
        TextField addressText = new TextField("", skin);

        Table table = new Table();
        table.add(nameLabel);
        table.add(nameText).width(100);
        table.row();
        table.add(addressLabel);
        table.add(addressText).width(100);
    }

    public void update(float dt) {

//        if (Gdx.input.isKeyPressed(Keys.S))
//            StarfishGame.setActiveScreen(new LevelScreen());
    }

    @Override
    public void render(float dt) {
        super.render(dt);
    }
}