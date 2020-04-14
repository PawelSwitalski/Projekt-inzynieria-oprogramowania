package sample;

import java.io.File;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileChoose {
    @FXML
    public Button plik;
    @FXML
    public ListView listview;

    /**
     * wybierz pojedyńczy plik
     */
    public void buttonAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        // otwiera ustalony folder
        //fc.setInitialDirectory(new File("C:\\Users\\Mikoaj\\Videos"));

        // wybór rozszerzenia
        fc.getExtensionFilters().addAll(new ExtensionFilter("Plik mp4", "*.mp4"));
        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null) {
            listview.getItems().add(selectedFile.getAbsolutePath());
        }else {
            System.out.println("File is not valid!");
        }
    }
}