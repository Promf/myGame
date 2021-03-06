package com.mygdx.game.cars;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

public class EnemyCar extends Actor implements Car
        {
            private final Texture texture;
            private float x;
            private float y;
            private double speed;
            private int column;
            private final int height = Gdx.graphics.getHeight();
            private final int width = Gdx.graphics.getWidth();
            Random rand = new Random();
            double[] speeds = new double[]{height/6.5f, height/6f, height/7f, height/7.5f};
            int[] position_x = new int[]{width/3-width/7, (int) (width*0.45), width-width/3};
            private final Rectangle rectangle;
            private boolean crash=false;
            private final int num;
            private int temp_column;
            private Vector vector;
            private EnemyCar[] enemyCars;
            private int positionXI;

            public enum State{
                STATE_MOVING,
                STATE_CHANGING,
                STATE_CRASHED

            }
            public enum Vector{
                RIGHT,
                LEFT
            }

            public EnemyCar(Texture texture, int x, int y, double speed, int num){
                this.texture = texture;
                this.x = x;
                this.y = y;
                this.speed = speed;
                this.num = num;
                this.rectangle = new Rectangle(getX(), getY(), Gdx.graphics.getWidth()/7f, Gdx.graphics.getHeight()/7f);
                this.positionXI = Math.abs(num-3);
                if (positionXI==3){
                    positionXI=0;
                }
            }

            @Override
            public void setY(float y) {
                super.setY(y);
            }

            public Vector getVector() {
                return vector;
            }

            public void setVector(Vector vector) {
                this.vector = vector;
            }

            public boolean isCrash() {
                return crash;
            }

            public void setCrash(boolean crash) {
                this.crash = crash;
            }

            public int getTemp_column() {
                return temp_column;
            }

            public void setTemp_column(int temp_column) {
                this.temp_column = temp_column;
            }

            public int getColumn() {
                return column;
            }

            public void setColumn(int column) {
                this.column = column;
            }

            State state = State.STATE_MOVING;

            public State getState() {
                return state;
            }

            public void setState(State state) {
                this.state = state;
            }

            @Override
            public float getX() {
                return x;
            }

            @Override
            public float getY() {
                return y;
            }

            public void randMove(){
                if (rand.nextInt(400)==0 && !isCrash()){
                    setState(State.STATE_CHANGING);
                    if (column==1){
                        setVector(Vector.RIGHT);
                    }
                    else if (column==2){
                        if (rand.nextInt(10)>5){
                            setVector(Vector.LEFT);
                        } else {
                            setVector(Vector.RIGHT);
                        }

                    }
                    else {
                        setVector(Vector.LEFT);
                    }

                    setTemp_column(getColumn());
                }
            }

            public void setEnemyCars(EnemyCar[] enemyCars) {
                this.enemyCars = enemyCars;
            }

            void columnSet(){
                if (getX() < position_x[0]){
                    setColumn(1);
                }
                else if (getX() < position_x[1] && getX() >= position_x[0]){
                    setColumn(2);
                }
                else{
                    setColumn(3);
                }
            }

            public Rectangle getBounds(){
                return rectangle;
            }

            void outTheWorld(){
                if (y < -height / 7f){
                    float z=0;
                    for (EnemyCar car:enemyCars){
                        float i = car.getY();
                        if (z<i){
                            z=i;
                        }
                    }
                    speed = speeds[rand.nextInt(4)];
                    x = (position_x[positionXI]-1);
                    y = z+height/2.5f;
                    setState(State.STATE_MOVING);
                    setCrash(false);
                }
            }

            void moveRight(double mc){
                if (getColumn()!=3){
                    if (getTemp_column()==getColumn()) {
                        x += width/3f*mc;
                    }
                    else {
                        setState(State.STATE_MOVING);
                    }
                }
            }
            void moveLeft(double mc){
                if (getColumn()!=1){
                    if (getTemp_column()==getColumn()) {
                        x -= width/3f*mc;
                    }
                    else {
                        setState(State.STATE_MOVING);
                    }
                }
            }

            @Override
            public void move(double mc) {
                switch (getState()){
                    case STATE_MOVING:
                        randMove();
                        y -= speed*mc;
                        collision();
                        crashAvoid();
                        outTheWorld();
                        break;
                    case STATE_CHANGING:
                        columnSet();
                        y -= speed*mc;
                        collision();
                        crashAvoid();
                        if (!crash){
                        switch (getVector()){
                            case LEFT:
                                moveLeft(mc);
                                break;
                            case RIGHT:
                                moveRight(mc);
                                break;
                        }}
                        outTheWorld();
                        break;
                    case STATE_CRASHED:
                        y -= Gdx.graphics.getHeight()/2f*mc;
                        outTheWorld();
                        break;
                }
            }
            void crashAvoid(){
                Rectangle rect = new Rectangle(x, y+height/7f, Gdx.graphics.getWidth()/7f, Gdx.graphics.getHeight()/7f);
                for (EnemyCar car: enemyCars) {
                    if (rect.overlaps(car.getBounds()) && num != car.num){
                        if (car.state==State.STATE_CRASHED){
                            speed+=10;
                            setVector(Vector.RIGHT);
                            setState(State.STATE_CHANGING);
                            break;
                        }
                        speed += 2;
                        car.speed -=2;
                        if (getColumn()==1){
                            setState(State.STATE_CHANGING);
                            setVector(Vector.RIGHT);
                        }
                        else if (getColumn()==3){
                            setState(State.STATE_CHANGING);
                            setVector(Vector.LEFT);
                        }
                        else if (getColumn()==2){
                            setState(State.STATE_CHANGING);
                            setVector(Vector.LEFT);

                        }
                    }
                }
            }

            @Override
            public void collision() {
                rectangle.set(x, y, Gdx.graphics.getWidth()/7f, Gdx.graphics.getHeight()/7f);
                for (EnemyCar car: enemyCars) {
                    if (rectangle.overlaps(car.getBounds()) && car.num!= num) {
                        setCrash(true);
                        setState(State.STATE_CRASHED);
                    }
                }
            }

            @Override
            public void draw(Batch batch, float x) {
                batch.draw(this.texture, (int)getX(), (int)getY(), Gdx.graphics.getWidth()/7f, Gdx.graphics.getHeight()/7f);
            }
        }
