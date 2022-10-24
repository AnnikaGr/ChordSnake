package com.game.chordsnake.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;


public class ChordPopup {

    private final Popup popup;
    private final Button checkAndContinue;
    private final Text title;
    private final Text subtitle;
    private final int STARTTIME = 5;
    private final int width = 600;
    private final int height = 300;
    private final TextField[] notes = new TextField[3];
    private String[] noteText = {"", "", ""};
    private Timeline timeline;
    private Integer timeSeconds;
    private Label timerLabel = new Label();
    private int success;


    public ChordPopup(String titleText, String subtitleText) {
        success = 1;
        this.popup = new Popup();
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);
        vbox.setPrefSize(width, height);

        Button checkAndContinue = new Button("Check and continue");
        this.checkAndContinue = checkAndContinue;
        checkAndContinue.setFont(Font.font("Bauhaus 93", 20));
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

        timerLabel.setFont(Font.font("Bauhaus 93", 24));
        timerLabel.setStyle("-fx-text-fill: white;");

        for (int i = 0; i < 3; i++) {
            notes[i] = new TextField("");
            notes[i].setPrefWidth(80);
            notes[i].setFont(Font.font("Bauhaus 93", 24));
            notes[i].setStyle("-fx-fill:#FFFFFF");
        }

        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds = STARTTIME;
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        EventHandler<ActionEvent> updateListener = new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                timeline.playFromStart();
                timeSeconds--; // update timerLabel
                timerLabel.setText(timeSeconds.toString());

                if (timeSeconds <= 0) {
                    success = 0;
                    timeline.stop();
                }
            }
        };
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), updateListener));
        timeline.playFromStart();

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(notes[0], notes[1], notes[2]);

        vbox.getChildren().addAll(title, subtitle, timerLabel, hbox, checkAndContinue);
        vbox.setStyle("-fx-background-color:#bd5923; -fx-border-width:2;-fx-border-radius:3;-fx-hgap:3;-fx-vgap:5;");

        popup.getContent().addAll(vbox);
    }

    public int getSuccess() { return success; }

    public Button getCheckAndContinue() {
        return checkAndContinue;
    }

    public Popup getPopup() {
        return popup;
    }

    public String getTextInput(int index) { return this.notes[index].getText(); }

    public void resetNoteText() {
        noteText[0] = "";
        noteText[1] = "";
        noteText[2] = "";
    }


}
