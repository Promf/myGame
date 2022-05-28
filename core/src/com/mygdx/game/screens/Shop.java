package com.mygdx.game.screens;

import static com.mygdx.game.MyGdxGame.database;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.CarSkin;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.cars.Player;

public class Shop implements Screen {
    private final Stage stage;
    private final MyGdxGame game;
    private final CarSkin[] skins;


    public Shop(final MyGdxGame game, final Player player) {
        Gdx.input.setCatchKey(com.badlogic.gdx.Input.Keys.BACK, true);
        this.game = game;
        skins = new CarSkin[5];

        try {
            for (int i = 0; i < 5; i++) {
                skins[i] = database.select_skin(i);
            }

        } catch (Exception e) {
            database.insert_skin(0,"ЕЕ мобиль", 99999, "data/car.png", 1, "");
            database.insert_skin(1,"ЕЕ мобиль", 99999, "data/car.png", 1, "");
            database.insert_skin(2,"ЕЕ мобиль", 99999, "data/car.png", 1, "");
            database.insert_skin(3,"ЕЕ мобиль", 99999, "data/car.png", 1, "");
            database.insert_skin(4,"ЕЕ мобиль", 99999, "data/car.png", 1, "");
            for (int i = 0; i < 5; i++) {
                skins[i] = database.select_skin(i);
            }
            e.printStackTrace();
        }

        int gameHeight = Gdx.graphics.getHeight();
        int gameWidth = Gdx.graphics.getWidth();

        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        ScrollPane.ScrollPaneStyle style = new ScrollPane.ScrollPaneStyle();

        Table container = new Table();
        Table table = new Table();
        container.background(new TextureRegionDrawable(new Texture(Gdx.files.internal("fon.jpg"))));

        Label.LabelStyle style1 = new Label.LabelStyle(game.font, Color.GOLD);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.font;
        Image imageCoin = new Image();
        imageCoin.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal("coin.png"))));

        final Label coins = new Label(player.coin+"", style1);
        table.add(imageCoin).height(gameWidth /6f).width(gameWidth /6f).align(Align.left);
        table.add(coins).align(Align.left);
        table.row().height(gameHeight /3f);

        for (int i = 0; i < skins.length; i++) {
            final int fi = i;
            Image image = new Image();
            image.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal(skins[i].getTexture()))));
            final TextButton button;
            final TextButton useButton;
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = game.font;
            labelStyle.fontColor = Color.BLACK;
            final Label textName=new Label(skins[fi].getName(), labelStyle);
            final Label textDescription=new Label(skins[fi].getDescription(), labelStyle);
            final boolean[] open = {false};

            if (skins[i].isBought()){
                TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
                buttonStyle.font= game.font;
                buttonStyle.fontColor=Color.GRAY;
                button = new TextButton("Purchased", buttonStyle);
                 if (player.getTexturePath().equals(skins[fi].getTexture())){
                     useButton = new TextButton("Use", buttonStyle);


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
                            coins.setText(player.coin+"");
                            database.update(player);
                            database.update_skins(skins[fi]);

                        }
                    }

                }
            });
            useButton.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y){
                    if (skins[fi].isBought()) {
                        if (!player.getTexturePath().equals(skins[fi].getTexture())) {
                            database.update(skins[fi].getTexture());
                            TextButton.TextButtonStyle style2 = new TextButton.TextButtonStyle();
                            style2.font= game.font;
                            style2.fontColor=Color.GRAY;
                            useButton.setStyle(style2);
                        }
                    }
                }});

            image.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    open[0] = !open[0];
                }


                });

            table.add(textName).expandX().left().colspan(3);
            table.row();
            table.add(image).left().height(gameHeight /7f).width(gameWidth /7f);
            table.add(button).center().align(Align.center);
            table.add(useButton).right().align(Align.center);
            if (open[0]){
                table.add(textDescription).bottom().left().expandX();
            }
            table.row().height(gameHeight /3f);

        }

        ScrollPane scrollPane = new ScrollPane(table, style);
        container.add(scrollPane).width(gameWidth).height(gameHeight);
        container.row();
        container.setBounds(0,0, gameWidth, gameHeight);
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
            stage.dispose();
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


    }
}
