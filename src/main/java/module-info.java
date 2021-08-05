module MusicMetaDataEditor.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens jasmine.jragon to javafx.fxml;
    exports jasmine.jragon;
}