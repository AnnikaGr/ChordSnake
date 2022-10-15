package com.game.chordsnake.view;

import com.game.chordsnake.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;


public class ChordPopup {

    private final Popup popup;
    private final Button checkAndContinue;
    private final Text title;
    private final Text subtitle;


    private final Text[] notes = new Text[3];
    private String[] noteText = {"", "", ""};

    public ChordPopup(String titleText, String subtitleText) {
        this.popup = new Popup();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);
        vbox.prefWidthProperty().bind(Main.getPrimaryStage().getScene().widthProperty());
        vbox.prefHeightProperty().bind(Main.getPrimaryStage().getScene().heightProperty());

        Button checkAndContinue = new Button("Check entry and continue");
        this.checkAndContinue = checkAndContinue;
        checkAndContinue.setFont(Font.font("Bauhaus 93", 36));
        checkAndContinue.setStyle("-fx-text-fill:#FFFFFF; -fx-background-color:  #975C4E; -fx-effect:  dropshadow( gaussian , rgba(0,0,0,0.4) , 10,0,0,1 )");

        Text title = new Text();
        title.setStyle("-fx-fill:#FFFFFF");
        title.setFont(Font.font("Bauhaus 93", 36));
        title.setText(titleText);
        this.title = title;

        Text subtitle = new Text();
        subtitle.setFont(Font.font("Bauhaus 93", 24));
        subtitle.setStyle("-fx-fill:#FFFFFF");
        subtitle.setText(subtitleText);
        this.subtitle = subtitle;

        /*Text note1 = new Text("----");
        note1.setFont(Font.font("Bauhaus 93", 24));
        note1.setStyle("-fx-fill:#FFFFFF");
        this.note1 = note1;

        Text note2 = new Text("----");
        note2.setFont(Font.font("Bauhaus 93", 24));
        note2.setStyle("-fx-fill:#FFFFFF");
        this.note2 = note2;

        Text note3 = new Text("----");
        note3.setFont(Font.font("Bauhaus 93", 24));
        note3.setStyle("-fx-fill:#FFFFFF");
        this.note3 = note3;*/

        for(int i = 0; i<3;i++) {
            notes[i] = new Text("----");
            notes[i].setFont(Font.font("Bauhaus 93", 24));
            notes[i].setStyle("-fx-fill:#FFFFFF");
        }

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(notes[0], notes[1], notes[2]);

        vbox.getChildren().addAll(title, subtitle, hbox, checkAndContinue);
        vbox.setStyle("-fx-background-color:#975C4E; -fx-border-width:2;-fx-border-radius:3;-fx-hgap:3;-fx-vgap:5;");

        popup.getContent().addAll(vbox);
    }

    public Button getCheckAndContinue() {
        return checkAndContinue;
    }

    public Popup getPopup() {
        return popup;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle.setText(subtitle);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }


    public void setNoteText(int counter, String note) {
        noteText[counter] += note;
        this.notes[counter].setText(noteText[counter]);
    }

    public void resetNoteText() {
        noteText[0] = "";
        noteText[1] = "";
        noteText[2] = "";
    }


}
