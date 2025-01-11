package game;
import javax.swing.JFrame;

public class GameWindow {
    private JFrame jframe;
    public GameWindow(GamePanel gamePanel){
        jframe = new JFrame();
        jframe.setSize(400,400);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }
}

// package game;

// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.stage.Stage;

// public class GameWindow extends Application {

//     @Override
//     public void start(Stage primaryStage) {
//         primaryStage.setTitle("Hubi Game");

//         GameRunner gameRunner = new GameRunner(primaryStage);
//         gameRunner.run();
//     }

//     public static void main(String[] args) {
//         launch(args);
//     }
// }