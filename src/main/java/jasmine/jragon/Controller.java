package jasmine.jragon;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import jasmine.jragon.facade.FacadeFX;
import jasmine.jragon.music.MusicRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    private final MusicRepository repository;
    private final Stage chooserStage;

    public Controller() {
        repository = new MusicRepository();
        chooserStage = new Stage();
    }

    @FXML
    private void loadMusic(){
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Music Selector");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("MP3 Files", "*.mp3")
        );

        try {
            repository.loadMusic(fileChooser.showOpenMultipleDialog(chooserStage));
            chooseOutputDirectory();
        } catch (IOException ioex) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, "General IOException occurred");
        } catch (InvalidDataException | UnsupportedTagException exception) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, "Invalid Data");
        }
    }

    private void chooseOutputDirectory(){
        
    }
}
