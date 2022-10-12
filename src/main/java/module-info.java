module com.game.chordsnake {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.game.chordsnake to javafx.fxml;
    exports com.game.chordsnake;
}