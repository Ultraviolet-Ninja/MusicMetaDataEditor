package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ProgressBar;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Controller {
    @FXML
    private ProgressBar bar;

    @FXML
    private Label progression, warning;

    @FXML
    private TextArea originalName;

    @FXML
    private TextField album, artist, name, year;

    @FXML
    private Button saving, toNext, toPrevious;

    private int iterator = 0, loadedMusic = 0;
    private double size,
            count = 1;
    private boolean previousEnabled = false,
            nextEnabled = false;
    private char backslash = 92;
    private Stage stage = new Stage();
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Boolean> existingId3v = new ArrayList<>();
    private ArrayList<Mp3File> fileList = new ArrayList<>();
    private ArrayList<ID3v1> ids = new ArrayList<>();
    private FileChooser chooser = new FileChooser();
    private DirectoryChooser direct = new DirectoryChooser();
    private Path outputDirectory;

    @FXML
    private void load(){
        if (loadedMusic > 0){
            warning.setText("");
        }

        chooser.setTitle("Music Selector");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));

        try {
            for (File file : chooser.showOpenMultipleDialog(stage)) {
                fileList.add(new Mp3File(file));
            }

            direct.setTitle("Output Directory");
            outputDirectory = direct.showDialog(stage).toPath();

            for (Mp3File mp3 : fileList) {
                if (mp3.getFilename().substring(0,
                        mp3.getFilename().lastIndexOf(backslash))
                        .equals(outputDirectory.toString())) {
                    throw new IllegalArgumentException();
                }
            }

            for (Mp3File mp3 : fileList){
                if (mp3.hasId3v1Tag()){
                    ids.add(mp3.getId3v1Tag());
                    existingId3v.add(true);
                } else {
                    ids.add(new ID3v1Tag());
                    existingId3v.add(false);
                }
            }

            size = fileList.size() - 1;
            String fullName = fileList.get(iterator).getFilename();
            originalName.setText(fullName.substring(fullName.lastIndexOf(backslash) + 1));
            progression.setText((iterator + 1) + " of " + (fileList.size()) + " songs selected.");

            if (fileList.size() == 1){
                saving.disableProperty().setValue(false);
            }

            if (fileList.size() != 1){
                toNext.disableProperty().setValue(false);
            }
        } catch (IOException io){
            System.err.println("IO Exception");
        } catch (UnsupportedTagException tag){
            System.err.println("Unsupported Tag");
        } catch (InvalidDataException data){
            System.err.println("Invalid Data");
        } catch (NullPointerException empty){
            System.out.println("Null");
        } catch (IllegalArgumentException illegal){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("");
            alert.showAndWait();
        }

        loadedMusic++;
    }

    @FXML
    private void saveSong(){
        setStats();
        previousEnabled = false;

            try {
                for (int i = 0; i < fileList.size(); i++){
                    fileList.get(i).setId3v1Tag(ids.get(i));
                    fileList.get(i).save(outputDirectory.toString() + backslash
                            + titles.get(i));
                }

                iterator = 0;
                count = 1;
                saving.disableProperty().setValue(true);
                toPrevious.disableProperty().setValue(true);
                clearMem();
                warning.setText("Save Successful!");
            } catch (IOException io){
                warning.setText("IO Exception");
            } catch (NotSupportedException unsupported){
                warning.setText("Unsupported Format");
            } catch (IllegalArgumentException illegal){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("New Path unspecified");
                alert.setHeaderText("Cannot have the new path be the same as the original");
                alert.setContentText("Change the path");
                alert.showAndWait();
            }
    }

    @FXML
    private void nextSong(){
        setStats();
        iterator++;
        count++;
        songChange();

        if (!previousEnabled){
            toPrevious.disableProperty().setValue(false);
            previousEnabled = true;
        }

        if (!notFull()){
            toNext.disableProperty().setValue(true);
            warning.setText("Press 'Save Song' to save the list of songs where they found.");
            nextEnabled = false;
            saving.disableProperty().setValue(false);
        }
    }

    @FXML
    private void previousSong(){
        iterator--;
        count--;
        warning.setText("");
        songChange();

        if (!nextEnabled){
            toNext.disableProperty().setValue(false);
            nextEnabled = true;
            saving.disableProperty().setValue(true);
        }

        if (!notEmpty()){
            toPrevious.disableProperty().setValue(true);
            previousEnabled = false;
        }

    }

    private boolean notFull(){
        return iterator != fileList.size() - 1;
    }

    private boolean notEmpty(){
        return iterator != 0;
    }

    private void songChange(){
        originalName.setText(fileList.get(iterator).getFilename()
                .substring(fileList.get(iterator).getFilename().lastIndexOf(backslash) + 1));
        bar.setProgress(count / size);
        progression.setText((iterator + 1) + " of " + (fileList.size()) + " songs selected.");

        if (existingId3v.get(iterator)){
            artist.setText(ids.get(iterator).getArtist());
            album.setText(ids.get(iterator).getAlbum());
            name.setText(ids.get(iterator).getTitle());
            year.setText(ids.get(iterator).getYear());
        } else {
            album.setText("");
            artist.setText("");
            name.setText("");
            year.setText("");
        }
    }

    private void setStats(){
        ids.get(iterator).setArtist(artist.getText());
        ids.get(iterator).setAlbum(album.getText());
        ids.get(iterator).setTitle(name.getText());
        ids.get(iterator).setYear(year.getText());
        titles.add(iterator, originalName.getText());
        existingId3v.add(iterator, true);
    }

    private void clearMem(){
        fileList.clear();
        ids.clear();
        titles.clear();
        artist.setText("");
        album.setText("");
        name.setText("");
        year.setText("");
    }
}
