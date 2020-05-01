/*

        Klasa potrzebna do zapamietania poczatkowych rozmiarow okna.


*/




package sample;

import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

public class SceneParameter {
    static double mediaPaneLayoutX;
    static double mediaPaneLayoutY;
    static double mediaPanePrefWidth;
    static double mediaPanePrefHeight;

    static double mediaViewX;
    static double mediaViewY;
    static double mediaViewFitHeight;
    static double mediaViewFitWidth;

    static double buttonPaneLayoutY;


    public SceneParameter(AnchorPane mainPane,
                          AnchorPane mediaPane,
                          AnchorPane leftPane,
                          AnchorPane buttonPane,
                          MediaView mediaView){
        /* Zbieramy dane o obecnym oknie */


        mediaPaneLayoutX = mediaPane.getLayoutX();
        mediaPaneLayoutY = mediaPane.getLayoutY();
        mediaPanePrefWidth = mediaPane.getPrefWidth();
        mediaPanePrefHeight = mediaPane.getPrefHeight();

        mediaViewX = mediaView.getX();
        mediaViewY = mediaView.getY();
        mediaViewFitHeight = mediaView.getFitHeight();
        mediaViewFitWidth = mediaView.getFitWidth();

        buttonPaneLayoutY = buttonPane.getLayoutY();
    }

    public SceneParameter(){}

    public static void setMediaPane(AnchorPane mediaPane){
        /* Ustawia poczatkowe parametry mediaPane */
        mediaPane.setLayoutX(SceneParameter.mediaPaneLayoutX);
        mediaPane.setLayoutY(SceneParameter.mediaPaneLayoutY);
        mediaPane.setPrefHeight(mediaPanePrefHeight);
        mediaPane.setPrefWidth(mediaPanePrefWidth);
    }

    public static void setMediaView(MediaView mediaView){
        /* Ustawia poczatkowe parametry mediaView */
        mediaView.setX(SceneParameter.mediaViewX);
        mediaView.setY(SceneParameter.mediaViewY);
        mediaView.setFitHeight(mediaViewFitHeight);
        mediaView.setFitWidth(mediaViewFitWidth);
    }

    public static void setButtonPane(AnchorPane buttonPane){
        /* Ustawia poczatkowe parametry buttonPane */
        buttonPane.setLayoutY(buttonPaneLayoutY);
        buttonPane.setVisible(true);
    }


}
