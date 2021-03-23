package net.davidbrowne.asteroidavoid.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Coin {
    public static final int ASTEROID_WIDTH = 16;

    private static final int FLUCTUATION =130;
    private static final int ASTEROID_GAP =100,LOWEST_OPENING=120;
    private Texture Coin;
    private Vector2 posCoin;
    private Rectangle bounds;
    private Random rand;
    private Animation coinAnimation;
    private Ship ship;
    public Coin(float x,float y,Ship ship){
        this.ship=ship;
        Coin = Assets.star;
        coinAnimation = new Animation(new TextureRegion(Coin),3,0.3f);
        rand = new Random();
        posCoin = new Vector2(x,y);
        bounds = new Rectangle(posCoin.x, posCoin.y, ASTEROID_WIDTH, ASTEROID_WIDTH);

    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void update(float dt){
        coinAnimation.update(dt);
        bounds.setPosition(posCoin.x, posCoin.y);
    }

    public TextureRegion getCoinTexture() {
        return coinAnimation.getFrame();
    }



    public Vector2 getCoinPos() {
        return posCoin;
    }



    public void reposition(){

        posCoin = new Vector2((ship.getPosition().x +200)+rand.nextInt(100),rand.nextInt(350));


        bounds.setPosition(posCoin.x, posCoin.y);

    }

    public boolean collides(Rectangle player){
        return player.overlaps(bounds) || player.overlaps(bounds);
    }

    public void dispose(){
        Coin.dispose();

    }
}
