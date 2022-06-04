package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mygdx.game.CutScene;
import com.mygdx.game.CutScenePlan;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.SceneReader;

import java.io.FileNotFoundException;
import java.io.Reader;

public class CutSceneScreen implements Screen {

    private final Stage stage;
    private final CutScene scene;
    private final SceneReader reader;
    private final CutScenePlan plan;

    public CutSceneScreen(MyGdxGame game, int id) throws FileNotFoundException {
        this.stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        reader=new SceneReader(game);
        Gson gson = new Gson();
        Reader file = Gdx.files.internal("scenes/cut_scenes.json").reader();
        Reader file1 = Gdx.files.internal("scenes/scene"+id+"/scene_"+id+".json").reader();
        JsonReader reader = new JsonReader(file);
        JsonReader reader1 = new JsonReader(file1);
        CutScene[] scenes = gson.fromJson(reader, CutScene[].class);
        scene = scenes[id-1];
        this.plan = gson.fromJson(reader1, CutScenePlan.class);
        stage.addActor(this.reader);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update();
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

    }

    public void update(){
        if (Gdx.input.justTouched()){
            plan.next();
            if (plan.getString().equals("do")){
                reader.update(scene.getText());
            }
            else {
                reader.changeTexture(new Texture(Gdx.files.internal(plan.getString())), scene.getText());
            }
        }
    }

}
