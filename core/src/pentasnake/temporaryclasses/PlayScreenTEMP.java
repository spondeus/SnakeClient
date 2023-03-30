package pentasnake.temporaryclasses;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.compression.lzma.Base;

import pentasnake.pointsystem.PickupItems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayScreenTEMP extends Game implements Screen, InputProcessor {

    private AssetManager assetManager;
    public Label.LabelStyle labelStyle;
    protected Stage mainStage;
    protected Stage uiStage;
    private Label myPoints;
    private List<Label> pointsLabel;
    // private ArrayList<Snake> snakeList;
    private Table table;

    private SpriteBatch batch;

    private SnapshotArray<PickupItems> pickups;

    public PlayScreenTEMP() {
        mainStage = new Stage();
        uiStage = new Stage();
        batch = new SpriteBatch();
        /*
        PickupSpawner spawner = new PickupSpawner(mainStage);
        pickups = spawner.getPickups();

         */
        initialize();
    }

    public void initialize() {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);
        /*snakeList = new ArrayList<Snake>();
        Snake snake = new Snake(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 20, Color.BLUE);
        snakeList.add(snake);*/
        labelInitialize();
        //mainStage.addActor(snake);
        /*BaseActorTEMP field = new BaseActorTEMP(0,0,mainStage);
        field.loadTexture("blackscreen.png");
        field.setSize(800,600);*/
    }

    public void labelInitialize() {
        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        table = new Table();
        labelStyle.fontColor = Color.GOLD;
        // pointsLabel= new Label(snakeList.get(0).getPoints()+" p",labelStyle);
        pointsLabel = new LinkedList<>();
        // myPoints = new Label(snakeList.get(0).getPoints()+"p",labelStyle);
        pointsLabel.add(myPoints);
        pointsLabel.add(new Label("10 p", labelStyle));
        pointsLabel.add(new Label("20 p", labelStyle));
        pointsLabel.add(new Label("30 p", labelStyle));
        // pointsLabel.setPosition(Gdx.graphics.getWidth()-40,Gdx.graphics.getHeight()-30);
        table.add(myPoints);
        table.add().expandX();
        table.add(pointsLabel.get(0)).row();
        table.add();
        table.add();
        table.add(pointsLabel.get(1)).row();
        table.add();
        table.add();
        table.add(pointsLabel.get(2)).row();
        table.add();
        table.add();
        table.add(pointsLabel.get(3)).row();
        table.add();
        table.add();
        table.setFillParent(true);
        uiStage.addActor(table);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void update(float dt) {
    }

    public void render(float dt) {
        uiStage.act(dt);
        mainStage.act(dt);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainStage.draw();
        uiStage.draw();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void show() {
        //  InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(this);
        im.addProcessor(uiStage);
        im.addProcessor(mainStage);
    }

    @Override
    public void create() {

    }

    //    @Override
//    public void render(float delta) {
//
//    }
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.removeProcessor(this);
        im.removeProcessor(uiStage);
        im.removeProcessor(mainStage);
    }

    @Override
    public void dispose() {
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Label.LabelStyle getLabelStyle() {
        return labelStyle;
    }

    public void setLabelStyle(Label.LabelStyle labelStyle) {
        this.labelStyle = labelStyle;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public Stage getUiStage() {
        return uiStage;
    }

    public void setUiStage(Stage uiStage) {
        this.uiStage = uiStage;
    }

    public Label getMyPoints() {
        return myPoints;
    }

    public void setMyPoints(Label myPoints) {
        this.myPoints = myPoints;
    }

    public List<Label> getPointsLabel() {
        return pointsLabel;
    }

    public void setPointsLabel(List<Label> pointsLabel) {
        this.pointsLabel = pointsLabel;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public SnapshotArray<PickupItems> getPickups() {
        return pickups;
    }

    public void setPickups(SnapshotArray<PickupItems> pickups) {
        this.pickups = pickups;
    }
}


