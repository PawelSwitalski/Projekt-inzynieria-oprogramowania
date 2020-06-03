package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.*;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.powiadomienie.Notification;

import javax.swing.event.MouseInputListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.Semaphore;


public class Controller implements Initializable {
    public BorderPane borderPane;
    File glosnik = new File("src\\media\\g.jpg");
    Image imageGlosnik = new Image(glosnik.toURI().toString());
    public Button ONandOFFSound;


    /* Zmienne do urytych przyciskow */
    public Button playH;
    public Button pauseH;
    public Slider volumeSliderH;
    public Slider mediaTimeSliderH;
    public Button startH;
    public Button endH;
    public Button plus5secondsH;
    public Button minus5secondsH;
    public TextFlow myTextFlow;
    @FXML
    protected AnchorPane hideButtonPane;





    @FXML
    protected MenuBar menuBar;

    protected String fileName;
    @FXML
    protected ScrollPane fileList;
    @FXML
    protected MediaView mediaView;
    protected MediaPlayer mediaPlayer;
    protected Media media;

    @FXML
    protected AnchorPane mainPane;
    @FXML
    protected AnchorPane mediaPane;
    @FXML
    protected AnchorPane leftPane;
    @FXML
    protected AnchorPane buttonPane;


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

    public boolean playState;

    private double actualSoundValue;

    private Semaphore semaphore = new Semaphore(1);

