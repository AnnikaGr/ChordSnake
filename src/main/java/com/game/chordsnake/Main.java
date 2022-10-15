package com.game.chordsnake;

import com.game.chordsnake.model.Game;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends Application {
    private final static int totalPaneNum = 6;
    static AnchorPane root;
    static List<BorderPane> listPane = new ArrayList<>(totalPaneNum);
    private static int idCurrentPane = 0;
    private static Stage primaryStage;

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
    @FXML
    private Text titleInstrument = new Text();
    @FXML
    private Text titleSong = new Text();
    private static int song = 0;

    private Game gameInstance = new Game();
    private Controller controller = new Controller(gameInstance);

    public static void setPane(int idPane) {
        root.getChildren().remove(listPane.get(idCurrentPane));
        root.getChildren().add(listPane.get(idPane));
        idCurrentPane = idPane;
    }

    public int getSong() {
        return song;
    }

    public static void setSong(int songSet) {
        song = songSet;
    }

    public static void main(String[] args) {
        launch();
    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        setPrimaryStage(stage);

        root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("anchor.fxml")));
        listPane.add(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("start-view.fxml")))); //page 0
        listPane.add(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("instrument-selection-view.fxml")))); //page 1
        listPane.add(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("song-selection-view.fxml")))); //page 2
        listPane.add(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("success-view.fxml")))); //page 3
        listPane.add(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("fail-view.fxml")))); //page 4
        btnGuitar.setToggleGroup(instrumentGroup);
        btnCello.setToggleGroup(instrumentGroup);
        btnPiano.setToggleGroup(instrumentGroup);
        btnSong1.setToggleGroup(songGroup);
        btnSong2.setToggleGroup(songGroup);
        btnSong3.setToggleGroup(songGroup);

        root.getChildren().add(listPane.get(0));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("ChordSnake");
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    public void confirmInstrument() {
        int toggleGroupValue = instrumentGroup.getToggles().indexOf(instrumentGroup.getSelectedToggle());
        if (toggleGroupValue < 0) {
            titleInstrument.setText("Please select an instrument to proceed!");
            titleInstrument.setFill(Color.RED);
        } else {
            gameInstance.setInstrumentChosenID(toggleGroupValue);
            Main.setPane(2); //turn to choosing song page
        }

    }

    @FXML
    public void confirmSong() throws IOException {
        int toggleGroupValue = songGroup.getToggles().indexOf(songGroup.getSelectedToggle());
        if (toggleGroupValue < 0) {
            titleSong.setText("Please select a song to proceed!");
            titleSong.setFill(Color.RED);
        } else {
            gameInstance.setSongChosenID(toggleGroupValue);
            setSong(song);
            FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("game-view.fxml"));
            gameLoader.setController(controller);
            listPane.add(5, gameLoader.load());
            controller.initializeGrid();
            Main.setPane(5); //turn to game page
        }

    }

    @FXML
    public void startGame(MouseEvent event) {

        Main.setPane(1);
    }

    @FXML
    public void tryAgain() throws IOException {
        //TODO select song again? or restart the game?
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("game-view.fxml"));
        gameLoader.setController(controller);
        controller.initializeGrid();
        listPane.set(5, gameLoader.load());
        Main.setPane(5);

    }

}