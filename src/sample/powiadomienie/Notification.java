package sample.powiadomienie;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Notification{

    private Alert notification = new Alert(Alert.AlertType.INFORMATION);                           //stworzenie okienka pokazującego zasadę

    private int openingTime;                                                                        //zmienna przechwoująca czas otwarcia

    public Notification(int openingTime){
        this.openingTime=openingTime;
    }

    public void showNotification(){
        DialogPane dialogPane = notification.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("notification.css").toExternalForm());               //styl powiadomienia
        notification.setResizable(true);
        notification.getDialogPane().setPrefSize(250, 100);                                    //ustawienie rozmiarów okienka
        notification.setHeaderText(null);
        File logo = new File("src\\media\\logo.png");
        Image image = new Image(logo.toURI().toString());
        ImageView imageView = new ImageView(image);
        notification.setGraphic(imageView);                                                     //wczytanie grafiki do powiadomienia
        notification.setTitle("Zasada");
        notification.setContentText("PIJ!");                                                  //przypisanie zasady do tekstu w okienku
        notification.show();                                                                  //wyświetlenie okienka
    }

    public void closeNotification(int time){
        if(time==openingTime+2){                                                            //zamknięcie okienka po 2 sekundach od pojawienia się
            notification.close();
        }
    }
}
