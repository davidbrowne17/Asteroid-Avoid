package net.davidbrowne.asteroidavoid.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import net.davidbrowne.asteroidavoid.Sprites.Assets;

public class DeadState extends State {

    private Texture background;
    private Skin mySkin;
    private Stage stage;
    private Label HighScore,Score,Stars,Respawn;
    private BitmapFont font;
    private Preferences prefs;
    private Music music;
    int row_height = Gdx.graphics.getWidth() / 12;
    int col_width = Gdx.graphics.getWidth() / 12;
    int score,highScore,stars;
    float volume;


    public DeadState(GameStateManager gsm,int score,int stars,float volume) {
        super(gsm);
        Assets.init();
        this.volume=volume;
        this.score=score;
        this.stars=stars;
        cam.setToOrtho(false, Game.WIDTH / 2, Game.HEIGHT / 2);
        cam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/3,0);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Bromine.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        font = generator.generateFont(parameter);
        background = Assets.background;
        mySkin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));

        prefs = Gdx.app.getPreferences("MyPreferences");
        create();

        highScore(score);
        System.out.println(prefs.getInteger("highscore"));

    }
    public void highScore(int score){
        highScore= prefs.getInteger("highscore");
        if(score>=highScore){
            prefs.putInteger("highscore",score);
            highScore = score;

        }
        prefs.flush();


    }

    @Override
    public void handlerInput() {
        /*if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }*/
    }

    public void create () {

        music = Gdx.audio.newMusic(Gdx.files.internal("gameover.wav"));
        music.setLooping(false);
        music.setVolume(volume);
        music.play();
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        stage = new Stage(gamePort);
        Gdx.input.setInputProcessor(stage);
        HighScore = new Label("High Score: "+highScore, new Label.LabelStyle(font, com.badlogic.gdx.graphics.Color.WHITE));
        Score = new Label("Score: "+score, new Label.LabelStyle(font, com.badlogic.gdx.graphics.Color.WHITE));
        Respawn = new Label("Cost: 50 Stars", new Label.LabelStyle(font, com.badlogic.gdx.graphics.Color.WHITE));
        Stars = new Label("Stars: "+stars, new Label.LabelStyle(font, com.badlogic.gdx.graphics.Color.WHITE));
        // Text Button
        Button button2 = new TextButton("Play Again?",mySkin,"default");
        button2.setPosition(col_width*2,Gdx.graphics.getHeight()-row_height*10);
        button2.setTransform(true);
        button2.scaleBy(0.5f);
        button2.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                gsm.set(new PlayState(gsm,volume,0));
                return true;
            }
        });
        table.add(HighScore).center().padTop(150);
        table.row();
        table.add(Score).center();
        table.row();
        table.add(button2).center().padTop(100).padRight(120);
        table.row();
        table.add(Respawn).center().padTop(30);
        table.row();
        table.add(Stars).center().padTop(30);
        table.row();
        // Text Button
        if(stars>=50){
            Button button3 = new TextButton("Respawn?",mySkin,"default");
            button3.setPosition(col_width*2,Gdx.graphics.getHeight()-row_height*17);
            button3.setTransform(true);
            button3.scaleBy(0.5f);
            button3.addListener(new InputListener(){
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                }
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    gsm.set(new PlayState(gsm,volume,score));

                    return true;
                }
            });
            table.add(button3).center().padTop(100).padRight(120);

        }else{
            Button button3 = new TextButton("Cant Afford!",mySkin,"default");
            button3.setPosition(col_width*2,Gdx.graphics.getHeight()-row_height*17);
            button3.setTransform(true);
            button3.scaleBy(0.5f);
            button3.addListener(new InputListener(){
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                }
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {


                    return true;
                }
            });
            table.add(button3).center().padTop(100).padRight(120);
        }
        stage.addActor(table);
    }

    @Override
    public void update(float dt) {
        handlerInput();
    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
        music.dispose();
        mySkin.dispose();
        System.out.println("menustate disposed");
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(background,0,0,Game.WIDTH,Game.HEIGHT);
        sb.end();
        //sb.draw(background,0,0 );
        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();


        stage.getBatch().end();
        stage.draw();


        //sb.draw(playBtn,cam.position.x-playBtn.getWidth()/2,cam.position.y);


    }
}


