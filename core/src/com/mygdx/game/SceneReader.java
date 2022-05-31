package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.mygdx.game.screens.MainMenuScreen;

public class SceneReader extends Actor {
    private final VisLabel label;
    private Texture background;
    private final Texture labelBackground;
    private int i=-1;
    private final MyGdxGame game;

    public SceneReader(MyGdxGame game){

        this.labelBackground= new Texture(Gdx.files.internal("scenes/dialogue.png"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font= game.font2;
        label=new VisLabel("Text", style);
        this.game=game;
        label.setWidth(Gdx.graphics.getWidth());
        label.setPosition(0, 0);
        label.setAlignment(Align.center);
        label.setAlignment(Align.bottom);
        label.setWrap(true);
        background=new Texture(Gdx.files.internal("backgrounds/fon.jpg"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(labelBackground, 0, 0, Gdx.graphics.getWidth(), label.getPrefHeight());

        label.draw(batch, parentAlpha);
    }
    public void changeTexture(Texture texture, String[] text){
        background=texture;
        if (i<text.length-1) {
            i++;
        }
        else {
            game.setScreen(new MainMenuScreen(game));
        }
        label.setText(text[i]);
        print();

    }
    public void update(String[] text){
        if (i<text.length-1) {
            i++;
        }
        else {
            game.setScreen(new MainMenuScreen(game));
        }
        label.setText(text[i]);
        print();

    }
    private void print(){


    }

}
