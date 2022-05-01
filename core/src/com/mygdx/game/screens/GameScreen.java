package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.mygdx.game.Background;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.cars.EnemyCar;
import com.mygdx.game.cars.Player;

import java.io.FileOutputStream;

public class GameScreen implements Screen {
    private final MyGdxGame game;
    private Background background;
    private final Texture fon;
    Music mainMusic;
    private final Stage stage;
    private final Player playerCar;
    private EnemyCar[] enemyCars;
    private Texture carTexture3, carTexture4, carTexture5, carTexture6;
    private boolean gameOn=true;
    private final int width = Gdx.app.getGraphics().getWidth();
    private final int height = Gdx.app.getGraphics().getHeight();



    public GameScreen(final MyGdxGame game, EnemyCar car1, EnemyCar car2, Player player) {

        this.game = game;


        this.stage = new Stage(new ExtendViewport(width, height));


        fon = new Texture(Gdx.files.internal("fon.jpg"));


        this.background = new Background(fon, 0, 0, width, height);

        this.playerCar = player;


        this.carTexture3 = new Texture(Gdx.files.internal("mini_truck.png"));
        this.carTexture4 = new Texture(Gdx.files.internal("mini_van.png"));
        this.carTexture5 = new Texture(Gdx.files.internal("red_car.png"));
        this.carTexture6 = new Texture(Gdx.files.internal("taxi.png"));

        int height = Gdx.graphics.getHeight();
        int width =Gdx.graphics.getWidth();
        int[] position_x = new int[]{width/3-width/7, (int) (width*0.45), width-width/3};

        int[] position_y = new int[]{height+height/7, height+height/2+height/7, 2*height, height+height/7, height+height/2+height/7, 2*height+height/3};



        EnemyCar car3 = new EnemyCar(carTexture3, (int) position_x[0], position_y[2], 225, 3 );
        EnemyCar car4 = new EnemyCar(carTexture4, (int)position_x[1], position_y[0]+height, 125, 4 );
        EnemyCar car5 = new EnemyCar(carTexture5, (int) position_x[2], position_y[1]+height, 175, 5 );
        EnemyCar car6 = new EnemyCar(carTexture6, (int) position_x[0], position_y[2]+height, 275, 6 );

        enemyCars = new EnemyCar[]{car1, car2, car3, car4, car5, car6};
        playerCar.setEnemyCars(enemyCars);


        Gdx.input.setInputProcessor(stage);
        stage.addActor(background);
        stage.addActor(playerCar);
        for (EnemyCar car: enemyCars) {
            car.setEnemyCars(enemyCars);
            stage.addActor(car);

        }
        Gdx.input.setInputProcessor(stage);

        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("paint_it_black.mp3"));
        mainMusic.setLooping(true);




    }


        @Override
    public void show() {
        mainMusic.play();


    }

    @Override
    public void render(float delta) {


        ScreenUtils.clear(0, 0, 0.2f, 1);
        if (!gameOn){
            mainMusic.stop();
            game.setScreen(new MainMenuScreen(game));
            stage.dispose();
            mainMusic.dispose();
            fon.dispose();
            carTexture3.dispose();
            carTexture4.dispose();
            carTexture5.dispose();
            carTexture6.dispose();
            dispose();



        }
        update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
        draw();




    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);


    }

    @Override
    public void pause() {


    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        Kryo kryo = new Kryo();
        try {
            Output output = new Output(new FileOutputStream(Gdx.files.getLocalStoragePath () + "/" + "saves.bin"));
            // serialize object to file
            kryo.writeObject(output, playerCar);
            output.close();

        }catch (Exception e){
            e.printStackTrace();
        }







    }
    public void draw(){
        background.update();
        stage.draw();
    }
    public void update(boolean isTouched, int x, int y){
        if (playerCar.isCrashed()){
            gameOn=(false);
        }



        playerCar.getInfo(x, isTouched);
        playerCar.move(Gdx.graphics.getDeltaTime());
        for (EnemyCar enemyCar : enemyCars) {
            enemyCar.move(Gdx.graphics.getDeltaTime());
            enemyCar.collision();

        }
        playerCar.collision();


    }

}
