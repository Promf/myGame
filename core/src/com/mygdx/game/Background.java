package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {
    private final Texture bitmap_1;
    private final Texture bitmap_2;


    private float y;
    private float y1;


    public Background(Texture bitmap_1, double x, double y, int width, int height) {
        this.bitmap_1 = bitmap_1;
        this.bitmap_2=bitmap_1;


        this.y = Gdx.graphics.getHeight();
        this.y1=0;


    }






    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = (float) y1;
    }





    public void update() {
        y -= Gdx.graphics.getHeight()/2f * Gdx.graphics.getDeltaTime();
        y1 -= Gdx.graphics.getHeight()/2f * Gdx.graphics.getDeltaTime();



        if (y1<=-Gdx.graphics.getHeight()){
            y1 = y + Gdx.graphics.getHeight();
        }
        if (y <= -Gdx.graphics.getHeight()){
            y = y1 + Gdx.graphics.getHeight();
        }

    }









    @Override
    public void draw(Batch batch, float x){


        batch.draw(bitmap_1, 0, y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(bitmap_2, 0,  y1, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());





    }


}
