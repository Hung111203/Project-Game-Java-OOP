package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hubi Game");

        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);

        Button playButton = new Button("Play");
        playButton.setLayoutX(350);
        playButton.setLayoutY(250);
        playButton.setOnAction(event -> {
            System.out.println("Play button clicked!");
            // Add logic to start the game
        });

        root.getChildren().add(playButton);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
