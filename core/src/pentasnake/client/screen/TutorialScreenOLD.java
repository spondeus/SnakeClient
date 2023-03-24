/*
package pentasnake.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pentasnake.client.SnakeGame;
import pentasnake.pointsystem.Type;


public class TutorialScreen implements Screen {

    private final SnakeGame game;
    private final Viewport viewport = new ScreenViewport();
    private final Stage stage;
    private final Stage stageSnakes = new Stage(viewport);
    private final Table table = new Table();
    private final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/dark-hdpi/Holo-dark-hdpi.atlas"));
    private final Skin skin = new Skin(Gdx.files.internal("skin/dark-hdpi/Holo-dark-hdpi.json"), atlas);
    public Label.LabelStyle style = new Label.LabelStyle();
    public static final Type[] pickupTypes = {
            Type.FOOD,
            Type.POISON,
            Type.DRINK,
            Type.WEB,
            Type.ICE,
            Type.GHOST
    };

    String[] pickupDescriptions = {
            "Increases snake's body length",
            "Decreases snake's body length",
            "Speeds up snake movement",
            "Slows down the snake",
            "Freezes the snake for 5 seconds",
            "Enables the snake to go through walls and other snakes"
    };

    public TutorialScreen(SnakeGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        setupUI();
    }

    private void setupUI() {

        style.font = new BitmapFont(Gdx.files.internal("assets/skin/default.fnt"));
        style.fontColor = Color.WHITE;

        skin.add("tutorial", style);

        // A table to hold all the UI elements
        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(1f);


        // A label for the title
        Label titleLabel = new Label("How to Play"
                , new Label.LabelStyle(new BitmapFont()
                , Color.WHITE));
        table.add(titleLabel).colspan(2).center().top();
        table.row();

        Label goal = new Label(
                "To win the game, get the most points ahead of your opponents"
                , new Label.LabelStyle(new BitmapFont()
                , Color.WHITE));
        table.add(goal).right();
        table.row();

        // Left side labels and descriptions
        Label moveLabel = new Label(
                "Controls -"
                , new Label.LabelStyle(new BitmapFont()
                , Color.WHITE));
        Label moveDescriptionLabel = new Label(
                "Press A to turn left \n Press D to turn right"
                , new Label.LabelStyle(new BitmapFont()
                , Color.WHITE
        ));
        table.add(moveLabel).right();
        table.add(moveDescriptionLabel).left().expandX().fillX();
        table.row();

        // Adding the pickup types and images
        Label pickupTypesLabel = new Label(
                "Pickup types"
                , new Label.LabelStyle(new BitmapFont()
                , Color.WHITE));
        table.add(pickupTypesLabel).colspan(2).center().top();
        table.row();

        // A horizontal group to hold the pickup images
        VerticalGroup pickupImages = new VerticalGroup();
        pickupImages.space(0.2f);

        // Adding the pickup images to the horizontal group
        TextureRegionDrawable foodImage = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal(
                                        "food.png"))));
        Image foodIcon = new Image(foodImage);
        foodIcon.setScale(0.25f);
        pickupImages.addActor(foodIcon);

        TextureRegionDrawable poisonImage = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal(
                                        "poison.png"))));
        Image poisonIcon = new Image(poisonImage);
        poisonIcon.setScale(0.25f);
        pickupImages.addActor(poisonIcon);

        TextureRegionDrawable energyDrinkImage = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal(
                                        "energydrink.png"))));
        Image energyDrinkIcon = new Image(energyDrinkImage);
        energyDrinkIcon.setScale(0.25f);
        pickupImages.addActor(energyDrinkIcon);

        TextureRegionDrawable spiderWebImage = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal(
                                        "spiderweb.png"))));
        Image spiderWebIcon = new Image(spiderWebImage);
        spiderWebIcon.setScale(0.25f);
        pickupImages.addActor(spiderWebIcon);

        TextureRegionDrawable iceBlockImage = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal(
                                        "iceblock.png"))));
        Image iceBlockIcon = new Image(iceBlockImage);
        iceBlockIcon.setScale(0.25f);
        pickupImages.addActor(iceBlockIcon);

        TextureRegionDrawable ghostImage = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal(
                                        "ghost.png"))));
        Image ghostIcon = new Image(ghostImage);
        ghostIcon.setScale(0.25f);
        pickupImages.addActor(ghostIcon);

        // Adding the horizontal group of pickup images to the table
        table.add(pickupImages).colspan(2).expandX().center().row();

        // Adding pickup descriptions to the table
        */
