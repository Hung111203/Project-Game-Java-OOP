package Schanppt_Hubi.Structure.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import Schanppt_Hubi.Structure.Main;
public class menuGUI implements Screen {

    private Stage stage;
    private Skin skin;

    public menuGUI(final Main game,MapGUI mapGUI) {
        // Initialize the stage and skin
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json")); // Replace with your skin file path

        // Create a button
        TextButton stopButton = new TextButton("Stop", skin);

        // Set button size and position
        stopButton.setSize(200, 50);
        stopButton.setPosition(
            Gdx.graphics.getWidth() / 2f - stopButton.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - stopButton.getHeight() / 2f
        );

        // Add a click listener to the button
        stopButton.addListener(event -> {
            if (event.toString().equalsIgnoreCase("touchDown")) {
                game.setScreen(mapGUI); // Return to Main Menu
                return true; // Consume the event
            }
            return false;
        });

        // Add the button to the stage
        stage.addActor(stopButton);

        // Set the stage as the input processor
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        // Called when the screen is shown
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Update the stage's viewport
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Called when the application is paused
    }

    @Override
    public void resume() {
        // Called when the application is resumed
    }

    @Override
    public void hide() {
        // Called when the screen is hidden
    }

    @Override
    public void dispose() {
        // Dispose of the stage and skin
        stage.dispose();
        skin.dispose();
    }
}
