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
    boolean gameOn=true;
    private int coin;
    private int x1;
    private boolean xin;
    private EnemyCar[] enemyCars;
    private boolean crashed=false;

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    Rectangle bounds;


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

    public Player(int x, int y, int speed, int height, int width, Texture texture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.texture = texture;

        this.bounds = new Rectangle(x, y, Gdx.graphics.getWidth()/7, Gdx.graphics.getHeight()/7);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
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


    @Override
    public void move(double mc) {

        if (xin){


            if (width/2 < x1 && getX() < width/1.5) {
                setX((int) (getX()+width/1.5*mc));
            }
            else if (width/2 > x1 && getX() > width/7){
                setX((int) (getX()-width/1.5*mc));
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
}
