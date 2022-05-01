package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.mygdx.game.Background;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.cars.EnemyCar;
import com.mygdx.game.cars.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;

public class MainMenuScreen implements Screen {
    private MyGdxGame game;
    private Stage stage;
    private Texture fon;
    private Texture car1, car2;
    private EnemyCar enemyCar1;
    private EnemyCar enemyCar2;
    private Player player;
    private Texture playerTexture;


    public MainMenuScreen(final MyGdxGame game){
        Kryo kryo = new Kryo();
        try {
            Input input = new Input(new FileInputStream(Gdx.files.getLocalStoragePath () + "/" + "saves.bin"));
            // deserialize object from file, in this case LinkedList
            this.player = kryo.readObject(input, Player.class);
            input.close();
        } catch (FileNotFoundException e) {
            this.playerTexture = new Texture(Gdx.files.internal("car.png"));
            this.player = new Player((int) (Gdx.graphics.getWidth()*0.45), (int) (Gdx.graphics.getHeight()/3.5), 30,  Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), playerTexture);
            e.printStackTrace();
        }

        this.game = game;
        this.stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        this.fon = new Texture(Gdx.files.internal("fon.jpg"));
        Background background = new Background(fon, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(background);
        Random random = new Random();

        this.car1 = new Texture(Gdx.files.internal("ambulance.png"));
        this.car2 = new Texture(Gdx.files.internal("black_car.png"));

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        int[] position_x = new int[]{width/3-width/7, (int) (width*0.45), width-width/3};
        int[] position_y = new int[]{height/2+height/7, height-height/7};

        this.enemyCar1 = new EnemyCar(car1, (int) position_x[random.nextInt(3)], position_y[random.nextInt(2)] , 150, 1 );
        this.enemyCar2 = new EnemyCar(car2, (int) position_x[random.nextInt(3)],  position_y[random.nextInt(2)], 200, 2 );


        stage.addActor(this.enemyCar1);
        stage.addActor(this.enemyCar2);
        stage.addActor(player);


        Gdx.input.setInputProcessor(stage);



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isTouched()){



            game.setScreen(new GameScreen(game, enemyCar1, enemyCar2, player));
            stage.dispose();
            fon.dispose();
            this.dispose();

        }
        stage.draw();
        game.batch.begin();
        game.font.draw(game.batch, "Tap to start", Gdx.graphics.getWidth()/3-Gdx.graphics.getWidth()/7, Gdx.graphics.getHeight()/2 );
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

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

    }


}
