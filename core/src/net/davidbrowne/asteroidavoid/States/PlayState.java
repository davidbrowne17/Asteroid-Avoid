package net.davidbrowne.asteroidavoid.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import net.davidbrowne.asteroidavoid.Sprites.Assets;
import net.davidbrowne.asteroidavoid.Sprites.Asteroid;
import net.davidbrowne.asteroidavoid.Sprites.Coin;
import net.davidbrowne.asteroidavoid.Sprites.Ship;

import java.util.Random;

public class PlayState extends State {

    private static final int ASTEROID_COUNT = 5;
    private int score=0,stars=0;
    private Ship ship;
    private Texture bg;
    private BitmapFont font;
    private Music music;
    private float volume;

    private Array<Coin> coins;
    private Array<Asteroid> asteroids;


    public PlayState(GameStateManager gsm,float volume,int score) {

        super(gsm);
        this.score=score;
        Assets.init();
        this.volume=volume;
        font = new BitmapFont(Gdx.files.internal("game.fnt"),Gdx.files.internal("game.png"),false);
        ship = new Ship(50,200,gsm,this,volume);
        cam.setToOrtho(false, Game.WIDTH/2,Game.HEIGHT/2);
        bg = Assets.background;
        cam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/3,0);
        music = Assets.music;
        music.setLooping(true);
        music.setVolume(volume);
        music.play();

        asteroids = new Array<Asteroid>();
        for(int i =0;i<ASTEROID_COUNT;i++){
            Random rand = new Random();
            asteroids.add(new Asteroid(100+(i*100),100,ship));
            asteroids.get(i).reposition();
        }
        coins = new Array<Coin>();
        for(int i =0;i<ASTEROID_COUNT;i++){
            Random rand = new Random();
            coins.add(new Coin(100+(i*100),100,ship));
            coins.get(i).reposition();
        }
    }

    @Override
    protected void handlerInput() {
        if(ship.getPosition().y<Game.HEIGHT/2-45){

            if(Gdx.input.isTouched()){
                ship.jump();

            }

        }
        //System.out.println(ship.getPosition().x+","+ship.getPosition().y);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean checkCollisions(){
        for(int i=0;i<ASTEROID_COUNT;i++){
            if(asteroids.get(i).collides(ship.getBounds()))
                return true;
        }return false;
    }
    public boolean checkCoins(){
        for(int i=0;i<ASTEROID_COUNT;i++){
            if(coins.get(i).collides(ship.getBounds()))
                return true;
        }return false;
    }

    @Override
    public void update(float dt) {
        handlerInput();
        checkScore();
        if(checkCollisions())
            gsm.set(new DeadState(gsm,score,stars,volume));
        for(int i=0;i>= asteroids.size;i++){
            asteroids.get(i).update();
        }
        if(checkCoins()){
            stars++;
            for(int i=0;i<ASTEROID_COUNT;i++){
                if(coins.get(i).collides(ship.getBounds())){
                    coins.get(i).reposition();
                }
            }
        }
        ship.update(dt);
        for(int i=0;i<ASTEROID_COUNT;i++){
            coins.get(i).update(dt);
        }

        asteroidLoop();
        cam.position.x = ship.getPosition().x +80;


        cam.update();
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void checkScore(){
        if(score>500){
            ship.setMOVEMENT(220);
        }
        else if(score>300){
            ship.setMOVEMENT(200);
        }
        else if(score>200){
            ship.setMOVEMENT(185);
        }
        else if(score > 100){
            ship.setMOVEMENT(175);
        }
        else if(score > 20){
            ship.setMOVEMENT(140);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        ship.dispose();
        font.dispose();
        music.dispose();
        for(Asteroid asteroid : asteroids)
            asteroid.dispose();
        for(Coin coin : coins)
            coin.dispose();
        System.out.println("playstate disposed");
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(bg,ship.getPosition().x-100,0,Game.HEIGHT,Game.WIDTH);
        font.setUseIntegerPositions(false);

        font.draw(sb,"Score: "+score ,cam.position.x-100,cam.viewportHeight);
        font.draw(sb,"Stars: "+stars ,cam.position.x+50,cam.viewportHeight);
        sb.draw(ship.getTexture(),ship.getPosition().x,ship.getPosition().y,32,32);

        for(int i = 0; i<ASTEROID_COUNT;i++){
            sb.draw(asteroids.get(i).getTopAsteroid(),asteroids.get(i).getPosAsteroid().x,asteroids.get(i).getPosAsteroid().y-(Asteroid.ASTEROID_WIDTH/2),62,62);


        }

        for(int i = 0; i<ASTEROID_COUNT;i++){
            sb.draw(coins.get(i).getCoinTexture(),coins.get(i).getCoinPos().x,coins.get(i).getCoinPos().y,32,32);

        }
        font.draw(sb,"Touch screen to fly!", Game.WIDTH/3,Game.HEIGHT/4);


        sb.end();
    }

    public void asteroidLoop(){
        for(int i = 0;i<ASTEROID_COUNT;i++){
            if(asteroids.get(i).getPosAsteroid().x+90< ship.getPosition().x){
                asteroids.get(i).reposition();
                score++;
                /*System.out.println("updated asteroid");
                System.out.println(asteroids.get(i).getPosAsteroid().x+","+asteroids.get(i).getPosAsteroid().y);
            */}
        }

        for(int i = 0;i<ASTEROID_COUNT;i++){
            if(coins.get(i).getCoinPos().x+80< ship.getPosition().x){
                coins.get(i).reposition();
                score++;
                /*System.out.println("updated asteroid");
                System.out.println(asteroids.get(i).getPosAsteroid().x+","+asteroids.get(i).getPosAsteroid().y);
            */}
            for(int y=0;y<ASTEROID_COUNT;y++){
                if(asteroids.get(i).collides(asteroids.get(y).getBounds())&&asteroids.get(y)!=asteroids.get(i)){
                    asteroids.get(i).reposition();
                }
                else if(asteroids.get(i).collides(coins.get(y).getBounds())){
                    asteroids.get(i).reposition();
                }

            }
        }
    }




}
