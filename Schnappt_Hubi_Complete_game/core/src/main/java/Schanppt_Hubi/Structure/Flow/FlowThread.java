/**
 * OOP Java Project  WiSe 2024/2025
 * Author: Manh Thanh Long Nguyen
 * Date: 25/12/2024
 * Final Complete Date: 28/01/2025
 * Description: Control flow of GUI
 * Status: Accepted
 * Method: Threading in Java
 */

package Schanppt_Hubi.Structure.Flow;
import Schanppt_Hubi.Structure.Logic.game.GameRunner;
import Schanppt_Hubi.Structure.Main;
import Schanppt_Hubi.Structure.OutputCapture;
import Schanppt_Hubi.Structure.View.MapGUI;
import com.badlogic.gdx.Gdx;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
public class FlowThread implements Runnable {
    private volatile boolean running = true;
    private static OutputCapture outputCapture;
    public String returnData;
    public int currentTurnState;

    private int TurnCount;
    private int TotalTurn;
    private  GameRunner gameRunner;
    private MapGUI mapGUI;

    private int loseThresHold;
    public FlowThread(final Main game, OutputCapture outputCapture, GameRunner gameRunner) {
        returnData="";
        TurnCount = 0;
        this.gameRunner = gameRunner;
        this.outputCapture = outputCapture;
        mapGUI= new MapGUI(game,this);
        game.setScreen(mapGUI);
        ArrayList<String> validPlayer = gameRunner.getAllPlayersPosition();
        mapGUI.ReArrangePLayer(validPlayer);
        Random random = new Random();
        loseThresHold = 2 * (random.nextInt(8) + 8); // Generates a random number from 8 to 15
        String difficulty = gameRunner.getDifficulty();
        if (difficulty.equals("easy"))    {
            loseThresHold *=4;
        }
        else if (difficulty.equals("medium"))   {
            loseThresHold *=3;
        }
        else    {
            loseThresHold *=2;
        }
        System.out.println(loseThresHold);
        TotalTurn=0;
    }

    @Override
    public void run() {
        int switchPhase=0;
        while (running) {
            if (gameRunner.winGame())   {
                mapGUI.textDisplay.displayMessage("Congratulations, you have won the game",10, 0.2f*mapGUI.getScreenWidth(), 0, 0.6f*mapGUI.getScreenWidth(), 0.8f*mapGUI.getScreenHeight());
                stop();
            }
            if (TotalTurn > loseThresHold)   {
                mapGUI.textDisplay.displayMessage("Sorry, Hubi has won, player lose",10, 0.2f*mapGUI.getScreenWidth(), 0, 0.6f*mapGUI.getScreenWidth(), 0.8f*mapGUI.getScreenHeight());
                stop();
            }
            if (currentTurnState==0)    {
                currentTurnState=1;
                if (!returnData.equals(""))   {
//                    System.out.println(returnData);
                    gameRunner.actionOption(returnData);
                    TurnCount++;
                    TotalTurn++;
                    if (!outputCapture.getFirstLine().equals("|") && !outputCapture.getFirstLine().equals("_") && !returnData.equalsIgnoreCase("ask")) {
                        mapGUI.addWall(outputCapture.getFirstLine());
                    }
                }

            }

            if (!outputCapture.isEmpty())   {
//                System.out.println("line is "+outputCapture.getLine(2));

                if (outputCapture.getFirstLine().equals("Player ask"))  {
                    currentTurnState=0;
                    mapGUI.textDisplay.displayMessage(outputCapture.getAllContent(),7, 0.2f*mapGUI.getScreenWidth(), 0, 0.5f*mapGUI.getScreenWidth(), 0.25f*mapGUI.getScreenHeight());
                    mapGUI.nextPlayer();
                    returnData="";
                    outputCapture.clear();
                }
                else if (outputCapture.getLine(1).equals("Sorry, the move is invalid!!!"))   {
                    mapGUI.textDisplay.displayMessage("Sorry, the move is invalid", 2, 0.2f * mapGUI.getScreenWidth(), 0, 0.5f * mapGUI.getScreenWidth(), 0.25f * mapGUI.getScreenHeight());
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
                if (gameRunner.canFindHubi() && switchPhase==0)   {
                    mapGUI.textDisplay.displayMessage("Now you can find Hubi", 3, 0.2f * mapGUI.getScreenWidth(), 0, 0.5f * mapGUI.getScreenWidth(), 0.25f * mapGUI.getScreenHeight());
                    switchPhase=1;
                }

                if (TurnCount>= gameRunner.getHubiMoveThreshold()+5)  {
                    gameRunner.game.moveHubi();
                    if (gameRunner.canFindHubi()) {
                        mapGUI.textDisplay.displayMessage("Hubi has moved ", 4, 0.2f * mapGUI.getScreenWidth(), 0, 0.5f * mapGUI.getScreenWidth(), 0.25f * mapGUI.getScreenHeight());
                    }
                    TurnCount=0;
                }

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
