package pentasnake.client.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pentasnake.client.SnakeGame;
import pentasnake.client.entities.score.ResultClass;
import pentasnake.client.messages.Message;
import pentasnake.client.messages.Points;
import pentasnake.client.messages.Top10;
import pentasnake.client.socket.ClientSocket;
import pentasnake.client.socket.Communication;

import java.util.ArrayList;
import java.util.Queue;

public class ScoreboardScreen implements Screen {

    private final Viewport viewport = new ScreenViewport();
    private final Stage stage = new Stage(viewport);
    private final Stage stageBack = new Stage(viewport);
    private final Table tableName = new Table();
    private final Table tableScore = new Table();
    private final Table tableTime = new Table();
    private final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/dark-hdpi/Holo-dark-hdpi.atlas"));
    private final Skin skin = new Skin(Gdx.files.internal("skin/dark-hdpi/Holo-dark-hdpi.json"), atlas);
    private final Game game;

    private java.util.List<ResultClass> results = new ArrayList<>();

    Queue<Message> msgQueue;

    public ScoreboardScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        Label scoreboardTitle = new Label("SCOREBOARD", skin);
        scoreboardTitle.setFontScale(2);
        scoreboardTitle.setPosition(Gdx.graphics.getWidth() / 2f - scoreboardTitle.getWidth() + 20, Gdx.graphics.getHeight() / 1.1f);
        stage.addActor(scoreboardTitle);

        ClientSocket client = new Communication((SnakeGame) game).getWebsocketClient();
        Message msg = new Points();
        msg.setId(client.getId());
        while(!client.isOpen()) wait(client);
        client.writeMsg(client.getId(), msg);
        msgQueue = client.getMsgQueue();
        while (msgQueue.isEmpty()) wait(client);
        while (!msgQueue.isEmpty()) {
            Message inMsg = msgQueue.poll();
            if (inMsg instanceof Top10) results = new ArrayList<>(((Top10) inMsg).getResults());
        }
        client.close();

        for (ResultClass result : results) {
            TextButton labelName = new TextButton(result.getName(), skin);
            tableName.add(labelName).width(300).height(60).padBottom(5);
            tableName.row();
            TextButton labelScore = new TextButton(result.getResult().toString(), skin);
            tableScore.add(labelScore).width(300).height(60).padBottom(5);
            tableScore.row();
        }
        tableName.setPosition(Gdx.graphics.getWidth() / 4f - tableName.getWidth() - 2, Gdx.graphics.getHeight() / 2f - tableName.getHeight() / 2);
        tableScore.setPosition(Gdx.graphics.getWidth()/(4/3f)-tableName.getWidth()-2,Gdx.graphics.getHeight()/2f-tableScore.getHeight()/2 );
        stage.addActor(tableName);
        stage.addActor(tableScore);
        stage.addActor(tableTime);


        TextButton backButton = new TextButton("(B)ack to menu", skin);
        backButton.setPosition(Gdx.graphics.getWidth() / 2f - backButton.getWidth() / 2f, tableScore.getHeight());
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen((SnakeGame) game));
            }
        });

        stageBack.addActor(backButton);
        Gdx.input.setInputProcessor(stageBack);

    }

    private static void wait(ClientSocket client) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.B)) game.setScreen(new MenuScreen((SnakeGame) game));
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.6f, 0.3f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        stage.act(Gdx.graphics.getDeltaTime());
        stageBack.act();
        stageBack.draw();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
    }
}
