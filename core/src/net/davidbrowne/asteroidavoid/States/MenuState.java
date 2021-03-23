package net.davidbrowne.asteroidavoid.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class MenuState extends State {

    private Texture background,logo;
    public float volume=0.4f;
    private Skin mySkin;
    private Stage stage;
    private Label outputLabel;
    int row_height = Gdx.graphics.getWidth() / 12;
    int col_width = Gdx.graphics.getWidth() / 12;
    private String Mutetext="Mute";


    public MenuState(GameStateManager gsm) {
        super(gsm);
        Assets.init();
        cam.setToOrtho(false, Game.WIDTH / 2, Game.HEIGHT / 2);
        cam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/3,0);
        background = Assets.background;
        logo = Assets.logo;
        mySkin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));
        create();

    }
    @Override
    public void handlerInput() {
        /*if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }*/
    }

    public void create () {
        stage = new Stage(gamePort);
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        // Text Button
        Button button2 = new TextButton("Play",mySkin,"default");
        button2.setSize(col_width*4,row_height);
        button2.setPosition(col_width*2,Gdx.graphics.getHeight()-row_height*15);
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

        Button button1 = new TextButton(Mutetext,mySkin,"default");
        button1.setSize(col_width*4,row_height);
        button1.setPosition(col_width*2,Gdx.graphics.getHeight()-row_height*17);
        button1.setTransform(true);
        button1.scaleBy(0.5f);
        button1.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(Mutetext=="Mute"){
                    volume = 0.0f;
                    Mutetext="Un-Mute";
                }else{
                    Mutetext="Mute";
                    volume=0.4f;
                }

                return true;
            }
        });


        table.add(button2).center().expandY().padTop(500).padRight(65);
        table.row();
        table.add(button1).center().expandY().padBottom(70).padRight(65);
        stage.addActor(table);


    }

    @Override
    public void update(float dt) {
        handlerInput();
        create();

    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
        logo.dispose();

        System.out.println("menustate disposed");
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(background,0,0,Game.WIDTH,Game.HEIGHT);
        sb.draw(logo,cam.position.x-(logo.getWidth()/2),cam.position.y,200,100);
        //sb.draw(background,0,0 );
        sb.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();
        stage.getBatch().end();
        stage.draw();


        //sb.draw(playBtn,cam.position.x-playBtn.getWidth()/2,cam.position.y);


    }
}