package sample;

import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class KeyEsc {
    /*     To tylko wypisuje w konsoli,    NIE Jest do niczego potrzebne    */
    public void keyEsc(Parent root) {
        final Stage stage = new Stage();
        root.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                //System.out.println("Key Pressed: " + ke.getCode());
                stage.close();
            }
        });
    }
}
