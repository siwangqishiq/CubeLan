package com.xinlan.cubelan.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.xinlan.cubelan.Config;
import com.xinlan.cubelan.core.CoreData;

/**
 * ÓÎÏ·Ö÷ÆÁÄ»
 * 
 * @author panyi
 * 
 */
public class GameScreen extends CubeLanScreen
{
    public OrthographicCamera cam;
    protected ShapeRenderer shapeRenderer;

    private SpriteBatch spriteBatch;
    private BitmapFont bitmapFont;
    protected CoreData coreData;
    public int score;

    public GameScreen(Game game)
    {
        super(game);
    }

    @Override
    public void show()
    {
        shapeRenderer = new ShapeRenderer();
        cam = new OrthographicCamera(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        cam.position.set(Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2, 0);

        spriteBatch = new SpriteBatch();
        bitmapFont = new BitmapFont(Gdx.files.internal("1.fnt"),
                Gdx.files.internal("1.png"), false);
        coreData = new CoreData(this);
    }

    @Override
    public void render(float delta)
    {
        delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
        coreData.logic(delta);
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        cam.update();
        shapeRenderer.setProjectionMatrix(cam.combined);
        coreData.draw(shapeRenderer, delta);
        spriteBatch.setProjectionMatrix(cam.combined);
        
        spriteBatch.begin();
        bitmapFont.drawWrapped(spriteBatch, score+"", 420, 770, 100);
        spriteBatch.end();
    }

    @Override
    public void hide()
    {
        shapeRenderer.dispose();
        spriteBatch.dispose();
        bitmapFont.dispose();
    }
}// end class
