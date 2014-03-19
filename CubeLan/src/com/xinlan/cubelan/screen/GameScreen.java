package com.xinlan.cubelan.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
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

    ParticleEffect particle;
    ParticleEffect tem;
    ParticleEffectPool particlePool;
    ArrayList<ParticleEffect> particleList;

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

        particle = new ParticleEffect();
        particle.load(Gdx.files.internal("particle.p"), Gdx.files.internal(""));
        particlePool = new ParticleEffectPool(particle, 10, 15);
        particleList = new ArrayList<ParticleEffect>();
    }

    @Override
    public void render(float delta)
    {
        delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
        coreData.logic(delta);
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        cam.update();
        shapeRenderer.setProjectionMatrix(cam.combined);
        coreData.draw(shapeRenderer, delta);
        spriteBatch.setProjectionMatrix(cam.combined);

        spriteBatch.begin();
        bitmapFont.drawWrapped(spriteBatch, score + "", 420, 770, 100);
        spriteBatch.end();
        if (coreData.dismissList.size() > 4)
        {
            for (int i = 0, size = coreData.dismissList.size(); i < size; i += 2)
            {
                tem = particlePool.obtain();
                int x = coreData.dismissList.get(i);
                int y = coreData.dismissList.get(i + 1);
                tem.setPosition(CoreData.PAD + y * CoreData.CUBE
                        + CoreData.MARGIN * y + CoreData.CUBE / 2,
                        Config.SCREEN_HEIGHT - CoreData.TOP -x * CoreData.CUBE
                                - CoreData.MARGIN * x );
                particleList.add(tem);
            }// end for i
            coreData.dismissList.clear();
        }

        spriteBatch.begin();
        for (int i = 0; i < particleList.size(); i++)
        {
            particleList.get(i).draw(spriteBatch, Gdx.graphics.getDeltaTime());
        }
        spriteBatch.end();

        ParticleEffect temparticle;
        for (int i = 0; i < particleList.size(); i++)
        {
            temparticle = particleList.get(i);
            if (temparticle.isComplete())
            {
                particleList.remove(i);
            }
        } // end for i

    }

    @Override
    public void hide()
    {
        shapeRenderer.dispose();
        spriteBatch.dispose();
        bitmapFont.dispose();
        if (tem != null)
        {
            tem.dispose();
        }
        particlePool.clear();
    }
}// end class
