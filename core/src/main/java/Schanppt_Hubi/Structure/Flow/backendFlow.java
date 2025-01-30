package Schanppt_Hubi.Structure.Flow;

import Schanppt_Hubi.Structure.Logic.game.GameRunner;
import Schanppt_Hubi.Structure.OutputCapture;

public class backendFlow implements Runnable {
    private OutputCapture outputCapture;
    private volatile boolean running = true;

    private GameRunner gameRunner;
    public backendFlow(OutputCapture outputCapture,GameRunner gameRunner)    {
        this.gameRunner = gameRunner;
        this.outputCapture = outputCapture;
    }
    @Override
    public void run() {
        while (running) {
            gameRunner.run();
        }
    }

    public void stop() {
        running = false;
    }
}