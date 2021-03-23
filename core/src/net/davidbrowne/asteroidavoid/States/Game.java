package net.davidbrowne.asteroidavoid.States;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.davidbrowne.asteroidavoid.AdHandler;
import net.davidbrowne.asteroidavoid.Sprites.Assets;

public class Game extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Asteroid Avoid";
	private GameStateManager gsm;
	//only make one of these
	private SpriteBatch batch;
	AdHandler handler;
	boolean toggle = true;

	public Game(AdHandler handler) {
		this.handler = handler;
		handler.showAds(true);
	}


	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Assets.init();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose () {
		batch.dispose();


	}
}
