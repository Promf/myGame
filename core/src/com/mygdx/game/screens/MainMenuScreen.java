package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.mygdx.game.Background;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.cars.EnemyCar;
import com.mygdx.game.cars.Player;
import com.mygdx.game.serialize.PlayerSerialize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class MainMenuScreen implements Screen {
    private final MyGdxGame game;
    private final Stage stage;
    private final Texture fon;
    private final Texture car1;
    private final Texture car2;
    private final Texture car3;
    private final EnemyCar enemyCar1;
    private final EnemyCar enemyCar2;
    private final EnemyCar enemyCar3;
    private Player player;
    private final ImageButton imageShopButton;
    private final ImageButton imagePrefButton;
    private final ImageButton imageMusicButton;
    private final TextButton startButton;
    private final ImageButton imageMapButton;
    private final Texture treeTexture;
    private final Texture shopTexture;
    private final Texture shopTextureClicked;
    private final Texture prefTexture;
    private final Texture musicTexture;
    private final Texture mapTexture;
    private final Texture mapChoosen;
    private EnemyCar[] enemyCars;
    private Background background;



    public MainMenuScreen(final MyGdxGame game) {
        Kryo kryo = new Kryo();
        kryo.register(Player.class, new PlayerSerialize());

        try {
            Input input = new Input(new FileInputStream(Gdx.files.getLocalStoragePath() + "/" + "save.txt"));
            // deserialize object from file, in this case LinkedList
            this.player = kryo.readObject(input, Player.class);
            //this.player = new Player((int) (Gdx.graphics.getWidth()*0.45), (int) (Gdx.graphics.getHeight()/3.5), 30,  Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), "data/car.png", 1);

            input.close();
        } catch (FileNotFoundException e) {

            this.player = new Player((int) (Gdx.graphics.getWidth() * 0.45), (int) (Gdx.graphics.getHeight() / 3.5), 30, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), "data/car.png", 1);
            e.printStackTrace();
        }

        this.game = game;
        this.stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        this.fon = new Texture(Gdx.files.internal("fon.jpg"));
        this. treeTexture = new Texture(Gdx.files.internal("data/tree.png"));
        this.background = new Background(fon, treeTexture);
        stage.addActor(background);
        Random random = new Random();

        this.car1 = new Texture(Gdx.files.internal("ambulance.png"));
        this.car2 = new Texture(Gdx.files.internal("black_car.png"));
        this.car3 = new Texture(Gdx.files.internal("red_car.png"));

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        int[] position_x = new int[]{width / 3 - width / 7, (int) (width * 0.45), width - width / 3};
        int[] position_y = new int[]{height / 7, height / 2 + height / 7, height-height/14};



        this.enemyCar1 = new EnemyCar(car1, position_x[random.nextInt(3)], position_y[0], 150, 1);
        this.enemyCar2 = new EnemyCar(car2, position_x[random.nextInt(3)], position_y[1], 200, 2);
        this.enemyCar3 = new EnemyCar(car3, position_x[random.nextInt(3)], position_y[2], 175, 3);
        this.enemyCars = new EnemyCar[]{enemyCar1, enemyCar2, enemyCar3};

        for (EnemyCar car: enemyCars){
            car.setEnemyCars(enemyCars);
        }


        stage.addActor(this.enemyCar1);
        stage.addActor(this.enemyCar2);
        stage.addActor(this.enemyCar3);



        Gdx.input.setInputProcessor(stage);

        this.shopTexture = new Texture(Gdx.files.internal("garage.png"));
        TextureRegion shopRegion = new TextureRegion(shopTexture);
        TextureRegionDrawable regionDrawable = new TextureRegionDrawable(shopRegion);

        this.shopTextureClicked = new Texture(Gdx.files.internal("garage_chosen.png"));
        TextureRegionDrawable shop = new TextureRegionDrawable(new TextureRegion(shopTextureClicked));
        regionDrawable.setMinSize(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getWidth() / 4f);
        shop.setMinSize(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getWidth() / 4f);

        this.prefTexture = new Texture(Gdx.files.internal("system.png"));
        TextureRegionDrawable pref = new TextureRegionDrawable(new TextureRegion(prefTexture));
        pref.setMinSize(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getWidth() / 4f);

        this.musicTexture = new Texture(Gdx.files.internal("music.png"));
        TextureRegionDrawable mus = new TextureRegionDrawable(new TextureRegion(musicTexture));
        mus.setMinSize(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getWidth() / 4f);

        this.mapTexture = new Texture(Gdx.files.internal("map.png"));
        this.mapChoosen= new Texture(Gdx.files.internal("map_choosen.png"));
        TextureRegionDrawable map = new TextureRegionDrawable(new TextureRegion(mapTexture));
        TextureRegionDrawable map_pressed = new TextureRegionDrawable(new TextureRegion(mapChoosen));
        map.setMinSize(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getWidth() / 4f);
        map_pressed.setMinSize(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getWidth() /4f);


        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.font;


        this.imageMapButton = new ImageButton(map, map_pressed);
        this.startButton = new TextButton("Tap to start", textButtonStyle);
        this.imageShopButton = new ImageButton(regionDrawable, shop);
        this.imagePrefButton = new ImageButton(pref);
        this.imageMusicButton = new ImageButton(mus);

        startButton.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 1);
        imageMusicButton.setPosition(Gdx.graphics.getWidth() - imageMusicButton.getWidth()-height/56f, Gdx.graphics.getHeight() - imageMusicButton.getHeight());
        imageShopButton.setPosition(height/56f, 0);
        imagePrefButton.setPosition(height/56f, Gdx.graphics.getHeight() - imagePrefButton.getHeight());
        imageMapButton.setPosition(Gdx.graphics.getWidth()/2f-imageMapButton.getWidth()/2, 0);

        stage.addActor(imageMapButton);
        stage.addActor(imageShopButton);
        stage.addActor(imagePrefButton);
        stage.addActor(imageMusicButton);
        stage.addActor(startButton);

        imageShopButton.addListener(new ClickListener(){

            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new Shop(game, player));

            }
        });

        startButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new GameScreen(game, enemyCar1, enemyCar2, enemyCar3, player));
                stage.dispose();
                fon.dispose();
                dispose();

            }
        });







    }





    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        background.update();
        for (EnemyCar car: enemyCars){
            car.move(Gdx.graphics.getDeltaTime());
        }
        stage.draw();

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
        shopTexture.dispose();
        shopTextureClicked.dispose();
        prefTexture.dispose();
        musicTexture.dispose();
        mapTexture.dispose();
        mapChoosen.dispose();


    }


}
