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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
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
        skins = new CarSkin[database.getCount()+1];
        for (int i = 0; i < skins.length; i++) {
                skins[i] = database.select_skin(i);
        }
        int gameHeight = Gdx.graphics.getHeight();
        final int gameWidth = Gdx.graphics.getWidth();
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        ScrollPane.ScrollPaneStyle style = new ScrollPane.ScrollPaneStyle();
        VisTable container = new VisTable();
        final VisTable table = new VisTable(true);
        container.background(new TextureRegionDrawable(new Texture(Gdx.files.internal("backgrounds/fon.jpg"))));
        Label.LabelStyle style1 = new Label.LabelStyle(game.font, Color.GOLD);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.font;
        Image imageCoin = new Image();
        imageCoin.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal("game_elements/coin.png"))));
        final Label coins = new Label(player.coin+"", style1);
        table.add(imageCoin).height(gameWidth /6f).width(gameWidth /6f).align(Align.left);
        table.add(coins).align(Align.left);
        table.row().height(gameHeight /3f);
        for (int i = 0; i < skins.length; i++) {
            final int fi = i;
            VisImage image = new VisImage();
            image.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal(skins[i].getTexture()))));
            final VisTextButton button;
            final VisTextButton useButton;
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = game.font;
            final VisLabel textName=new VisLabel(skins[fi].getName(), labelStyle);
            final VisLabel textDescription=new VisLabel("Info", labelStyle);
            final boolean[] bool = {false};
            if (skins[i].isBought()){
                TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
                buttonStyle.font= game.font;
                buttonStyle.fontColor=Color.GRAY;
                button = new VisTextButton("Purchased");
                button.setStyle(buttonStyle);
                 if (player.getTexturePath().equals(skins[fi].getTexture())){
                     useButton = new VisTextButton("Use");
                     useButton.setStyle(buttonStyle);
                 }
                 else {
                     useButton = new VisTextButton("Use");
                     useButton.setStyle(textButtonStyle);
                 }
            }
            else {
                button = new VisTextButton("Buy\n"+skins[i].getPrice());
                useButton = new VisTextButton("Use");
                button.setStyle(textButtonStyle);
                useButton.setStyle(textButtonStyle);
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

            textDescription.addListener(new ClickListener(){

                public void clicked(InputEvent event, float x, float y) {
                    bool[0] =!bool[0];
                    if (bool[0]) {
                        textDescription.setWrap(true);
                        textDescription.setWidth(gameWidth);
                        textDescription.setText(skins[fi].getDescription());
                    }
                    else {
                        textDescription.setText("Info");
                    }
                }
                });

            table.add(textName).expandX().left().colspan(3).height(100);
            table.row();
            table.add(image).left().height(gameHeight /7f).width(gameWidth /7f);
            table.add(button).center().align(Align.center);
            table.add(useButton).right().align(Align.center);
            table.row().expandX().colspan(3);
            table.add(textDescription).left().width(gameWidth);
            table.row();

        }

        VisScrollPane scrollPane = new VisScrollPane(table, style);
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
