package Schanppt_Hubi.Structure.Flow;
import Schanppt_Hubi.Structure.InputSimulator;
import Schanppt_Hubi.Structure.Logic.game.GameRunner;
import Schanppt_Hubi.Structure.Main;
import Schanppt_Hubi.Structure.OutputCapture;
import Schanppt_Hubi.Structure.View.MapGUI;
import Schanppt_Hubi.Structure.Map_GUI.core.src.main.java.Schanppt_Hubi.Structure.View.MainMenuScreen;

public class flow {
    private FlowThread flowThread;
    private  backendFlow backendflow;
    private Thread thread;
    private static OutputCapture outputCapture;
    private  Thread backendThread;
    private static GameRunner gameRunner;
    private MainMenuScreen mainMenuScreen;


    public flow(final Main game, OutputCapture outputCapture, InputSimulator inputSimulator)   {
        this.outputCapture = outputCapture;
        gameRunner = new GameRunner(outputCapture);
        startBackendflow();
        startMainMenuScreen(game);
        startFrontendThread(game);
    }

    public void startMainMenuScreen(final Main game) {
        mainMenuScreen = new MainMenuScreen(game);
        game.setScreen(mainMenuScreen);
    }

    public void startFrontendThread(final Main game) {
        flowThread = new FlowThread(game, outputCapture, gameRunner);
        thread = new Thread(flowThread);
        thread.start();
    }
    public void startBackendflow()     {
        backendflow = new backendFlow(outputCapture,gameRunner);
        backendThread = new Thread(backendflow);
        backendThread.start();
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
    public void stopBackendflow() {
        if (backendflow != null) {
            backendflow.stop();
        }
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void getGameRunner(){
        return gameRunner;
    }
}
