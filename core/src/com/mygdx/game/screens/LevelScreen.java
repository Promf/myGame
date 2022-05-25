package com.mygdx.game.screens;

import static com.mygdx.game.MyGdxGame.database;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Output;
import com.mygdx.game.GameLevel;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.cars.Player;
import com.mygdx.game.serialize.PlayerSerialize;

import java.io.FileOutputStream;

public class LevelScreen implements Screen {
    private ScrollPane scrollPane;
    private ScrollPane.ScrollPaneStyle style;
    private Player player;
    private final Table table;
    private final Table container;
    private GameLevel[] levels;
    private final Stage stage;
    private final MyGdxGame game;
    private final int gameWidth= Gdx.graphics.getWidth();
    private final int gameHeight=Gdx.graphics.getHeight();

    public LevelScreen(MyGdxGame game, final Player play) {
        Gdx.input.setCatchKey(com.badlogic.gdx.Input.Keys.BACK, true);
        this.game = game;
        this.player = play;
        stage = new Stage(new ExtendViewport(gameWidth, gameHeight));
        table = new Table();
        container = new Table();

        try {
            GameLevel level1 = database.select("Terrible story");
            GameLevel level2 = database.select("Escape into Darkness");
            GameLevel level3 = database.select("The End");
            levels = new GameLevel[]{level1, level2, level3};
        } catch (Exception e) {
            database.insert("Terrible story", 0, 10000, 1);
            database.insert("Escape into Darkness", 10000, 20000, 0);
            database.insert("The End", 20000, 100000, 0);
            GameLevel level1 = database.select("Terrible story");
            GameLevel level2 = database.select("Escape into Darkness");
            GameLevel level3 = database.select("The End");
            levels = new GameLevel[]{level1, level2, level3};
            e.printStackTrace();
        }



        for (int i = 0; i < levels.length; i++) {
            if (player.getLevelResults()[i]!=0){
                levels[i].setAvailable(true);
            }
        }

        final Label.LabelStyle styl = new Label.LabelStyle();
        styl.font = game.font;
        styl.fontColor = Color.MAGENTA;
        final Label text = new Label("Your level:"+(player.getLevel()+1), styl);
        table.add(text).expandX().center();
        table.row().height(gameHeight/5f);


        for (int i = 0; i < levels.length; i++) {

            final GameLevel current = levels[i];
            Label.LabelStyle style = new Label.LabelStyle();
            style.font = game.font;
            style.fontColor = Color.MAGENTA;
            final TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
            buttonStyle.font = game.font;
            buttonStyle.checkedDownFontColor=Color.BLACK;
            TextButton.TextButtonStyle style1 = new TextButton.TextButtonStyle();
            style1.font= game.font;
            style1.fontColor=Color.DARK_GRAY;
            style1.downFontColor=Color.BLACK;
            final Label label=new Label("Need to next: "+levels[i].getGoal()+"\n"+"Your record: "+player.getLevelResults()[i], style);



            final TextButton playButton = new TextButton(current.getName(), style1);



            final int finalI = i;
            playButton.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    if (current.isAvailable()){
                        player.setLevel(finalI);
                        label.setText("Need to next: "+current.getGoal()+"\n"+"Your record: "+player.getResult());
                        text.setText("Your level:"+(player.getLevel()+1));
                    }
                    else if (!current.isAvailable()){
                        if (player.getLevelResults()[finalI-1]>= current.getCost()){
                            current.setAvailable(true);
                            playButton.setStyle(buttonStyle);
                            player.setLevel(finalI);
                            label.setText("Need to next: "+current.getGoal()+"\n"+"Your record: "+player.getResult());
                            text.setText("Your level:"+(player.getLevel()+1));

                        }
                    }
                }
            });



            table.add(playButton).expandX().center();
            if (current.isAvailable())
                table.add(label).expandX().center();
            table.row().height(gameHeight/3f);
            }





        Gdx.input.setInputProcessor(stage);
        style = new ScrollPane.ScrollPaneStyle();
        scrollPane = new ScrollPane(table, style);
        container.background(new TextureRegionDrawable(new Texture(Gdx.files.internal("fon.jpg"))));
        container.add(scrollPane).height(gameHeight).width(gameWidth);
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
        Kryo kryo = new Kryo();
        kryo.register(Player.class, new PlayerSerialize());

        try {
            Output output = new Output(new FileOutputStream(Gdx.files.getLocalStoragePath()+"/"+"save.txt"));
            // serialize object to file
            kryo.writeObject(output, player);
            output.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
