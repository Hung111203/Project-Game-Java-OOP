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

public class HowToPlayScreen implements Screen {
    private final Main game;

    private Stage stage;
    private Texture background;
    private FitViewport viewport;
    private Image logoImage;
    private Sound clickSound;

    public HowToPlayScreen(final Main game,final flow gameFlow){
        this.game = game;
        viewport = new FitViewport(800, 600);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        clickSound = Gdx.audio.newSound(Gdx.files.internal("clicksound.wav"));
        background = new Texture(Gdx.files.internal("rule-background.png"));
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton backButton = new TextButton("Back to Menu", skin);
        backButton.setSize(200, 50);
        //  click on the button and every lines of code in addlistener will be executed
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game, gameFlow));
                clickSound.play();
            }
        });
        Table table = new Table();
        table.setFillParent(true);
        table.center().top();
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
    public void show() {

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

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
    }
}
