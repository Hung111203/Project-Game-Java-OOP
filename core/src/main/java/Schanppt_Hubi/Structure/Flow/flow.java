package Schanppt_Hubi.Structure.Flow;

import Schanppt_Hubi.Structure.Logic.game.GameRunner;
import Schanppt_Hubi.Structure.Main;
import Schanppt_Hubi.Structure.OutputCapture;
import Schanppt_Hubi.Structure.View.MainMenuScreen;



public class flow {
    private FlowThread flowThread;
    private backendFlow backendflow;
    private Thread thread;
    private static OutputCapture outputCapture;
    private Thread backendThread;
    private static GameRunner gameRunner;
    private MainMenuScreen mainMenuScreen;

    public flow(final Main game, OutputCapture outputCapture) {
        this.outputCapture = outputCapture;
        gameRunner = new GameRunner(outputCapture);
        startMainMenuScreen(game);
//        startBackendflow();
//        startFrontendThread(game);
    }

    public void startMainMenuScreen(final Main game) {
        mainMenuScreen = new MainMenuScreen(game, this);
        game.setScreen(mainMenuScreen);
    }

    public void startFrontendThread(final Main game) {
        flowThread = new FlowThread(game, outputCapture, gameRunner);
        thread = new Thread(flowThread);
        thread.start();
    }

    public void startBackendflow() {
        gameRunner.run();
    }

    public void stopFrontEndThread() {
        if (flowThread != null) {
            flowThread.stop();
        }
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    public GameRunner getGameRunner(){
        return gameRunner;
    }
}
