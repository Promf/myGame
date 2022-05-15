package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.MyGdxGame;

import java.sql.Time;

public class LoadScreen implements Screen {

    private Stage stage;
    private MyGdxGame game;
    private Label.LabelStyle style;
    private Label label;



    public LoadScreen (final MyGdxGame game){
        this.stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.game = game;
        this.style = new Label.LabelStyle();
        style.font= game.font;
        style.fontColor=Color.WHITE;
        label = new Label("ARSENII  \nSTUDIO \nPRESENTS", style);
        label.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Align.center);
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

    public void show (){} // do nothing

    public void render (float delta) {
        if (Gdx.input.isTouched()){

            game.setScreen(new MainMenuScreen(game));
        }
        ScreenUtils.clear(Color.BLACK);
        stage.draw();



    }

    private void loadEverything(){
        // load your font, assets, prefs, etc.





    }
}

