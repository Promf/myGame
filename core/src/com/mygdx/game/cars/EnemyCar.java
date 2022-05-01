package com.mygdx.game.cars;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

public class EnemyCar extends Actor implements Car
        {
            private Texture texture;
            private float x;
            private float y;
            private double speed;
            private int column;
            private int height = Gdx.graphics.getHeight();
            private int width = Gdx.graphics.getWidth();
            Random rand = new Random();
            double[] speeds = new double[]{height/3f, height/4f, height/7f, height/5f};
            int[] position_x = new int[]{width/3-width/7, (int) (width*0.45), width-width/3};
            int[] position_y = new int[]{height+height/7, height+height/2, 2*height+height/3, height+height/7, height+height/2, 2*height+height/3};
            private Rectangle rectangle;
            private boolean crash=false;
            private final int num;
            private int temp_column;
            private Vector vector;
            private EnemyCar[] enemyCars;
            private int positionI;
            private int positionXI;


            public EnemyCar(Texture texture, int x, int y, double speed, int num){
                this.texture = texture;
                this.x = x;
                this.y = y;
                this.speed = speed;
                this.num = num;
                this.rectangle = new Rectangle(getX(), getY(), Gdx.graphics.getWidth()/7, Gdx.graphics.getHeight()/7);
                this.positionI=num-1;
                this.positionXI = Math.abs(num-3);

            }



            public Vector getVector() {
                return vector;
            }

            public void setVector(Vector vector) {
                this.vector = vector;
            }

            public void getCars(EnemyCar[] enemyCars){
                this.enemyCars = enemyCars;
            }

            public boolean isCrash() {
                return crash;
            }

            public void setCrash(boolean crash) {
                this.crash = crash;
            }

            public enum State{
                STATE_MOVING,
                STATE_CHANGING,
                STATE_CRASHED

            }
            public enum Vector{
                RIGHT,
                LEFT
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



            public float getX() {
                return (float) x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public float getY() {
                return (float) y;
            }

            public void setY(int y) {
                this.y = y;
            }
            public int getNum(){return num;}

            void randMove(){
                if (rand.nextInt(200)==0 && !isCrash()){
                    setState(State.STATE_CHANGING);
                    if (rand.nextInt(500)>250){
                        setVector(Vector.LEFT);
                    }
                    else {
                        setVector(Vector.RIGHT);
                    }
                    setTemp_column(getColumn());
                }


            }

            public EnemyCar[] getEnemyCars() {
                return enemyCars;
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
                if (getY() < -height / 7f){
                    if (positionI>5){
                        positionI = 0;
                    }
                    if (positionXI>2){
                        positionXI=0;
                    }
                    speed = speeds[rand.nextInt(4)];
                    setX(position_x[positionXI]);
                    setY(position_y[positionI]);
                    setState(State.STATE_MOVING);
                    setCrash(false);


                }
            }

            void moveRight(double mc){
                if (getColumn()!=3){
                    if (getTemp_column()==getColumn()) {
                        setX((int) (getX() + width/300*mc));
                    }
                    else {
                        setState(State.STATE_MOVING);
                    }

                }

            }
            void moveLeft(double mc){
                if (getColumn()!=1){
                    if (getTemp_column()==getColumn()) {
                        setX((int) (getX() - width/300*mc));
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
                        setY((int) (getY()-speed*mc));
                        collision();
                        crashAvoid();
                        outTheWorld();
                        break;


                    case STATE_CHANGING:
                        columnSet();
                        setY((int) (getY()-speed*mc));
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
                        setY((int) (getY()-Gdx.graphics.getHeight()/2f*mc));
                        outTheWorld();
                        break;
                }

            }
            void crashAvoid(){

                Rectangle rect = new Rectangle(x, y+height/7, Gdx.graphics.getWidth()/7f, Gdx.graphics.getHeight()/7f);
                for (EnemyCar car: enemyCars) {

                    if (rect.overlaps(car.getBounds()) && num != car.num){
                        if (car.state==State.STATE_CRASHED){
                            speed+=10;
                            setVector(Vector.RIGHT);
                            setState(State.STATE_CHANGING);


                            break;
                        }
                        speed += 5;
                        car.speed -=5;
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





            }}

            @Override
            public void collision() {
                rectangle.set(x, y, Gdx.graphics.getWidth()/7f, Gdx.graphics.getHeight()/7f);
                for (EnemyCar car: enemyCars) {
                    if (rectangle.overlaps(car.getBounds()) && car.num!= num) {
                        setCrash(false);
                        setState(State.STATE_CRASHED);
                    }

            }}

            @Override
            public void draw(Batch batch, float x) {
                batch.draw(this.texture, (int)getX(), (int)getY(), Gdx.graphics.getWidth()/7f, Gdx.graphics.getHeight()/7f);
            }

        }
