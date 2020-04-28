package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.*;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;

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
    private AnchorPane buttonPane;


    @FXML
    private Button play;
    @FXML
    private Button pause;
    public Button wybierzPlik;
    public Button start;
    public Button end;
    public Button plus5seconds;
    public Button minus5seconds;

    public Button full_screen;

    public Slider volumeSlider;
    public Slider mediaTimeSlider;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Sciezka do pliku
        String path = new File("src/media/test.mp4").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        //mediaPlayer.play();


        initialVideo();

        /*
        // Nie dziala

        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();

        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        */
    }

    private void initialVideo() {
        /* Używana do ustawienia stanu poczatkowego */
        setMediaView();
        volume_slider();
        time_slider();

        //funkcja do wyświetlania zasad gry
        int startTime = (int) mediaPlayer.getStartTime().toSeconds();
        try { // komentarz
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                Scanner scanner = new Scanner(new File("src/media/rules.txt"));                 //otwarcie pliku z zasadami
                int ruleTime = Integer.parseInt(scanner.nextLine());                                        //pobranie z pliku czasu pierwszej zasady
                String rule = scanner.nextLine();                                                           //pobranie z pliku pierwszej zasady
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    int currentTime = (int) mediaPlayer.getCurrentTime().toSeconds();                   //pobranie aktualnego czasu filmu w sekundach
                    if(currentTime-startTime==ruleTime){                                                //sprawdzenie czy aktualny czas jest równy czasowi następnej zasady
                        System.out.println(rule);                                                       //wyświetlenie zasady
                        if(scanner.hasNext()){                                                    //sprawdzenie czy jest następna zasada
                            ruleTime=Integer.parseInt(scanner.nextLine());                              //pobranie z pliku czasu następnej zasady
                            rule=scanner.nextLine();                                                    //pobranie z pliku następnej zasady
                        }
                        else{                                                                   //jeśli nie
                            scanner.close();                                                            //zamknięcie pliku
                            ruleTime=0;                                                                 //pozbycie się błędu wyświetlania kilka razy ostatniej zasady
                        }
                    }
                }
            });
        } catch (Exception e) {                                                             //obsługa błędu w przypadku braku pliku
            e.printStackTrace();
        }
        //koniec funckji
    }


    private void time_slider() {
        /* Funkcja steruje paskiem czasu pliku*/

        mediaPlayer.currentTimeProperty().addListener((observableValue, duration, t1) -> {
            // Ustawienie wartosci Max jako dlugosc filmu
            // Jest on dany tutaj ponieważ pasek zmienia dlugosc wraz ze zmiana wielkosci okna
            mediaTimeSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
            // Przesuwanie wskaznika względem czasu filmu
            mediaTimeSlider.setValue(t1.toSeconds());

        });

        // Ustawienie czasu filmu poprzez klikniecie na pasek
        mediaTimeSlider.setOnMouseClicked(mouseEvent ->
                mediaPlayer.seek(Duration.seconds(mediaTimeSlider.getValue())));
    }

    private void volume_slider() {
        /* Funkcja ustawia zakres dzwięku od 0 do 100
        *  Sprawzda ona też czy nie jest zmieniany poziom dzwieku */
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);
        volumeSlider.valueProperty().addListener(observable ->
                mediaPlayer.setVolume(volumeSlider.getValue() / 100));
    }

    private void setMediaView() {
        /* Funkcja ustawia wysokosc i szerokosc mediaView */
        mediaView.setFitHeight(mainPane.getMinHeight() - leftPane.getMinHeight());
        mediaView.setFitWidth(mainPane.getMinWidth() - leftPane.getMinWidth());
    }

    public void play_pause(ActionEvent event){

    }

    public void wybierzPlik(ActionEvent event){
        FileChooser fc = new FileChooser();
        // otwiera ustalony folder
       // fc.setInitialDirectory(new File("src/media/test.mp4"));

        // wybór rozszerzenia
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Plik mp4", "*.mp4"));
        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null) {
            media = new Media(new File(selectedFile.getAbsolutePath()).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
        }else {
            System.out.println("File is not valid!");
        }
        initialVideo();

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

    public void start(ActionEvent event){
        /* funkcja ustawia film na start(czas poczatkowy) */
        mediaPlayer.seek(Duration.seconds(0));
    }

    public void end(ActionEvent event){
        /* funkcja ustawia film na koniec */
        mediaPlayer.seek(Duration.seconds(mediaPlayer.getTotalDuration().toSeconds()));
    }

    public void plusFiveSeconds(ActionEvent event){
        /* Przeskakuje film o 5 sekund */
        try {
            mediaPlayer.seek(Duration.seconds(
                    mediaPlayer.getCurrentTime().toSeconds() + 5
            ));
        } catch (Exception e) {
            // Ustawia na koniec filmu
            mediaPlayer.seek(Duration
                    .seconds(mediaPlayer.getTotalDuration().toSeconds()));
        }
    }

    public void minusFiveSeconds(ActionEvent event){
        /* Cofa film o 5 sekund lub mniej */
        try {
            mediaPlayer.seek(Duration.seconds(
                    mediaPlayer.getCurrentTime().toSeconds() - 5
            ));
        } catch (Exception e) {
            // Ustawia na poczatek filmu
            mediaPlayer.seek(Duration.seconds(0));
        }

    }



    public void fullScreen(ActionEvent event){
        /* Ustawia tryb pelnoekranowy */


        ((Stage)mediaView.getScene().getWindow()).setFullScreen(true);


        leftPane.setVisible(false);
        buttonPane.setVisible(false);


        // Ustawienie mediaPane tak by niezabieralo miejsca
        double mediaPaneLayoutX = mediaPane.getLayoutX();
        mediaPane.setLayoutX(0);
        // Przesuniecie mediaView na miejsce mediaPane
        mediaView.setX(-mediaPaneLayoutX);

        System.out.println("\nbuttonPane.getLayoutX()" + buttonPane.getLayoutX() +
                "\nbuttonPane.getLayoutY()" + buttonPane.getLayoutY() +
                "\nbuttonPane.getScaleY()" + buttonPane.getScaleY() +
                "\nbuttonPane.getTranslateY()" + buttonPane.getTranslateY());

        buttonPane.setLayoutY(0);





        // Ustawienie mediaPane i mediaView na cala rozdzielczosc
        mediaPane.setPrefSize(mainPane.getWidth(), mainPane.getHeight());
        mediaView.setFitHeight(mainPane.getHeight());
        mediaView.setFitWidth(mainPane.getWidth());

        System.out.println( "\nmainPane.getLayoutX()" + mainPane.getLayoutX() +
                            "\nmainPane.getHeight()" + mainPane.getHeight() +
                            "\nmainPane.getMaxHeight()" + mainPane.getMaxHeight() );


    }



}
