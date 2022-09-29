package com.game.chordsnake;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;


public class FXMLController {

    @FXML
    private ToggleGroup instrumetnGroup = new ToggleGroup();
    @FXML
    private RadioButton btnGuitar = new RadioButton();
    @FXML
    private RadioButton btnCello = new RadioButton();
    @FXML
    private RadioButton btnPiano = new RadioButton();

    //private final Game gameModel;
    //public FXMLController(Game gameModel) {
    //    this.gameModel = gameModel;
    //}

    public void initialize() {
        btnGuitar.setToggleGroup(instrumetnGroup);
        btnCello.setToggleGroup(instrumetnGroup);
        btnPiano.setToggleGroup(instrumetnGroup);
    }

    @FXML
    public void confirmInstrument(MouseEvent event) {
        int toogleGroupValue = instrumetnGroup.getToggles().indexOf(instrumetnGroup.getSelectedToggle());
        System.out.println(toogleGroupValue);
        //gameModel.setInstrumentChosenID(toogleGroupValue);
    }

    @FXML
    public void startGame(MouseEvent event) {
        //start from level 1
        Main.setPane(1);
    }

}