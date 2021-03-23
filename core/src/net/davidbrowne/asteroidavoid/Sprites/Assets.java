package net.davidbrowne.asteroidavoid.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public  class Assets {

    public static Texture ship,asteroid,background,star,logo;

    public static Music music,gameover;
    public static void init() {
        ship = new Texture("ship.png");
        background = new Texture("bg.png");
        asteroid = new Texture("asteroid.png");
        star = new Texture("star.png");
        logo = new Texture("logo.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        gameover = Gdx.audio.newMusic(Gdx.files.internal("gameover.wav"));
    }
}
