package com.mygdx.game.cars;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.mygdx.game.MyGdxGame;

public class Player extends Actor implements Car {
    private int x;
    private final int y;
    private final double width;
    private final Texture texture;
    private String texturePath;
    boolean gameOn=true;
    public long coin;
    private int x1;
    private boolean xin;
    private EnemyCar[] enemyCars;
    private double speed= Gdx.graphics.getWidth()/1.5;
    private boolean crashed=false;
    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    Rectangle bounds;

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public String getTexturePath() {
        return texturePath;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, x, y, Gdx.graphics.getWidth()/7, Gdx.graphics.getHeight()/7);
    }



    public boolean isGameOn() {
        return gameOn;
    }

    public EnemyCar[] getEnemyCars() {
        return enemyCars;
    }

    public void setEnemyCars(EnemyCar[] enemyCars) {
        this.enemyCars = enemyCars;
    }

    public Player(int x, int y, int speed, int height, int width, String texture, int coin) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.texturePath = texture;
        this.texture = new Texture(Gdx.files.internal(texture));
        this.bounds = new Rectangle(x, y, Gdx.graphics.getWidth()/7, Gdx.graphics.getHeight()/7);
        this.coin = coin;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Texture getTexture() {
        return texture;
    }

    public void getCars(EnemyCar[] enemyCars){
        this.enemyCars = enemyCars;

    }



    public void setX(int x) {
        this.x = x;
    }






    public void getInfo(int x1, boolean xin){

     this.x1 = x1;
     this.xin = xin;
    }

    public void moveUp(){
        speed+=20;
    }


    @Override
    public void move(double mc) {
        coin+=50*mc;
        if (xin){


            if (width/2 < x1 && getX() < width/1.5) {
                setX((int) (getX()+speed*mc));
            }
            else if (width/2 > x1 && getX() > width/7){
                setX((int) (getX()-speed*mc));
            }
        }

    }

    @Override
    public void collision() {
        bounds.set(getX(), getY(), Gdx.graphics.getWidth()/7, Gdx.graphics.getHeight()/7);
        for (EnemyCar car: enemyCars) {
            if (bounds.overlaps(car.getBounds())) {
                setCrashed(true);
            }

        }

    }

    public long getCoin() {
        return coin;
    }
}
