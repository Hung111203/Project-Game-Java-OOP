package Schanppt_Hubi.Structure.Logic.game;
import java.awt.Color;
public class Player extends Animal{
    private int xCoord;
    private int yCoord;

    public Player(Color color, String type){
        super(color, type);
    }

    public void setLocation(int xCoord, int yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }


    public int[] getLocation(){
        int[] location = {xCoord, yCoord};
        return location;
    }

    public void updateLocation(int xNew, int yNew){
        this.xCoord = xNew;
        this.yCoord = yNew;
    }

    /* players[] = {player1, player2, player3, player4} */


    /* Map[] = {r, w, r, w, ...} */

}
