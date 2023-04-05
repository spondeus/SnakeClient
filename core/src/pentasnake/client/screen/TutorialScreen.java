package pentasnake.client.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pentasnake.client.SnakeGame;

public class TutorialScreen implements Screen {

    private final SnakeGame game;
    private final Viewport viewport = new ScreenViewport();
    private final Stage stage;
    private final Stage stageSnakes = new Stage(viewport);
    private final Table table = new Table();
    private final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/dark-hdpi/Holo-dark-hdpi.atlas"));
    private final Skin skin = new Skin(Gdx.files.internal("skin/dark-hdpi/Holo-dark-hdpi.json"), atlas);
    public Label.LabelStyle style = new Label.LabelStyle();

    public static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    public static final int CENTER_X = SCREEN_WIDTH / 2;
    public static final int CENTER_Y = SCREEN_HEIGHT / 2;
    public static final int COL_WIDTH = SCREEN_WIDTH / 8;
    public static final int ROW_HEIGHT = SCREEN_HEIGHT / 8;

    public TutorialScreen(SnakeGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        setupUI();
    }

    @Override
    public void show() {

        addButton("(B)ACK TO MAIN MENU", true).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
                table.left();
            }
        });
        /*addButton("GOT IT, LET'S (P)LAY!", false).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LobbyScreen(game, true));
                table.right();
                game.setScreen(new LobbyScreen(game,true));
            }
        });*/
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private void setupUI() {

        style.font = new BitmapFont(Gdx.files.internal("assets/skin/default.fnt"));
        style.fontColor = Color.WHITE;

        skin.add("tutorial", style);

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(1f);
        stage.addActor(table);

        Label titleLabel = new Label("HOW TO PLAY"
                , new Label.LabelStyle(new BitmapFont()
                , Color.ORANGE));
        titleLabel.setSize(COL_WIDTH * 2, ROW_HEIGHT * 2);
        titleLabel.setFontScale(2);
        titleLabel.setPosition(CENTER_X - titleLabel.getWidth() / 2, CENTER_Y + ROW_HEIGHT * 2 + 50);
        titleLabel.setAlignment(Align.center);
        table.add(titleLabel);
        stage.addActor(titleLabel);

        Label goalLabel = new Label(
                "GOAL:\nTo win the game, get the most points ahead of your opponents by\n" +
                        "collecting food and rewarding pickups while avoiding the bad ones!"
                , new Label.LabelStyle(new BitmapFont()
                , Color.WHITE));
        goalLabel.setSize(COL_WIDTH, ROW_HEIGHT);
        goalLabel.setFontScale(1.2f);
        goalLabel.setPosition(50, CENTER_Y + 210);
        goalLabel.setAlignment(Align.left);
        table.add(goalLabel);
        stage.addActor(goalLabel);

        Label controlsLabel = new Label(
                "CONTROLS: \nPress A to turn left \nPress D to turn right.\nHold down button for continuous turning."
                , new Label.LabelStyle(new BitmapFont()
                , Color.WHITE));
        controlsLabel.setSize(COL_WIDTH, ROW_HEIGHT);
        controlsLabel.setFontScale(1.2f);
        controlsLabel.setPosition(50, CENTER_Y + 120);
        controlsLabel.setAlignment(Align.left);
        table.add(controlsLabel);
        stage.addActor(controlsLabel);

        Label bewareLabel = new Label(
                "Beware! Colliding with walls, other \nsnakes and yourself will end your game!".toUpperCase()
                , new Label.LabelStyle(new BitmapFont()
                , Color.RED));
        bewareLabel.setSize(COL_WIDTH, ROW_HEIGHT);
        bewareLabel.setFontScale(1.2f);
        bewareLabel.setPosition(50, CENTER_Y + 40);
        bewareLabel.setAlignment(Align.left);
        table.add(bewareLabel);
        stage.addActor(bewareLabel);

        Label pickupTypesLabel = new Label(
                "PICKUP TYPES:\n\nFood\n\nPoison\n\nEnergy drink\n\nSpider web\n\nIce block\n\nGhost potion"
                , new Label.LabelStyle(new BitmapFont()
                , Color.WHITE));
        pickupTypesLabel.setSize(COL_WIDTH, ROW_HEIGHT);
        pickupTypesLabel.setFontScale(1.2f);
        pickupTypesLabel.setPosition(50, CENTER_Y - 140);
        pickupTypesLabel.setAlignment(Align.left);
        table.add(pickupTypesLabel);
        stage.addActor(pickupTypesLabel);

        VerticalGroup pickupImages = new VerticalGroup();

        TextureRegionDrawable foodImage = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal(
                                        "food.png"))));
        Image foodIcon = new Image(foodImage);
        foodIcon.setScale(0.15f);
        pickupImages.addActor(foodIcon);
        stage.addActor(foodIcon);
        foodIcon.setPosition(CENTER_X + 95, CENTER_Y + 220);

        TextureRegionDrawable poisonImage = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal(
                                        "poison.png"))));
        Image poisonIcon = new Image(poisonImage);
        poisonIcon.setScale(0.15f);
        pickupImages.addActor(poisonIcon);
        stage.addActor(poisonIcon);
        poisonIcon.setPosition(CENTER_X + 90, CENTER_Y + 115);

        TextureRegionDrawable energyDrinkImage = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal(
                                        "energydrink.png"))));
        Image energyDrinkIcon = new Image(energyDrinkImage);
        energyDrinkIcon.setScale(0.15f);
        pickupImages.addActor(energyDrinkIcon);
        stage.addActor(energyDrinkIcon);
        energyDrinkIcon.setPosition(CENTER_X + 92, CENTER_Y+5);

        TextureRegionDrawable spiderWebImage = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal(
                                        "spiderweb.png"))));
        Image spiderWebIcon = new Image(spiderWebImage);
        spiderWebIcon.setScale(0.15f);
        pickupImages.addActor(spiderWebIcon);
        stage.addActor(spiderWebIcon);
        spiderWebIcon.setPosition(CENTER_X + 85, CENTER_Y - 80);

        TextureRegionDrawable iceBlockImage = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal(
                                        "iceblock.png"))));
        Image iceBlockIcon = new Image(iceBlockImage);
        iceBlockIcon.setScale(0.15f);
        pickupImages.addActor(iceBlockIcon);
        stage.addActor(iceBlockIcon);
        iceBlockIcon.setPosition(CENTER_X + 90, CENTER_Y - 180);

        TextureRegionDrawable ghostImage = new TextureRegionDrawable(
                new TextureRegion(
                        new Texture(
                                Gdx.files.internal(
                                        "ghost.png"))));
        Image ghostIcon = new Image(ghostImage);
        ghostIcon.setScale(0.15f);
        pickupImages.addActor(ghostIcon);
        stage.addActor(ghostIcon);
        ghostIcon.setPosition(CENTER_X + 90, CENTER_Y - 275);

        //VerticalGroup pickupDescriptionsGroup = new VerticalGroup();

        Label foodLabel = new Label(
                "Increases snake's body length,\nadds 50 points to your score."
                , new Label.LabelStyle(new BitmapFont()
                , Color.ORANGE));
        foodLabel.setSize(COL_WIDTH, ROW_HEIGHT);
        foodLabel.setFontScale(1.2f);
        foodLabel.setPosition(CENTER_X + 200, SCREEN_HEIGHT - 150);
        foodLabel.setAlignment(Align.left);
        table.add(foodLabel);
        stage.addActor(foodLabel);

        Label poisonLabel = new Label(
                "Decreases snake's body length,\ndeducts 100 points from your score."
                , new Label.LabelStyle(new BitmapFont()
                , Color.ORANGE));
        poisonLabel.setSize(COL_WIDTH, ROW_HEIGHT);
        poisonLabel.setFontScale(1.2f);
        poisonLabel.setPosition(CENTER_X + 200, SCREEN_HEIGHT - 250);
        poisonLabel.setAlignment(Align.left);
        table.add(poisonLabel);
        stage.addActor(poisonLabel);

        Label drinkLabel = new Label(
                "Speeds up snake movement.\nWatch out: too much speed can be dangerous!"
                , new Label.LabelStyle(new BitmapFont()
                , Color.ORANGE));
        drinkLabel.setSize(COL_WIDTH, ROW_HEIGHT);
        drinkLabel.setFontScale(1.2f);
        drinkLabel.setPosition(CENTER_X + 200, SCREEN_HEIGHT - 350);
        drinkLabel.setAlignment(Align.left);
        table.add(drinkLabel);
        stage.addActor(drinkLabel);

        Label webLabel = new Label(
                "Slows down the snake."
                , new Label.LabelStyle(new BitmapFont()
                , Color.ORANGE));
        webLabel.setSize(COL_WIDTH, ROW_HEIGHT);
        webLabel.setFontScale(1.2f);
        webLabel.setPosition(CENTER_X + 200, SCREEN_HEIGHT - 450);
        webLabel.setAlignment(Align.left);
        table.add(webLabel);
        stage.addActor(webLabel);

        Label iceLabel = new Label(
                "Freezes the snake for 5 seconds."
                , new Label.LabelStyle(new BitmapFont()
                , Color.ORANGE));
        iceLabel.setSize(COL_WIDTH, ROW_HEIGHT);
        iceLabel.setFontScale(1.2f);
        iceLabel.setPosition(CENTER_X + 200, SCREEN_HEIGHT - 550);
        iceLabel.setAlignment(Align.left);
        table.add(iceLabel);
        stage.addActor(iceLabel);

        Label ghostLabel = new Label(
                "Enables the snake to go through walls, yourself\nand other snakes for 10 seconds." +
                        " Colors your\nbody white, signaling its duration. Can't be stacked!"
                , new Label.LabelStyle(new BitmapFont()
                , Color.ORANGE));
        ghostLabel.setSize(COL_WIDTH, ROW_HEIGHT);
        ghostLabel.setFontScale(1.2f);
        ghostLabel.setPosition(CENTER_X + 200, SCREEN_HEIGHT - 640);
        ghostLabel.setAlignment(Align.left);
        table.add(ghostLabel);
        stage.addActor(ghostLabel);


    }

    private TextButton addButton(String name, Boolean left) {
        TextButton button = new TextButton(name, skin);
        if (left) {
            table.add(button).width(380).height(60).padBottom(10).align(Align.left);
        } else {
            table.add(button).width(380).height(60).padBottom(10).align(Align.right);
        }
        table.bottom();
        return button;
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.P)) game.setScreen(new LobbyScreen(game,true));
       // if (Gdx.input.isKeyPressed(Input.Keys.B)) game.setScreen(new MenuScreen(game));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.1f, 0.6f, 1);
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
