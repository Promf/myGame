package com.mygdx.game.cars;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor implements Car {
    private int x;
    private int y;
    private final double width;
    private final Texture texture;
    private final String texturePath;
    public long coin;
    private int x1;
    private boolean xin;
    private EnemyCar[] enemyCars;
    private final double speed= Gdx.graphics.getWidth()/1.5;
    private boolean crashed=false;
    private boolean moveUpY=false;
    private boolean moveDownY=false;
    private int level;
    Rectangle bounds;

    public int getLevel() {
        return level;
    }

    public boolean isCrashed() {
        return crashed;
    }


    public void setLevel(int level) {
        this.level = level;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    public String getTexturePath() {
        return texturePath;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, x, y, Gdx.graphics.getWidth()/7f, Gdx.graphics.getHeight()/7f);
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
        this.bounds = new Rectangle(x, y, Gdx.graphics.getWidth()/7f, Gdx.graphics.getHeight()/7f);
        this.coin = coin;

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void getInfo(int x1, boolean xin){
     this.x1 = x1;
     this.xin = xin;
    }

    public void moveUp(){
        if (moveDownY) {
            moveUpY = false;
            moveDownY = false;
        }
        else {
            moveUpY=true;
        }
    }

    public void moveDown(){
        if(moveUpY) {
            moveDownY = false;
            moveUpY = false;
        }
        else {
            moveDownY=true;
        }
    }

    @Override
    public void move(double mc) {
        if (moveUpY){
            if (y>0 && y < Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/7 ){
                y+=mc*speed/2;
            }
        }
        else if(moveDownY){
            if (y>0 && y < Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/7 ){
                y-=mc*speed/2;
            }
        }
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
        bounds.set(getX(), getY(), Gdx.graphics.getWidth()/7f, Gdx.graphics.getHeight()/7f);
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
