package com.mygdx.game.cars;


import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;

public abstract class Cars  {

    protected double speed;
    protected final int height;
    protected final int width;
    protected final Texture texture;


    public Cars(int x, int y, double speed, int height, int width, Texture texture) {
        setX(x);
        setY(y);
        this.speed = speed;
        this.height = height;
        this.width = width;
        this.texture = texture;

    }

    protected abstract void setY(int y);

    protected abstract void setX(int x);


    abstract void move(double ms);

    abstract void collision();




    abstract void draw(MyGdxGame game);



}
