package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.kotcrab.vis.ui.VisUI;
import com.mygdx.game.screens.LoadScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public BitmapFont font1;
	public BitmapFont font2;
	private boolean firstOpen=false;
	public static Database database;
	public static final String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
			+ "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
			+ "1234567890.,:;_¡!¿?\"'+-*/()[]={}";

	public MyGdxGame(Database database) {

		MyGdxGame.database = database;
		try {
			MyGdxGame.database.select_level(1);
		} catch (Exception e) {
			firstOpen=true;
			MyGdxGame.database.insert_skin(0,"E Mobile", 0, "cars/car.png", 1, "She is yours. Ride and don't die!");
			MyGdxGame.database.insert_skin(1,"EE Mobile", 500, "cars/ambulance.png", 0, "She rides it and it disgusting");
			MyGdxGame.database.insert_skin(2,"EEE Mobile", 750, "cars/black_car.png", 0, "Black car whose owner is Russian ganster teacher");
			MyGdxGame.database.insert_skin(3,"EEEE Mobile", 999, "cars/mini_truck.png", 0, "A lot of money - a lot of dirt");
			MyGdxGame.database.insert_skin(4,"EEEEE Mobile", 1250, "cars/mini_van.png", 0, "Mater Van");
			MyGdxGame.database.insert_skin(5,"Red Mobile", 1499, "cars/red_car.png", 0, "Red for head and heart");
			MyGdxGame.database.insert_skin(6,"Taxi Mobile", 2759, "cars/taxi.png", 0, "Become the best taxi in Russia");
			MyGdxGame.database.insert(1, 1, "cars/car.png");
			MyGdxGame.database.insert(1, "Terrible story", 0, 5000, 1, 6, "ED");
			MyGdxGame.database.insert(2, "Escape", 5000, 15000, 0, 6, "ef");
			MyGdxGame.database.insert(3, "The End", 15000, 100000, 0, 6, "DF");
		}
	}

	@Override
	public void create () {
		VisUI.load();
		batch = new SpriteBatch();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("my_font.ttf"));
		FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("text_font.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter2.size=76;
		parameter2.color=Color.BROWN;
		parameter2.characters=RUSSIAN_CHARACTERS;
		parameter1.size=76;
		parameter1.color = Color.BLACK;
		parameter.size=82;
		parameter.color = Color.MAGENTA;
		font = generator.generateFont(parameter);
		font1 = generator.generateFont(parameter1);
		font2= generator1.generateFont(parameter2);

		this.setScreen(new LoadScreen(this, firstOpen));
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
