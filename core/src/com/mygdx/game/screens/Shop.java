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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;
import com.mygdx.game.MyGdxGame;

import com.mygdx.game.cars.Player;
import com.mygdx.game.serialize.PlayerSerialize;
import com.mygdx.game.serialize.SkinSerializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Shop implements Screen {
    private ScrollPane scrollPane;
    private ScrollPane.ScrollPaneStyle style;

    private Table table;
    private Table container;

    private Stage stage;
    private MyGdxGame game;
    private int gameWidth;
    private int gameHeight;

    public Shop(final MyGdxGame game) {
        this.game = game;

        Kryo kryo=new Kryo();
        kryo.register(com.mygdx.game.CarSkin.class, new SkinSerializer());

        this.gameHeight = Gdx.graphics.getHeight();
        this.gameWidth = Gdx.graphics.getWidth();

        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        ScrollPane.ScrollPaneStyle style = new ScrollPane.ScrollPaneStyle();

        Table container = new Table();
        Table table = new Table();
        container.background(new TextureRegionDrawable(new Texture(Gdx.files.internal("fon.jpg"))));

        Label.LabelStyle style1 = new Label.LabelStyle(game.font, Color.BLACK);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.font;
        Image imageCoin = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("coin.png")))));
        imageCoin.setScale(2,2);
        table.add(imageCoin).expandX().left();
        table.row();

        for (int i = 0; i < 100; i++) {
            Image image = new Image();
            image.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal("red_car.png"))));
            TextButton button = new TextButton("Buy\n100", textButtonStyle);

            Label label = new Label("Car: " + i, style1);
            button.addListener(new ClickListener(){

                public void clicked(InputEvent event, float x, float y){
                    game.setScreen(new MainMenuScreen(game));

                }
            });
            table.add(label).expandX().left();
            table.add(image).expand().center();
            table.add(button).expandX().right();

            table.row();

        }

        ScrollPane scrollPane=new ScrollPane(table, style);
        container.add(scrollPane).width(gameWidth).height(gameHeight);
        container.row();
        container.setBounds(0,0,gameWidth, gameHeight);
        stage.addActor(container);
        table.setDebug(true);




    }

    @Override
    public void show() {



    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(255, 255, 255f, 1);


        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
        Kryo kryo = new Kryo();
        kryo.register(Player.class, new PlayerSerialize());

        try {
            Output output = new Output(new FileOutputStream(Gdx.files.getLocalStoragePath()+"/"+"skin_save.txt"));
            // serialize object to file
            //kryo.writeObject(output, skin_1);
            output.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
