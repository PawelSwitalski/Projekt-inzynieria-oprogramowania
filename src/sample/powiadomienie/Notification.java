package sample.powiadomienie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Notification extends Application implements Runnable{
    private Thread thread;
    private Alert notification;
    private int a = 0;
    public Stage stage;

    /*
    public Notification() throws IOException {

        try {
            start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.stage.show();

        /*

        Alert notification = new Alert(Alert.AlertType.INFORMATION);                           //stworzenie okienka pokazującego zasadę
        notification.setResizable(true);
        notification.getDialogPane().setPrefSize(250, 100);                                    //ustawienie rozmiarów okienka
        notification.setHeaderText(null);
        File logo = new File("src\\media\\logo.png");
        Image image = new Image(logo.toURI().toString());
        ImageView imageView = new ImageView(image);
        notification.setGraphic(imageView);
        notification.setTitle("Zasada");
        notification.setContentText("PIJ!");                                                  //przypisanie zasady do tekstu w okienku
        notification.show();                                                                  //wyświetlenie okienka

        */






        /*
        Alert notification = FXMLLoader.load(getClass().getResource("sample.fxml"));

        this.notification = notification;
        this.thread = new Thread(this);
        thread.start();
        //this.notification = notification;


        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notification.hide();

        */



    public Notification(){

        /*
        try {
            start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */


        //this.thread = new Thread(this);
        //thread.start();


        /*
        System.out.println(stage.getUserData());

        for (int i = 0; i < 9999999; i++) {
            a = 1 + i/333;
            System.out.print("");

        }*/


    }



    @Override
    public void start(Stage stage) throws Exception {
        Parent notification = FXMLLoader.load(getClass().getResource("notification.fxml"));
        //stage.setTitle("Title powiadomienie");
        Scene scene = new Scene(notification);
        scene.getStylesheets().add(getClass().getResource("notification.css").toExternalForm());
        stage.setScene(scene);
        this.stage = stage;




    }

    @Override
    public void run() {        // z tymi watkami to cos nie wychodzi
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    @Override
    public void run() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(notification.isShowing());

        //this.notification.hide();
        //notification.hide();

    }
    */
    public static void main(String[] args) {
        /* Potrzebne do sprawdzania kodu, czy działa okno */
        launch(args);
}
}
