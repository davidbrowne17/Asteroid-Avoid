package net.davidbrowne.asteroidavoid.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;

public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;
    protected FillViewport gamePort;


    protected State(GameStateManager gsm){
        this.gsm=gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
        gamePort = new FillViewport(Game.WIDTH,Game.HEIGHT,cam);

    }
    protected abstract void handlerInput();

    public abstract void update(float dt);
    public abstract void dispose();
    public abstract void render(SpriteBatch sb);
}


