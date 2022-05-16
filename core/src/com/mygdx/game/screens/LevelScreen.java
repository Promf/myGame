package com.mygdx.game.screens;

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
import com.mygdx.game.Song;

public class LevelScreen implements Screen {
    private ScrollPane scrollPane;
    private ScrollPane.ScrollPaneStyle style;

    private final Table table;
    private final Table container;
    private GameLevel[] levels;
    private final Stage stage;
    private final MyGdxGame game;
    private final int gameWidth= Gdx.graphics.getWidth();
    private final int gameHeight=Gdx.graphics.getHeight();

    public LevelScreen(MyGdxGame game) {
        Gdx.input.setCatchKey(com.badlogic.gdx.Input.Keys.BACK, true);
        this.game = game;
        stage = new Stage(new ExtendViewport(gameWidth, gameHeight));
        table = new Table();
        container = new Table();
        GameLevel level1 = new GameLevel("Terrible story");
        GameLevel level2 = new GameLevel("Escape into Darkness");
        GameLevel level3 = new GameLevel("The End");
        levels = new GameLevel[]{level1, level2, level3};




        for (final GameLevel current : levels) {
            Label.LabelStyle style = new Label.LabelStyle();
            style.font = game.font;
            style.fontColor = Color.MAGENTA;
            TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
            buttonStyle.font = game.font;
            buttonStyle.checkedDownFontColor=Color.BLACK;
            TextButton.TextButtonStyle style1 = new TextButton.TextButtonStyle();
            style1.font= game.font;
            style1.fontColor=Color.DARK_GRAY;
            style1.downFontColor=Color.BLACK;



            TextButton playButton = new TextButton(current.getName(), style1);


            playButton.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {

                }


            });



            table.add(playButton).expandX().center();

            table.row().height(gameHeight);

        }


        Gdx.input.setInputProcessor(stage);
        style = new ScrollPane.ScrollPaneStyle();
        scrollPane = new ScrollPane(table, style);
        container.background(new TextureRegionDrawable(new Texture(Gdx.files.internal("fon.jpg"))));
        container.add(scrollPane).height(gameHeight).width(gameWidth);
        container.row();
        container.setBounds(0,0,gameWidth, gameHeight);
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
