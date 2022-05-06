package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {
    private final Texture bitmap_1;
    private final Texture tree;



    private float y;
    private float y1;
    private float treeY, treeY1, treeY2;


    public Background(Texture bitmap_1, Texture tree) {
        this.bitmap_1 = bitmap_1;

        this.tree = tree;



        this.y = Gdx.graphics.getHeight();
        this.y1=0;
        this.treeY= Gdx.graphics.getHeight()/10f;
        treeY1 = Gdx.graphics.getHeight()/2f;
        treeY2 = Gdx.graphics.getHeight();


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
        treeY -= Gdx.graphics.getHeight()/2f * Gdx.graphics.getDeltaTime();
        treeY1 -= Gdx.graphics.getHeight()/2f * Gdx.graphics.getDeltaTime();
        treeY2 -= Gdx.graphics.getHeight()/2f * Gdx.graphics.getDeltaTime();

        if (y1<=-Gdx.graphics.getHeight()){
            y1 = y + Gdx.graphics.getHeight();
        }
        if (y <= -Gdx.graphics.getHeight()){
            y = y1 + Gdx.graphics.getHeight();
        }
        if (treeY<=0){
            treeY = Gdx.graphics.getHeight()+Gdx.graphics.getHeight()/8f;
        }
        if (treeY1<=0){
            treeY1 = Gdx.graphics.getHeight()+Gdx.graphics.getHeight()/8f;
        }
        if (treeY2<=0){
            treeY2 = Gdx.graphics.getHeight()+Gdx.graphics.getHeight()/8f;
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
