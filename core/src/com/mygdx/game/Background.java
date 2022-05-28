package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {
    private final Texture bitmap_1;
    private final Texture tree;


//
    private float y;
    private float y1;
    private float treeY, treeY1, treeY2;


    public Background(Texture bitmap_1, Texture tree) {
        this.bitmap_1 = bitmap_1;

        this.tree = tree;



        this.y = Gdx.graphics.getHeight();
        this.y1=0;
        this.treeY= Gdx.graphics.getHeight()/3f;
        treeY2 = Gdx.graphics.getHeight()/2f+Gdx.graphics.getWidth()/6f;
        treeY1 = Gdx.graphics.getHeight();
    }

    public void update() {
        y -= Gdx.graphics.getHeight()/2f * Gdx.graphics.getDeltaTime();
        y1 -= Gdx.graphics.getHeight()/2f * Gdx.graphics.getDeltaTime();
        treeY -= Gdx.graphics.getHeight()/2f * Gdx.graphics.getDeltaTime();
        treeY1 -= Gdx.graphics.getHeight()/2f * Gdx.graphics.getDeltaTime();
        treeY2 -= Gdx.graphics.getHeight()/2f * Gdx.graphics.getDeltaTime();

        if (y1<=-Gdx.graphics.getHeight()){
            y1 = y + Gdx.graphics.getHeight();
        }
        if (y <= -Gdx.graphics.getHeight()){
            y = y1 + Gdx.graphics.getHeight();
        }
        if (treeY<=-Gdx.graphics.getWidth()/6f){
            treeY = Gdx.graphics.getHeight()+Gdx.graphics.getWidth()/6f;
        }
        if (treeY1<=-Gdx.graphics.getWidth()/6f){
            treeY1 = Gdx.graphics.getHeight()+Gdx.graphics.getWidth()/6f;
        }
        if (treeY2<=-Gdx.graphics.getWidth()/6f){
            treeY2 = Gdx.graphics.getHeight()+Gdx.graphics.getWidth()/6f;
        }


    }









    @Override
    public void draw(Batch batch, float x){


        batch.draw(bitmap_1, 0, y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(bitmap_1, 0,  y1, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(tree, 0, treeY, Gdx.graphics.getWidth()/6f, Gdx.graphics.getWidth()/6f);
        batch.draw(tree, 0, treeY1, Gdx.graphics.getWidth()/6f, Gdx.graphics.getWidth()/6f);
        batch.draw(tree, Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/6f, treeY2, Gdx.graphics.getWidth()/6f, Gdx.graphics.getWidth()/6f);


    }


}
