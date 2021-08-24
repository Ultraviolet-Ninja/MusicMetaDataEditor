module MusicMetaDataEditor.main {
    requires transitive javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires mp3agic;

    opens jasmine.jragon to javafx.fxml;
    exports jasmine.jragon;
}