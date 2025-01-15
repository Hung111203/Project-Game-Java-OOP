package Schanppt_Hubi.Structure.Flow;
import Schanppt_Hubi.Structure.Logic.game.GameRunner;
import Schanppt_Hubi.Structure.Main;
import Schanppt_Hubi.Structure.OutputCapture;
import Schanppt_Hubi.Structure.View.MapGUI;

import java.util.ArrayList;
import java.util.HashMap;

public class FlowThread implements Runnable {

    private volatile boolean running = true;
    private static OutputCapture outputCapture;
    public String returnData;
    public int currentTurnState;

    private  GameRunner gameRunner;
    private MapGUI mapGUI;
    public FlowThread(final Main game, OutputCapture outputCapture, GameRunner gameRunner) {
        this.gameRunner = gameRunner;
        this.outputCapture = outputCapture;
        mapGUI= new MapGUI(game,this);
        game.setScreen(mapGUI);
    }

    @Override
    public void run() {

        while (running) {

            if (currentTurnState==0)    {
                if (!returnData.equals(""))   {
                    System.out.println(returnData);
                    gameRunner.actionOption(returnData);

                }
                else    {
                    System.out.println("Return data is "+returnData);
                }
                mapGUI.ButtonEnabled=true;
            }

            if (!outputCapture.isEmpty())   {
                System.out.println("line is "+outputCapture.getLine(2));
                if (outputCapture.getFirstLine().equals("ChoosePlayer"))   {
                    String FirstPlayer = outputCapture.getLine(1);
                    ArrayList<String> validPlayer = new ArrayList<>();
                    for (int i=1;i<=outputCapture.getSize()-1;i++)     {
                        validPlayer.add(outputCapture.getLine(i));
                    }
                    mapGUI.ReArrangePLayer(validPlayer);
                    outputCapture.clear();
                    System.out.println("\nfirst player is "+FirstPlayer);
                }

                else if (outputCapture.getLine(1).equals("Sorry, the move is invalid!!!"))   {
                    mapGUI.nextPlayer();
                    outputCapture.clear();
                    returnData="";
                }
                else if (outputCapture.getLine(1).equals("The move is valid"))      {
                    System.out.println("This line used");
                    mapGUI.addWall();
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
                    outputCapture.clear();
                    returnData="";
                    currentTurnState=1;
                }
                else    {
                    System.out.println("No data");
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
