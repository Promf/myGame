package com.mygdx.game.screens;

import static com.mygdx.game.MusicPlayer.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.MyGdxGame;

public class LoadScreen implements Screen {

    private final Stage stage;
    private final MyGdxGame game;


    public LoadScreen (final MyGdxGame game){

        music = Gdx.audio.newMusic(Gdx.files.internal("music/old_yellow_bricks.mp3"));

        this.stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.game = game;
        Label.LabelStyle style = new Label.LabelStyle();
        style.font= game.font;
        style.fontColor=Color.WHITE;
        Label label = new Label("ARSENII\nPRESENTS", style);
        label.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, Align.center);
        stage.addActor(label);
        Gdx.input.setInputProcessor(stage);

    }

    public void resize (int width, int height) {

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

    public void show (){
        music.play();
        music.setLooping(true);

    } // do nothing


    public void render (float delta) {
        if (Gdx.input.isTouched()){

            game.setScreen(new MainMenuScreen(game));
        }
        ScreenUtils.clear(Color.BLACK);
        stage.draw();



    }
}

