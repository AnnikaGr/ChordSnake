package com.game.chordsnake;

import com.game.chordsnake.model.Game;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends Application {
    private final static int totalPaneNum = 4;
    static AnchorPane root;
    static List<BorderPane> listPane = new ArrayList<>(totalPaneNum);
    private static int idCurrentPane = 0;

    @FXML
    private ToggleGroup instrumentGroup = new ToggleGroup();
    @FXML
    private RadioButton btnGuitar = new RadioButton();
    @FXML
    private RadioButton btnCello = new RadioButton();
    @FXML
    private RadioButton btnPiano = new RadioButton();
    //radio button group for song selection
    @FXML
    private ToggleGroup songGroup = new ToggleGroup();
    @FXML
    private RadioButton btnSong1 = new RadioButton();
    @FXML
    private RadioButton btnSong2 = new RadioButton();
    @FXML
    private RadioButton btnSong3 = new RadioButton();

    private Game gameInstance = new Game();


    public static void setPane(int idPane) {
        root.getChildren().remove(listPane.get(idCurrentPane));
        root.getChildren().add(listPane.get(idPane));
        idCurrentPane = idPane;
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("anchor.fxml")));
        listPane.add(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("start-view.fxml"))));
        listPane.add(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("instrument-selection-view.fxml"))));
        listPane.add(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("song-selection-view.fxml"))));

        btnGuitar.setToggleGroup(instrumentGroup);
        btnCello.setToggleGroup(instrumentGroup);
        btnPiano.setToggleGroup(instrumentGroup);
        btnSong1.setToggleGroup(songGroup);
        btnSong2.setToggleGroup(songGroup);
        btnSong3.setToggleGroup(songGroup);

        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("game-view.fxml"));
        gameLoader.setController(new Controller(gameInstance));
        listPane.add(gameLoader.load());

        root.getChildren().add(listPane.get(0));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("ChordSnake");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void confirmInstrument() {
        int toggleGroupValue = instrumentGroup.getToggles().indexOf(instrumentGroup.getSelectedToggle());
        gameInstance.setInstrumentChosenID(toggleGroupValue);
        //turn to choosing song page
        Main.setPane(2);
    }

    @FXML
    public void confirmSong() {
        int toggleGroupValue = songGroup.getToggles().indexOf(songGroup.getSelectedToggle());
        gameInstance.setSongChosenID(toggleGroupValue);
        Main.setPane(3);
    }

    @FXML
    public void startGame(MouseEvent event) {
        //start from choosing instruments
        Main.setPane(1);
    }


}