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
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Song;

public class MusicScreen implements Screen {

    private final Stage stage;
    private final MyGdxGame game;


    public MusicScreen(MyGdxGame game) {
        Gdx.input.setCatchKey(com.badlogic.gdx.Input.Keys.BACK, true);
        this.game = game;
        int gameWidth = Gdx.graphics.getWidth();
        int gameHeight = Gdx.graphics.getHeight();
        stage = new Stage(new ExtendViewport(gameWidth, gameHeight));
        Table table = new Table();
        Table container = new Table();

        Song song1 = new Song("music/old_yellow_bricks.mp3", true, "Old Yellow bricks", "Artic Monkeys");
        Song song2 = new Song("music/breaking_the_habits.mp3", false, "Breaking the habits", "Linkin park");
        Song song3 = new Song("music/paint_it_black.mp3", false, "Pait it nlack", "Nlack EE");
        Song[] music = new Song[]{song1, song2, song3};

        for (final Song current : music) {
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


            Label name = new Label(current.getName() + "\n" + current.getArtist(), style);
            TextButton playButton = new TextButton("Play", style1);
            TextButton stopButton = new TextButton("Stop", style1);

            playButton.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    current.play();
                }


            });

            stopButton.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    current.stop();
                }


            });

            table.add(name).left().expandX().colspan(2);
            table.row();
            table.add(playButton).left().top();
            table.add(stopButton).left();
            table.row().height(gameHeight /4f);

        }


        Gdx.input.setInputProcessor(stage);
        ScrollPane.ScrollPaneStyle style2 = new ScrollPane.ScrollPaneStyle();
        ScrollPane scrollPane = new ScrollPane(table, style2);
        container.background(new TextureRegionDrawable(new Texture(Gdx.files.internal("backgrounds/fon.jpg"))));
        container.add(scrollPane).height(gameHeight).width(gameWidth);
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