/*for (int i = 0; i < pickupTypes.length; i++) {
            table.add(new Label(pickupTypes[i].name(), skin, "tutorial")).padTop(50f).left();
            table.add(new Label(pickupDescriptions[i], skin)).padTop(50f).left().row();
        }*//*


        // Adding table to the stage
        stage.addActor(table);

        // A vertical group to hold the pickup descriptions
        VerticalGroup pickupDescriptionsGroup = new VerticalGroup().space(0.2f);

        // Loop through the pickup types that adds a label for each pickup description
        for (int i = 0; i < pickupTypes.length; i++) {
            Label pickupLabel = new Label(pickupTypes[i].name(), skin, "tutorial");
            Label descriptionLabel = new Label(pickupDescriptions[i], skin);

            // Adding the pickup label and description to the vertical group
            pickupDescriptionsGroup.addActor(pickupLabel);
            pickupDescriptionsGroup.addActor(descriptionLabel);
        }

        // Add the vertical group of pickup descriptions to the table
        table.add(pickupDescriptionsGroup).padTop(1f).left();

       // a new cell for the pickup images
        Cell<VerticalGroup> pickupImagesCell = table.add(pickupImages);
        pickupImagesCell.center();

        // Add a new column to the table for the pickup descriptions
        table.columnDefaults(0).padLeft(1).padRight(1);
        table.columnDefaults(1).padRight(1);

        //Add each pickup description to the table
        for (Type type : pickupTypes) {
            table.row();
            table.add(new Label(type.toString(), skin));
            table.add(new Label(getPickupDescription(type), skin)).expandX().fillX();

            // Add an arrow pointing to the corresponding pickup image
            Image arrow = new Image(new Texture(Gdx.files.internal("arrow.png")));
            arrow.setScaling(Scaling.fit);
            arrow.setAlign(Align.center);
            arrow.setVisible(false);
            */
/*pickupArrows.add(arrow);
            pickupImagesCell.group().addActor(arrow);*//*


        }
    }

    private String getPickupDescription(Type type) {
        switch (type) {
            case FOOD:
                return "Increases snake's body length";
            case POISON:
                return "Decreases snake's body length";
            case DRINK:
                return "Speeds up snake movement";
            case WEB:
                return "Slows down the snake";
            case ICE:
                return "Freezes the snake for 5 seconds";
            case GHOST:
                return "Enables the snake to go through walls and other snakes";
            default:
                return "";
        }
    }

    */
/*private void updatePickupArrows() {
        for (int i = 0; i < pickupTypes.length; i++) {
            Type type = pickupTypes[i];
            Actor actor = pickupArrows.get(i);
            if (type == null || actor == null) continue;
            Array<Actor> actors = ((HorizontalGroup) stage.getRoot().findActor("pickupImages")).getChildren();
            for (int j = 0; j < actors.size; j++) {
                Actor childActor = actors.get(j);
                if (childActor instanceof Image) {
                    TextureRegionDrawable drawable = (TextureRegionDrawable) ((Image) childActor).getDrawable();
                    TextureRegion region = drawable.getRegion();
                    if (region != null && region.getTexture() != null) {
                        String fileName = region.getTexture().getTextureData().getFileHandle().name();
                        if (fileName.equals(type.getTextureName())) {
                            actor.setVisible(true);
                            actor.setPosition(childActor.getX(Align.center) - actor.getWidth() / 2f, childActor.getY(Align.top) - 20f);
                            break;
                        }
                    }
                }
            }
        }
    }*//*



   @Override
    public void show() {
        addButton("Back to main menu (Press B)").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
                table.left();
            }
        });
        addButton("Got it, let's play! (Press P)").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen());
                table.right();
            }
        });
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private TextButton addButton(String name) {
        TextButton button = new TextButton(name, skin);
        table.add(button).width(400).height(80).padBottom(5);
        table.bottom();
        return button;
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.P)) game.setScreen(new PlayScreen());
        if (Gdx.input.isKeyPressed(Input.Keys.B)) game.setScreen(new MenuScreen(game));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update();
        stage.act(Gdx.graphics.getDeltaTime());
        stageSnakes.act(Gdx.graphics.getDeltaTime());
        stageSnakes.draw();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stageSnakes.clear();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        stageSnakes.dispose();
        skin.dispose();
        atlas.dispose();
    }
}
*/
