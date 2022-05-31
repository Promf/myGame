package com.mygdx.game.screens;

import static com.mygdx.game.MyGdxGame.database;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.Background;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.TouchListener;
import com.mygdx.game.cars.EnemyCar;
import com.mygdx.game.cars.Player;

import java.util.Random;

public class GameScreen implements Screen {
    private final MyGdxGame game;
    private final Background background;
    private final Texture fon;
    private final ProgressBar bar;
    private final Stage stage;
    private final Player playerCar;
    private final EnemyCar[] enemyCars;
    private final Texture carTexture3;
    private final Texture carTexture4;
    private final Texture carTexture5;
    private final Texture carTexture6;
    private boolean gameOn=true;
    private float timeSeconds = 0f;
    private float time=0f;
    private int coin=0;
    private int road=1;
    private final Label labelCoin;
    private final Label labelRoad;


    public GameScreen(final MyGdxGame game, EnemyCar car1, EnemyCar car2, EnemyCar car3, final Player player) {

        this.game = game;
        int width1 = Gdx.app.getGraphics().getWidth();
        int height1 = Gdx.app.getGraphics().getHeight();
        this.stage = new Stage(new ExtendViewport(width1, height1));
        fon = new Texture(Gdx.files.internal("backgrounds/fon.jpg"));
        Texture tree = new Texture(Gdx.files.internal("game_elements/tree.png"));
        this.background = new Background(fon, tree);
        this.playerCar = player;
        this.carTexture3 = new Texture(Gdx.files.internal("cars/mini_truck.png"));
        this.carTexture4 = new Texture(Gdx.files.internal("cars/mini_van.png"));
        this.carTexture5 = new Texture(Gdx.files.internal("cars/red_car.png"));
        this.carTexture6 = new Texture(Gdx.files.internal("cars/taxi.png"));
        Texture[] textures = new Texture[]{carTexture3, carTexture4, carTexture5, carTexture6};
        int height = Gdx.graphics.getHeight();
        int width =Gdx.graphics.getWidth();
        int[] position_x = new int[]{width/3-width/7, (int) (width*0.45), width-width/3};
        int[] position_y = new int[]{2*height+height/7, height+height/2+height/7, 2*height+height/3};
        Random random = new Random();
        enemyCars = new EnemyCar[database.select_level(playerCar.getLevel()).getCarsCount()];
        enemyCars[0]=car1;
        enemyCars[1]=car2;
        enemyCars[2]=car3;
        for (int i = 0; i < database.select_level(playerCar.getLevel()).getCarsCount()-3; i++) {
            EnemyCar car = new EnemyCar(textures[i], position_x[random.nextInt(2)], position_y[1]+height*i, 225, 4+i );
            enemyCars[i+3]=car;
        }

        playerCar.setEnemyCars(enemyCars);


        Pixmap pixmap = new Pixmap(100,20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        TextureRegionDrawable reg = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        ProgressBar.ProgressBarStyle stylepr = new ProgressBar.ProgressBarStyle();
        stylepr.background= reg;

        Pixmap pixmap1 = new Pixmap(0,20, Pixmap.Format.RGBA8888);
        pixmap1.setColor(Color.GREEN);
        pixmap1.fill();
        TextureRegionDrawable reg1 = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap1)));
        pixmap1.dispose();
        stylepr.knob=reg1;

        Pixmap pixmap2 = new Pixmap(100,20, Pixmap.Format.RGBA8888);
        pixmap2.setColor(Color.GREEN);
        pixmap2.fill();
        TextureRegionDrawable reg2 = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap2)));
        pixmap2.dispose();
        stylepr.knobBefore=reg2;


        this.bar = new ProgressBar(0, database.select_level(playerCar.getLevel()).getGoal(), 1, false, stylepr);
        bar.setPosition(width/2f, height-bar.getHeight(), 1);
        bar.setAnimateDuration(0.25f);




        stage.addActor(background);
        stage.addActor(playerCar);
        for (EnemyCar car: enemyCars) {
            car.setEnemyCars(enemyCars);
            stage.addActor(car);

        }
        InputProcessor processor1 = new TouchListener(new TouchListener.DirectionListener() {
            @Override
            public void onLeft() {

            }

            @Override
            public void onRight() {

            }

            @Override
            public void onUp() {
                player.moveUp();

            }

            @Override
            public void onDown() {
                player.moveDown();

            }
        });
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this.stage);
        inputMultiplexer.addProcessor(processor1);
        Gdx.input.setInputProcessor(inputMultiplexer);

        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor=Color.BLACK;
        style.font= game.font;
        this.labelCoin = new Label(coin+"", style);
        labelCoin.setPosition(labelCoin.getWidth(), Gdx.graphics.getHeight()-labelCoin.getHeight());
        this.labelRoad = new Label(road+"", style);
        labelRoad.setPosition(Gdx.graphics.getWidth()-labelRoad.getWidth()*8, Gdx.graphics.getHeight()-labelCoin.getHeight());
        stage.addActor(labelCoin);
        this.stage.addActor(labelRoad);
        stage.addActor(bar);
    }


        @Override
    public void show() {



    }

    @Override
    public void render(float delta) {


        timeSeconds +=Gdx.graphics.getDeltaTime();
        time += Gdx.graphics.getDeltaTime();
        bar.setValue(road);


        if (time>0.02){
            time-=0.02;
            road+=1;
        }

        float period = 1f;
        if(timeSeconds > period){
            timeSeconds-= period;
            coin+=1;
        }
        labelRoad.setText(road+"");
        labelCoin.setText(""+ coin);




        ScreenUtils.clear(0, 0, 0.2f, 1);
        if (!gameOn){
            
            stage.dispose();

            fon.dispose();
            carTexture3.dispose();
            carTexture4.dispose();
            carTexture5.dispose();
            carTexture6.dispose();
            dispose();
            game.setScreen(new MainMenuScreen(game));



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
        playerCar.coin += coin;
        database.update(playerCar);
        if (road>database.select_level(playerCar.getLevel()).getRecord()) {
            database.update(database.select_level(playerCar.getLevel()), road);
        }
    }
    public void draw(){
        background.update();
        stage.draw();
        stage.act();
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
