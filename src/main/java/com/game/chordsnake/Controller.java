package com.game.chordsnake;

import com.game.chordsnake.model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;


public class Controller {

    //radio button group for instrument selection
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

    private Game gameModel;

    @FXML
    public void initialize() {
        this.gameModel = new Game();
        btnGuitar.setToggleGroup(instrumentGroup);
        btnCello.setToggleGroup(instrumentGroup);
        btnPiano.setToggleGroup(instrumentGroup);
        btnSong1.setToggleGroup(songGroup);
        btnSong2.setToggleGroup(songGroup);
        btnSong3.setToggleGroup(songGroup);
    }

    @FXML
    public void confirmInstrument() {
        int toggleGroupValue = instrumentGroup.getToggles().indexOf(instrumentGroup.getSelectedToggle());
        gameModel.setInstrumentChosenID(toggleGroupValue);
        //turn to choosing song page
        Main.setPane(2);
    }

    @FXML
    public void confirmSong() {
        int toggleGroupValue = songGroup.getToggles().indexOf(songGroup.getSelectedToggle());
        gameModel.setSongChosenID(toggleGroupValue);
        //turn to game page
        Main.setPane(3);
    }

    @FXML
    public void startGame(MouseEvent event) {
        //start from choosing instruments
        Main.setPane(1);
    }

}