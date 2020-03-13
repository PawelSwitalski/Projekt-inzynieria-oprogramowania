package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javafx.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ScrollPane fileList;
    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Media media;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane mediaPane;
    @FXML
    private AnchorPane leftPane;


    @FXML
    private Button play;
    @FXML
    private Button pause;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Sciezka do pliku
        String path = new File("src/media/test.mp4").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        //mediaPlayer.play();



        setMediaView();

        /*
        // Nie dziala

        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();

        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        */
    }

    private void setMediaView() {
        /* Funkcja ustawia wysokosc i szerokosc mediaView */
        mediaView.setFitHeight(mainPane.getMinHeight() - leftPane.getMinHeight());
        mediaView.setFitWidth(mainPane.getMinWidth() - leftPane.getMinWidth());
    }

    public void play_pause(ActionEvent event){

    }


    public void play(ActionEvent event){
        /* funkcja do wlaczenia wideo */
        mediaPlayer.play();
        play.setVisible(false);
        pause.setVisible(true);
    }

    public void pause(ActionEvent event){
        /* funkzja do zatrzymania wideo */
        mediaPlayer.pause();
        pause.setVisible(false);
        play.setVisible(true);
    }

}
