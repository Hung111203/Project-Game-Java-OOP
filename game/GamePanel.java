package game;

import java.awt.Graphics;
import java.awt.Polygon;
import javax.swing.JPanel;
import Input.MouseInputs;

public class GamePanel extends JPanel{
    private Polygon rightArrow;
    private Polygon leftArrow;
    private Polygon upArrow;
    private Polygon downArrow;
    private MouseInputs mouseInputs;

    public GamePanel(){
        mouseInputs = new MouseInputs();
        addMouseListener(mouseInputs);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int centerX = panelWidth / 2;
        int centerY = panelHeight / 2;

        // Draw right arrow
        int[] xRight = {centerX + 20, centerX + 40, centerX + 20};
        int[] yRight = {centerY - 10, centerY, centerY + 10};
        Polygon rightArrow = new Polygon(xRight, yRight, 3);
        g.fillPolygon(rightArrow);

        // Draw left arrow
        int[] xLeft = {centerX - 20, centerX - 40, centerX - 20};
        int[] yLeft = {centerY - 10, centerY, centerY + 10};
        Polygon leftArrow = new Polygon(xLeft, yLeft, 3);
        g.fillPolygon(leftArrow);

        // Draw up arrow
        int[] xUp = {centerX - 10, centerX, centerX + 10};
        int[] yUp = {centerY - 20, centerY - 40, centerY - 20};
        Polygon upArrow = new Polygon(xUp, yUp, 3);
        g.fillPolygon(upArrow);

        // Draw down arrow
        int[] xDown = {centerX - 10, centerX, centerX + 10};
        int[] yDown = {centerY + 20, centerY + 40, centerY + 20};
        Polygon downArrow = new Polygon(xDown, yDown, 3);
        g.fillPolygon(downArrow);
    }

    public String getArrowDirection(int x, int y){
        if(rightArrow.contains(x,y)){
            return "right";
        }else if(leftArrow.contains(x,y)){
            return "left";
        }else if(upArrow.contains(x,y)){
            return "up";
        }else if(downArrow.contains(x,y)){
            return "down";
        }
        return null;
    }
}


// package game;

// import javafx.scene.layout.Pane;
// import javafx.scene.shape.Polygon;
// import javafx.scene.input.MouseEvent;

// public class GamePanel extends Pane {
//     private Polygon rightArrow;
//     private Polygon leftArrow;
//     private Polygon upArrow;
//     private Polygon downArrow;
//     private GameRunner gameRunner;

//     public GamePanel(GameRunner gameRunner) {
//         setPrefSize(800, 600);

//         int centerX = 400;
//         int centerY = 300;

//         // Draw right arrow
//         rightArrow = new Polygon(centerX + 20, centerY - 10, centerX + 40, centerY, centerX + 20, centerY + 10);
//         getChildren().add(rightArrow);

//         // Draw left arrow
//         leftArrow = new Polygon(centerX - 20, centerY - 10, centerX - 40, centerY, centerX - 20, centerY + 10);
//         getChildren().add(leftArrow);

//         // Draw up arrow
//         upArrow = new Polygon(centerX - 10, centerY - 20, centerX, centerY - 40, centerX + 10, centerY - 20);
//         getChildren().add(upArrow);

//         // Draw down arrow
//         downArrow = new Polygon(centerX - 10, centerY + 20, centerX, centerY + 40, centerX + 10, centerY + 20);
//         getChildren().add(downArrow);

//         setOnMouseClicked(this::handleMouseClick);
//     }

//     private void handleMouseClick(MouseEvent event) {
//         double x = event.getX();
//         double y = event.getY();

//         if (rightArrow.contains(x, y)) {
//             gameRunner.movePlayer("right");
//         } else if (leftArrow.contains(x, y)) {
//             gameRunner.movePlayer("left");
//         } else if (upArrow.contains(x, y)) {
//             gameRunner.movePlayer("up");
//         } else if (downArrow.contains(x, y)) {
//             gameRunner.movePlayer("down");
//         }
//     }
// }