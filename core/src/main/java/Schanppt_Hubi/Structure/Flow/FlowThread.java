package Schanppt_Hubi.Structure.Flow;
import Schanppt_Hubi.Structure.Logic.game.GameRunner;
import Schanppt_Hubi.Structure.Main;
import Schanppt_Hubi.Structure.OutputCapture;
import Schanppt_Hubi.Structure.View.MapGUI;
import com.badlogic.gdx.Gdx;
import java.util.ArrayList;
import java.util.HashMap;

public class FlowThread implements Runnable {
    private volatile boolean running = true;
    private static OutputCapture outputCapture;
    public String returnData;
    public int currentTurnState;

    private int TurnCount;
    private  GameRunner gameRunner;
    private MapGUI mapGUI;
    public FlowThread(final Main game, OutputCapture outputCapture, GameRunner gameRunner) {
        returnData="";
        TurnCount = 0;
        this.gameRunner = gameRunner;
        this.outputCapture = outputCapture;
        mapGUI= new MapGUI(game,this);
        game.setScreen(mapGUI);
        ArrayList<String> validPlayer = gameRunner.getAllPlayersPosition();
        mapGUI.ReArrangePLayer(validPlayer);
    }

    @Override
    public void run() {
        while (running) {
            if (gameRunner.winGame())   {
                System.out.println("Game won");
                mapGUI.textDisplay.displayMessage("Congratulations, you have won the game",10, 0.2f*mapGUI.getScreenWidth(), 0, 0.6f*mapGUI.getScreenWidth(), 0.8f*mapGUI.getScreenHeight());
                stop();
            }

            if (currentTurnState==0)    {
                currentTurnState=1;
                if (!returnData.equals(""))   {
//                    System.out.println(returnData);
                    gameRunner.actionOption(returnData);
                    TurnCount++;
                    if (!outputCapture.getFirstLine().equals("|") && !outputCapture.getFirstLine().equals("_") && !returnData.equalsIgnoreCase("ask")) {
                        System.out.println("Wall added "+outputCapture.getFirstLine());
                        mapGUI.addWall(outputCapture.getFirstLine());
                    }
                }
                else    {
                    //System.out.println("Return data is "+returnData);
                }
            }

            if (!outputCapture.isEmpty())   {
//                System.out.println("line is "+outputCapture.getLine(2));

                if (outputCapture.getFirstLine().equals("asking command"))  {
                    currentTurnState=0;
                    mapGUI.textDisplay.displayMessage(outputCapture.getAllContent(),10, 0.2f*mapGUI.getScreenWidth(), 0, 0.5f*mapGUI.getScreenWidth(), 0.25f*mapGUI.getScreenHeight());
                    mapGUI.nextPlayer();
                    returnData="";
                    outputCapture.clear();
                }
                else if (outputCapture.getLine(1).equals("Sorry, the move is invalid!!!"))   {

                    System.out.println("Sorry, the move is invalid!!!");
                    mapGUI.nextPlayer();
                    outputCapture.clear();
                    returnData="";
                    mapGUI.ButtonEnabled=true;
                }
                else if (outputCapture.getLine(1).equals("The move is valid"))      {
                    currentTurnState=1;
                    if (returnData.equals("Left"))  {
                        mapGUI.leftAnimation();
                    }
                    else if (returnData.equals("Right"))    {
                        mapGUI.rightAnimation();
                    }
                    else if (returnData.equals("Up"))   {
                        mapGUI.upAnimation();
                    }
                    else if (returnData.equals("Down"))     {
                        mapGUI.downAnimation();
                    }
                    returnData="";
                    outputCapture.clear();
                }
                else if (returnData.equals("Magic"))   {
                    currentTurnState=1;
                    returnData="";
                    //System.out.println("No data");
                    if (gameRunner.canFindHubi())   {
                        gameRunner.game.setPhase();
                        gameRunner.findHubiPhase();
                    }
                    outputCapture.clear();
                }

                if (TurnCount>= gameRunner.getHubiMoveThreshold()+5)  {
                    System.out.println("Hubi move thres is "+gameRunner.getHubiMoveThreshold());
                    gameRunner.game.moveHubi();
                    TurnCount=0;
                }
                System.out.println("Exit app");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

        }
    }

    public void stop() {
        running = false;
    }
}
