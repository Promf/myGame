package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.LoadScreen;
import com.mygdx.game.screens.MainMenuScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public BitmapFont font1;





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
