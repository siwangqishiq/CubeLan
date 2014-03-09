package com.xinlan.cubelan.screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

/**
 * ÓÎÏ·Ö÷ÆÁÄ»
 * 
 * @author panyi
 * 
 */
public class GameScreen extends CubeLanScreen {
	
	public GameScreen(Game game) {
		super(game);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
		
		Gdx.gl.glClearColor(1f, 0f, 1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void hide() {
		
	}
}// end class
