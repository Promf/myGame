package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.screens.LoadScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public BitmapFont font1;
	public static Database database;

	public MyGdxGame(Database database) {
		MyGdxGame.database = database;
		try {
			MyGdxGame.database.select_level(1);
		} catch (Exception e) {
			MyGdxGame.database.insert_skin(0,"EE Mobile", 99999, "data/car.png", 1, "");
			MyGdxGame.database.insert_skin(1,"EE Mobile", 99999, "data/car.png", 0, "She rides it and it disgusting");
			MyGdxGame.database.insert_skin(2,"EE Mobile", 99999, "data/car.png", 0, "");
			MyGdxGame.database.insert_skin(3,"EE Mobile", 99999, "data/car.png", 0, "");
			MyGdxGame.database.insert_skin(4,"EE Mobile", 99999, "data/car.png", 0, "");
			MyGdxGame.database.insert(1, 1, "data/car.png");
			MyGdxGame.database.insert(1, "Terrible story", 0, 10000, 1, 6, "ED");
			MyGdxGame.database.insert(2, "Escape into Darkness", 10000, 20000, 0, 6, "ef");
			MyGdxGame.database.insert(3, "The End", 20000, 100000, 0, 6, "DF");
		}
	}

	@Override
	public void create () {

		batch = new SpriteBatch();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("my_font.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter1.size=76;
		parameter1.color = Color.BLACK;
		parameter.size=82;
		parameter.color = Color.MAGENTA;
		font = generator.generateFont(parameter);
		font1 = generator.generateFont(parameter1);
		this.setScreen(new LoadScreen(this));
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
