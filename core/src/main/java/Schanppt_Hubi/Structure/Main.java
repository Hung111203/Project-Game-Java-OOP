package Schanppt_Hubi.Structure;

import Schanppt_Hubi.Structure.Flow.flow;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    public SpriteBatch batch;


    @Override
    public void create() {
        batch = new SpriteBatch();
        OutputCapture outputCapture = new OutputCapture();
        flow fl=new flow(this, outputCapture);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
