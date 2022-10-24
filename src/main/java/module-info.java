module com.game.chordsnake {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens com.game.chordsnake to javafx.fxml;
    exports com.game.chordsnake;
}