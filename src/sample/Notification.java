package sample;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Notification {
    public Notification(){
        Alert notification = new Alert(Alert.AlertType.INFORMATION);                           //stworzenie okienka pokazującego zasadę
        notification.setResizable(true);
        notification.getDialogPane().setPrefSize(250, 100);                                    //ustawienie rozmiarów okienka
        notification.setHeaderText(null);
        File logo = new File("D:\\Programowanie\\Projekt-inzynieria-oprogramowania\\src\\media\\logo.png");
        Image image = new Image(logo.toURI().toString());
        ImageView imageView = new ImageView(image);
        notification.setGraphic(imageView);
        notification.setTitle("Zasada");
        notification.setContentText("PIJ!");                                                  //przypisanie zasady do tekstu w okienku
        notification.show();                                                                   //wyświetlenie okienka
    }
}
