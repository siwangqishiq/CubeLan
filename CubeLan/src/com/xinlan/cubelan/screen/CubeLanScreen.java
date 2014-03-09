package com.xinlan.cubelan.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * CubeLan所有Screen基类
 * @author panyi
 *
 */
public abstract class CubeLanScreen  implements Screen{
	protected Game game;
	public CubeLanScreen (Game game) {
		this.game = game;
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}//end class
