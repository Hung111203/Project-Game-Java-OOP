package Input;

import java.awt.event.MouseListener;
import game.Game;
import game.GamePanel;

import java.awt.event.MouseEvent;


public class MouseInputs implements MouseListener {
    private Game game;
    private GamePanel gamePanel;
    
    // public MouseInputs(Game game, GamePanel gamePanel){
    //     this.game = game;
    //     this.gamePanel = gamePanel;
    // }

    public void mouseClicked(MouseEvent e){
        // int x = e.getX();
        // int y = e.getY();
        // String direction = gamePanel.getArrowDirection(x,y);
        // if (direction != null){
        //     game.movePlayer(direction);
        // }
        System.out.println("Mouse is clicked!!!");
    }

    
    public void mousePressed(MouseEvent e){

    }

    
    public void mouseReleased(MouseEvent e){

    }

    public void mouseEntered(MouseEvent e){

    }

    public void mouseExited(MouseEvent e){

    }
}
