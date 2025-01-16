package Schanppt_Hubi.Structure.Map_GUI.core.src.main.java.Hung.Structure.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import Schanppt_Hubi.Structure.Main;
import com.badlogic.gdx.audio.Sound;
import Schanppt_Hubi.Structure.Flow.flow;

public class GameScreen2 implements Screen {
    private final Main game;
    private Stage stage;
    private Texture gameBackground;
    private FitViewport viewport;
    private Image logoImage;
    private Sound clickSound;
    private flow gameFlow;

    public GameScreen2(final Main game) {
        this.game = game;
        viewport = new FitViewport(800, 600);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // Load game background texture
        gameBackground = new Texture(Gdx.files.internal("test_back.jpg"));
        // Create a skin (optional, for button styles)
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        clickSound = Gdx.audio.newSound(Gdx.files.internal("clicksound.wav"));


        TextButton backButton = new TextButton("Back to Number of Players", skin);
        backButton.setSize(200, 50);
        //  click on the button and every line of code in addlistener will be executed
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen1(game));
                clickSound.play();
            }
        });

        logoImage = new Image(new Texture("logo-2.png"));
        TextButton easyButton = new TextButton("easy", skin);
        easyButton.setSize(200, 50);
        easyButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameFlow.getGameRunner().getGame().setLevel("easy");
                // game.setScreen(new MapGUI(game, "easy"));
                clickSound.play();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        TextButton mediumButton = new TextButton("medium", skin);
        mediumButton.setSize(200, 50);
        mediumButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameFlow.getGameRunner().getGame().setLevel("medium");
                // game.setScreen(new MapGUI(game, "medium"));
                clickSound.play();
            }
        });

        TextButton hardButton = new TextButton("hard", skin);
        hardButton.setSize(200, 50);
        hardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameFlow.getGameRunner().getGame().setLevel("hard");
                // game.setScreen(new MapGUI(game, "hard"));
                clickSound.play();
            }
        });


        Table table = new Table();
        table.setFillParent(true);
        table.center().top();
        table.add(logoImage).size(300,300);


        table.row();
        table.add(easyButton).size(200, 50).spaceBottom(7.0f);
        table.row();
        table.add(mediumButton).size(200, 50).spaceBottom(7.0f);
        table.row();
        table.add(hardButton).size(200, 50).spaceBottom(7.0f);
        table.row().bottom().right();
        table.add(backButton)
                .size(200, 50)
                .padTop(35f)
                .spaceBottom(3.0f)
                .align(com.badlogic.gdx.utils.Align.right)
                .expandX();

        // Add table to the stage
        stage.addActor(table);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(gameBackground, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }


    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        gameBackground.dispose();
        clickSound.dispose();
    }
}
