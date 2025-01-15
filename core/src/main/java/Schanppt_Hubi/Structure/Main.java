package Schanppt_Hubi.Structure;

import Schanppt_Hubi.Structure.Flow.*;
import Schanppt_Hubi.Structure.View.MapGUI;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.io.IOException;

public class Main extends Game {
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        OutputCapture outputCapture = new OutputCapture();
        InputSimulator inputSimulator = new InputSimulator();
        flow fl=new flow(this, outputCapture, inputSimulator);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
