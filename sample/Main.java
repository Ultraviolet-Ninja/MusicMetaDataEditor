package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Music Changer");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
        Alert instructions = new Alert(Alert.AlertType.INFORMATION);
        instructions.setTitle("Instructions");
        instructions.setContentText("");
        instructions.setHeaderText("Pick the music you'd like to edit, then pick " +
                "an output folder to store the output music");
        instructions.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}