
package game;
import java.awt.Color;


public class Hubi {
    private int xCoord;
    private int yCoord;

    public int[] getLocation(){
        int[] location = {xCoord, yCoord};
        return location;
    }

    public void setLocation(int xCoord, int yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    // public void updateLocation(int xNew, int yNew){
    //     xCoord = xNew;
    //     yCoord = yNew;
    // }

    /* players[] = {player1, player2, player3, player4} */
    

    /* Map[] = {r, w, r, w, ...} */
    
}