package jasmine.jragon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("main_display.fxml")));
        primaryStage.setTitle("Music MetaData Editor");
        primaryStage.setScene(new Scene(root, 1000, 625));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
