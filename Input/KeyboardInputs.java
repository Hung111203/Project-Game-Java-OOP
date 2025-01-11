package Input;
import game.Game;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import game.Game;

public class KeyboardInputs implements KeyListener {

    private Game game;
    public KeyboardInputs(Game game){
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e){

    };


    @Override
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_RIGHT:
                game.movePlayer("right");
                break;
            case KeyEvent.VK_LEFT:
                game.movePlayer("left");
                break;
            case KeyEvent.VK_UP:
                game.movePlayer("up");
                break;
            case KeyEvent.VK_DOWN:
                game.movePlayer("down");
                break;
        }
        System.out.println("A key is pressed");
    }

    @Override
    public void keyReleased(KeyEvent e){

    };
}
