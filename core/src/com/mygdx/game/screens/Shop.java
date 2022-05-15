package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;
import com.mygdx.game.CarSkin;
import com.mygdx.game.MyGdxGame;

import com.mygdx.game.cars.Car;
import com.mygdx.game.cars.Player;
import com.mygdx.game.serialize.PlayerSerialize;
import com.mygdx.game.serialize.SkinSerializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Shop implements Screen {
    private ScrollPane scrollPane;
    private ScrollPane.ScrollPaneStyle style;

    private final Table table;
    private final Table container;

    private final Stage stage;
    private final MyGdxGame game;
    private final int gameWidth;
    private final int gameHeight;
    private final Player player;
    private final CarSkin[] skins;
    private Image imageCoin;



    public Shop(final MyGdxGame game, final Player player) {
        Gdx.input.setCatchKey(com.badlogic.gdx.Input.Keys.BACK, true);
        this.game = game;
        this.player = player;
        skins = new CarSkin[5];

        Kryo kryo=new Kryo();
        kryo.register(com.mygdx.game.CarSkin.class, new SkinSerializer());

        try {
            Input input = new Input(new FileInputStream(Gdx.files.getLocalStoragePath() + "/" + "skin_save.txt"));
            // deserialize object from file, in this case LinkedList
            for (int i = 0; i < 5; i++) {
                this.skins[i] = kryo.readObject(input, CarSkin.class);
            }

            //this.player = new Player((int) (Gdx.graphics.getWidth()*0.45), (int) (Gdx.graphics.getHeight()/3.5), 30,  Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), "data/car.png", 1);

            input.close();
        } catch (FileNotFoundException e) {


            this.skins[0] = new CarSkin(true, 0, "data/car.png");
            this.skins[1] = new CarSkin(false, 300, "ambulance.png");
            this.skins[2] = new CarSkin(false, 300, "black_car.png");
            this.skins[3] = new CarSkin(false, 300, "mini_truck.png");
            this.skins[4] = new CarSkin(false, 300, "red_car.png");

        }

        this.gameHeight = Gdx.graphics.getHeight();
        this.gameWidth = Gdx.graphics.getWidth();

        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        ScrollPane.ScrollPaneStyle style = new ScrollPane.ScrollPaneStyle();

        this.container = new Table();
        this.table = new Table();
        container.background(new TextureRegionDrawable(new Texture(Gdx.files.internal("fon.jpg"))));

        Label.LabelStyle style1 = new Label.LabelStyle(game.font, Color.GOLD);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.font;
        this.imageCoin = new Image();
        imageCoin.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal("coin.png"))));

        Label coins = new Label(player.coin+"", style1);
        table.add(imageCoin).height(gameWidth/6).width(gameWidth/6).align(Align.left);
        table.add(coins).align(Align.left);
        table.row();

        for (int i = 0; i < skins.length; i++) {
            final int fi = i;
            Image image = new Image();
            image.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal(skins[i].getTexture()))));
            final TextButton button;
            final TextButton useButton;

            if (skins[i].isBought()){
                 button = new TextButton("Purchased", textButtonStyle);
                 if (player.getTexturePath().equals(skins[fi].getTexture())){
                     useButton = new TextButton("Use", textButtonStyle);
                     useButton.setColor(Color.GRAY);
                 }
                 else {
                     useButton = new TextButton("Use", textButtonStyle);
                 }
            }
            else {
                button = new TextButton("Buy\n"+skins[i].getPrice(), textButtonStyle);
                useButton = new TextButton("Use", textButtonStyle);
            }




            button.addListener(new ClickListener(){

                public void clicked(InputEvent event, float x, float y){
                    if (!skins[fi].isBought()){
                        if (skins[fi].getPrice()<=player.coin){
                            player.coin-=skins[fi].getPrice();
                            skins[fi].setBought(true);
                            button.setText("Purchased");

                        }
                    }

                }
            });
            useButton.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y){
                    if (player.getTexturePath().equals(skins[fi].getTexture())){

                    }
                    else {
                        player.setTexturePath(skins[fi].getTexture());


                    }

                }});

            table.add(image).expandX().left();
            table.add(button).expandX().center().align(Align.center);
            table.add(useButton).expandX().right().align(Align.center);


            table.row();

        }

        this.scrollPane=new ScrollPane(table, style);
        container.add(scrollPane).width(gameWidth).height(gameHeight);
        container.row();
        container.setBounds(0,0,gameWidth, gameHeight);
        stage.addActor(container);





    }

    @Override
    public void show() {





    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(255, 255, 255f, 1);


        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        if(Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.BACK)){
            dispose();
            game.setScreen(new MainMenuScreen(game));
        }
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
        Kryo kryo = new Kryo();
        kryo.register(Player.class, new PlayerSerialize());
        kryo.register(CarSkin.class, new SkinSerializer());

        try {
            Output output = new Output(new FileOutputStream(Gdx.files.getLocalStoragePath()+"/"+"skin_save.txt"));
            for (CarSkin skin : skins) {
                kryo.writeObject(output, skin);
            }
            output.close();
            Output out = new Output(new FileOutputStream(Gdx.files.getLocalStoragePath()+"/"+"save.txt"));
            kryo.writeObject(out, player);
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
