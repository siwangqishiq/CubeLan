package com.xinlan.cubelan;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.xinlan.cubelan.screen.GameScreen;

public class MyGame extends Game {
	FPSLogger fps;
	
	public void create() {
		setScreen(new GameScreen(this));
		fps = new FPSLogger();
	}
	
	
	@Override
	public void render() {
		super.render();
//		fps.log();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		getScreen().dispose();
	}
	
}//end class
