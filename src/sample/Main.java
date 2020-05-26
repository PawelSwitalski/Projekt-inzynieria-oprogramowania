package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        new KeyEsc().keyEsc(root);


        primaryStage.setTitle("Drinker");
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("applications.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    //halko
    //halko2
    public static void main(String[] args) {
        launch(args);
    }
}
