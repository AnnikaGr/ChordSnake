package com.game.chordsnake;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    public void startGame(MouseEvent event) {
        //start from level 1
        Main.setPane(1);
    }

   }