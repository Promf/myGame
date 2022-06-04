package com.mygdx.game.screens;

import static com.mygdx.game.MyGdxGame.database;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.GameLevel;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.cars.Player;

import java.io.FileNotFoundException;

public class LevelScreen implements Screen {
    private final Player player;
    private final Stage stage;
    private final MyGdxGame game;

    public LevelScreen(final MyGdxGame game, final Player play) {
        Gdx.input.setCatchKey(com.badlogic.gdx.Input.Keys.BACK, true);
        this.game = game;
        this.player = play;
        int gameWidth = Gdx.graphics.getWidth();
        int gameHeight = Gdx.graphics.getHeight();
        stage = new Stage(new ExtendViewport(gameWidth, gameHeight));
        Table table = new Table();
        Table container = new Table();

        GameLevel[] levels;
        try {
            GameLevel level1 = database.select_level(1);
            GameLevel level2 = database.select_level(2);
            GameLevel level3 = database.select_level(3);
            levels = new GameLevel[]{level1, level2, level3};
        } catch (Exception e) {
            database.insert(1, "Terrible story", 0, 10000, 1, 6, "ED");
            database.insert(2, "Escape into Darkness", 10000, 20000, 0, 6, "ef");
            database.insert(3, "The End", 20000, 100000, 0, 6, "DF");
            GameLevel level1 = database.select_level(1);
            GameLevel level2 = database.select_level(2);
            GameLevel level3 = database.select_level(3);
            levels = new GameLevel[]{level1, level2, level3};
            e.printStackTrace();
        }

        final Label.LabelStyle styl = new Label.LabelStyle();
        styl.font = game.font;
        styl.fontColor = Color.MAGENTA;
        final Label text = new Label("Your level: "+player.getLevel(), styl);


        for (final GameLevel current : levels) {
            Label.LabelStyle style = new Label.LabelStyle();
            style.font = game.font;
            style.fontColor = Color.DARK_GRAY;
            final TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
            buttonStyle.font = game.font;
            buttonStyle.fontColor=Color.DARK_GRAY;
            buttonStyle.checkedDownFontColor = Color.BLACK;
            TextButton.TextButtonStyle style1 = new TextButton.TextButtonStyle();
            style1.font = game.font;
            style1.fontColor = Color.MAGENTA;
            style1.downFontColor = Color.BLACK;
            final Label label = new Label("Need to next: " + current.getGoal() +
                    "\n" + "Your record: " + current.getRecord(), style);
            final TextButton playButton = new TextButton(current.getId()+". "+current.getName(), style1);
            final TextButton plotButton = new TextButton("Plot", buttonStyle);
            playButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    if (current.isAvailable()) {
                        player.setLevel(current.getId());
                        database.update(player);
                        label.setText("Need to next: " + current.getGoal() + "\n" + "Your record: " + database.select_level(current.getId()).getRecord());
                        text.setText("Your level: " + player.getLevel());
                    } else if (!current.isAvailable()) {
                        if (database.select_level(player.getLevel()).getRecord() >= current.getCost()) {
                            current.setAvailable(true);
                            database.update(current);
                            playButton.setStyle(buttonStyle);
                            player.setLevel(current.getId());
                            label.setText("Need to next: " + current.getGoal() + "\n" + "Your record: " + database.select_level(current.getId()).getRecord());
                            text.setText("Your level: " + player.getLevel());
                            try {
                                game.setScreen(new CutSceneScreen(game, current.getId()));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

            plotButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    try {
                        game.setScreen(new CutSceneScreen(game, current.getId()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

            table.add(playButton).expandX().left();
            if (current.isAvailable()) {
                table.row();
                table.add(plotButton).expandX().left();
                table.row();
                table.add(label).expandX().left();
            }
            table.row();
        }
        table.add(new Label("Soon...", styl)).left();
        Gdx.input.setInputProcessor(stage);
        ScrollPane.ScrollPaneStyle style2 = new ScrollPane.ScrollPaneStyle();
        ScrollPane scrollPane = new ScrollPane(table, style2);
        container.background(new TextureRegionDrawable(new Texture(Gdx.files.internal("backgrounds/fon.jpg"))));
        container.add(text).expandX().left().bottom();
        container.row();
        container.add(scrollPane).height(gameHeight-text.getHeight()).width(gameWidth);
        container.row();
        container.setBounds(0,0, gameWidth, gameHeight);
        stage.addActor(container);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(255, 255, 255f, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        if(Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.BACK)){
            dispose();
            stage.dispose();
            game.setScreen(new MainMenuScreen(game));
        }

    }

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

    }

    @Override
    public void dispose() {

    }
}