    /* Odpowiada za nasluchiwanie myszy */
    protected EventHandler<MouseEvent>eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {


            if (((Stage)mediaView.getScene().getWindow()).isFullScreen()){
                //System.out.println("siemka pelen ekran");

                hideButtonPane.setVisible(true);


            }
            else {
                //System.out.println("czesc okno");

                hideButtonPane.setVisible(false);



            }

        }
    };





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sciezka do pliku
        String path = new File("src/media/test.mp4").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        fileName=path;
        playState = false;
        Text text = new Text("Witaj w aplikajci Drinker, nasza aplikacja powstała z myślą o usprawininiu popularnych drinking games. Idea jest prosta, pijesz gdy film spełnia odpowiednie ustalone wcześniej zasady. A dzięki naszemu programowi nie musisz się martwić o pamiętaniu wszystkich zasad bo zostaniesz poinformowany w odpowiednim momencie. \nProgram został stworzony przez:\n Pawła Świtalskiego,\n Bartosza Pietrzczyka,\n Mikołaja Mosonia,\n Mateusza Musiała");
        myTextFlow.getChildren().add(text);



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

        // ustawienie obrazka glosnika na przycisku
        ONandOFFSound.setGraphic(new ImageView(imageGlosnik));

        // Na poczatku wylaczamy hideButtonPane
        hideButtonPane.setVisible(false);
        // Zczytuje czy porusza sie mysz nad mediaView
        mainPane.addEventHandler(MouseEvent.MOUSE_MOVED, eventHandler);

        //funkcja do wyświetlania zasad gry
        fileName=fileName+".txt";
        try {
            ArrayList<Integer> notificationsTime = new ArrayList<>();                               //utworzenie listy na czasy wyświetlania zasad
            Scanner scanner = new Scanner(new File(fileName));                                      //otwarcie pliku z zasadami
            int i=0;                                                                                //utworzenie zmiennej pomocniczej
            while(scanner.hasNext()){                                                               //pętla do dodawania czasów zasad do listy
              notificationsTime.add(scanner.nextInt());
              System.out.println(notificationsTime.get(i));
              i++;
            }
            scanner.close();
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<>() {
                int startTime = (int) mediaPlayer.getStartTime().toSeconds();                           //ustawienie czasu początku filmu
                int rule=0;                                                                               //zmienna pomocnicza
                int oldTime=startTime;                                                                      //zmienna pomocnicza by nie dublować powiadomień w liście
                ArrayList<Notification> notificationsList = new ArrayList<>();                   //utworzenie listy z powiadomieniami
                int ruleTime = notificationsTime.get(0);                                       //pobranie z pliku czasu pierwszej zasady
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    if (hideButtonPane.isVisible()) // Wygaszanie hideButtonPane
                        if (!semaphore.hasQueuedThreads()) // jesli nie ma kolejki watkow
                            new Wygaszanie(semaphore, hideButtonPane);

                    int currentTime = (int) mediaPlayer.getCurrentTime().toSeconds();                   //pobranie aktualnego czasu filmu w sekundach
                    for(Integer ruleTime:notificationsTime){                                                            //przeszukanie całej listy czasów w poszukiwaniu odpowiedniej zasady
                        if ((currentTime - startTime == ruleTime) && oldTime!=currentTime) {                              //sprawdzenie czy aktualny czas jest równy czasowi następnej zasady oraz czy się nie powtarza
                            rule++;
                            oldTime=currentTime;                                                            //przypisanie do zmiennej pomocniczej aktualnego czasu
                            Notification notification = new Notification(currentTime);                      //utworzenie nowego powiadomienia
                            notificationsList.add(notification);                                             //dodanie powiadomienia do listy
                            notificationsList.get(rule-1).showNotification();                              //pokazanie powiadomienia
                            break;
                        }
                    }
                    for(int i=0; i<notificationsList.size(); i++){                                   //przejrzenie całej listy
                        notificationsList.get(i).closeNotification(currentTime);                     //zamknięcie powiadomienia
                    }
                }
            });
        } catch (Exception e) {                                                             //obsługa błędu w przypadku braku pliku
            e.printStackTrace();
        }
        //koniec funckji


        new SceneParameter(mainPane, mediaPane, leftPane, buttonPane, mediaView, menuBar);
    }


    public void smallScene(){
        SceneParameter.setMediaPane(mediaPane);
        SceneParameter.setMediaView(mediaView);
        SceneParameter.setButtonPane(buttonPane);
        SceneParameter.setMainPane(mainPane);
        SceneParameter.setMenuBar(menuBar);
        leftPane.setVisible(true);
        hideButtonPane.setVisible(false);

        // Do szybkiej aktualizacji mediaTimeSlider
        soundONandOFF(new ActionEvent());
        soundONandOFF(new ActionEvent());
    }


    private void time_slider() {
        /* Funkcja steruje paskiem czasu pliku*/

        mediaPlayer.currentTimeProperty().addListener((observableValue, duration, t1) -> {
            // Ustawienie wartosci Max jako dlugosc filmu
            // Jest on dany tutaj ponieważ pasek zmienia dlugosc wraz ze zmiana wielkosci okna
            mediaTimeSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
            // To samo co linike wyzej dla slidera pelnoekranowego
            mediaTimeSliderH.setMax(mediaPlayer.getTotalDuration().toSeconds());
            // Przesuwanie wskaznika względem czasu filmu
            mediaTimeSlider.setValue(t1.toSeconds());
            // To samo co linike wyzej dla slidera pelnoekranowego
            mediaTimeSliderH.setValue(t1.toSeconds());

        });

        // Ustawienie czasu filmu poprzez klikniecie na pasek
        mediaTimeSlider.setOnMouseClicked(mouseEvent ->
                mediaPlayer.seek(Duration.seconds(mediaTimeSlider.getValue())));

        // Ustawienie czasu filmu poprzez klikniecie na pasek
        mediaTimeSliderH.setOnMouseClicked(mouseEvent ->
                mediaPlayer.seek(Duration.seconds(mediaTimeSliderH.getValue())));
    }

    private void volume_slider() {
        /* Funkcja ustawia zakres dzwięku od 0 do 100
         *  Sprawzda ona też czy nie jest zmieniany poziom dzwieku */
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);
        volumeSlider.valueProperty().addListener(observable ->
                mediaPlayer.setVolume(volumeSlider.getValue() / 100));

        // To samo dla volumeSliderH
        volumeSliderH.setValue(mediaPlayer.getVolume() * 100);
        volumeSliderH.valueProperty().addListener(observable ->
                mediaPlayer.setVolume(volumeSliderH.getValue() / 100));
    }

    private void setMediaView() {
        /* Funkcja ustawia wysokosc i szerokosc mediaView */
        mediaView.setFitHeight(mainPane.getMinHeight() - leftPane.getMinHeight());
        mediaView.setFitWidth(mainPane.getMinWidth() - leftPane.getMinWidth());
    }


    public void wybierzPlik(){
        FileChooser fc = new FileChooser();
        // otwiera ustalony folder
        // fc.setInitialDirectory(new File("src/media/test.mp4"));

        // wybór rozszerzenia
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Plik mp4", "*.mp4"));
        File selectedFile = fc.showOpenDialog(null);
        System.out.println(selectedFile.getAbsolutePath());

        if(selectedFile != null) {
            media = new Media(new File(selectedFile.getAbsolutePath()).toURI().toString());
            fileName=selectedFile.getAbsolutePath();
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
        }else {
            System.out.println("File is not valid!");
        }
        initialVideo();
    }

    // potrzebne żeby skrót do odtwarznia działał -Mikołaj
    public void play_pause(ActionEvent event){
        if(playState){
            pause(event);
            playState=false;
        }
        else{
            play(event);
            playState=true;
        }
    }
    public void play(ActionEvent event){
        /* funkcja do wlaczenia wideo */
        mediaPlayer.play();
        play.setVisible(false);
        pause.setVisible(true);

        // dla pelnoekranowych
        playH.setVisible(false);
        pauseH.setVisible(true);
    }

    public void pause(ActionEvent event){
        /* funkzja do zatrzymania wideo */
        mediaPlayer.pause();
        pause.setVisible(false);
        play.setVisible(true);

        // dla pelnoekranowych
        pauseH.setVisible(false);
        playH.setVisible(true);
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


        menuBar.setVisible(false);
        menuBar.setMinHeight(0);
        menuBar.setPrefHeight(0);


        mainPane.setStyle("-fx-background-color: black");


        leftPane.setVisible(false);
        buttonPane.setVisible(false);


        // Ustawienie mediaPane tak by niezabieralo miejsca
        double mediaPaneLayoutX = mediaPane.getLayoutX();
        mediaPane.setLayoutX(0);
        // Przesuniecie mediaView na miejsce mediaPane
        mediaView.setX(-mediaPaneLayoutX);

        /*
        System.out.println("\nbuttonPane.getLayoutX()" + buttonPane.getLayoutX() +
                "\nbuttonPane.getLayoutY()" + buttonPane.getLayoutY() +
                "\nbuttonPane.getScaleY()" + buttonPane.getScaleY() +
                "\nbuttonPane.getTranslateY()" + buttonPane.getTranslateY());
         */

        buttonPane.setLayoutY(0);



        // Ustawienie mediaPane i mediaView na cala rozdzielczosc
        mediaPane.setPrefSize(mainPane.getWidth(), mainPane.getHeight());
        mediaView.setFitHeight(borderPane.getHeight());
        mediaView.setFitWidth(borderPane.getWidth());


        /*
        System.out.println( "\nmainPane.getLayoutX()" + mainPane.getLayoutX() +
                "\nmainPane.getHeight()" + mainPane.getHeight() +
                "\nmainPane.getMaxHeight()" + mainPane.getMaxHeight() );
         */


        // W hideButtonPane można ustawic raz szerokosc.
        // Przy zmianie na tryb okinkowy nie trzeba sie przejmowac jak bedzie wygladal.
        hideButtonPane.setPrefWidth(mediaView.getFitWidth());

        //mediaView.setY(60); // dla 16:10


        // Do szybkiej aktualizacji mediaTimeSliderH
        soundONandOFF(new ActionEvent());
        soundONandOFF(new ActionEvent());

    }


    public void soundONandOFF(ActionEvent event) {
        /* Funkcja odpowiada za przycisk natychmiastowego wylaczenia dzwieku */

        if (mediaPlayer.getVolume() * 100 == 0)
            mediaPlayer.setVolume(actualSoundValue);
        else {
            actualSoundValue = mediaPlayer.getVolume();
            mediaPlayer.setVolume(0);
        }

        // potrzebne do zaktualizowania slider
        volume_slider();

    }


}

