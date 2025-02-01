package Schanppt_Hubi.Structure.View;

import Schanppt_Hubi.Structure.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import Schanppt_Hubi.Structure.Flow.flow;
public class MainMenuScreen implements Screen {
    private final Main game;
    private final flow gameFlow;
    private Stage stage;
    private Texture background;
    private FitViewport viewport;
    private Image logoImage;
    private Music music;
    private Sound clickSound;

    public MainMenuScreen(final Main game, final flow gameFlow) {
        this.game = game;
        this.gameFlow = gameFlow;
        viewport = new FitViewport(800, 600);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // Create a skin (optional, for button styles)
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        music = Gdx.audio.newMusic(Gdx.files.internal("background_song.mp3"));
        music.setVolume(0.5f);

        clickSound = Gdx.audio.newSound(Gdx.files.internal("clicksound.wav"));

        logoImage = new Image(new Texture("logo.png"));

        // Create a start button
        TextButton startButton = new TextButton("Start Game", skin);
        startButton.setSize(200, 50);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen1(game, gameFlow));
                // gameFlow.startBackendflow();
                clickSound.play();
            }
        });
        TextButton backButton = new TextButton("How to play", skin);
        backButton.setSize(200, 50);
        //  click on the button and every lines of code in addlistener will be executed
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HowToPlayScreen(game,gameFlow));
                clickSound.play();
            }
        });

        // Create a table for layout
        Table table = new Table();
        table.setFillParent(true);
        table.center().top();
        table.add(logoImage).size(500,400);
        table.row();
        table.add(startButton).size(200, 50).spaceBottom(7.0f);;
        table.row();
        table.add(backButton)
            .size(200, 50);

        // Add table to the stage
        stage.addActor(table);

        // Load background texture
        background = new Texture(Gdx.files.internal("test_back.jpg"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }
    public void playBackgroundMusic(){
        if (!music.isPlaying()) {
            music.setLooping(true);  // Loop the music
            music.play();  // Start playing the music
        }

    }
    public void stopBackgroundMusic() {
        // Stop the music when it's not needed
        if (music.isPlaying()) {
            music.stop();
        }
    }

    @Override
    public void show() {
        playBackgroundMusic();
    }

    @Override
    public void hide() {
        stopBackgroundMusic();

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        music.dispose();
        clickSound.dispose();
    }
}
