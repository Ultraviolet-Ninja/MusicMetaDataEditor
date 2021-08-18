module MusicMetaDataEditor.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires mp3agic;

    opens jasmine.jragon to javafx.fxml;
    exports jasmine.jragon;
}