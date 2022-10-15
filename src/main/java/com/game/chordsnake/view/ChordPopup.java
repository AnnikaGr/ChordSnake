package view;

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
        private Button checkAndContinue;
        private Text title;
        private Text subtitle;
    private Text note1;
    private Text note2;
    private Text note3;

        public ChordPopup(String titleText, String subtitleText){
            this.popup = new Popup();
            VBox vbox= new VBox();
            vbox.setSpacing(10);
            vbox.setAlignment(Pos.CENTER);
            vbox.setFillWidth(true);
            vbox.prefWidthProperty().bind(Main.getPrimaryStage().getScene().widthProperty());
            vbox.prefHeightProperty().bind(Main.getPrimaryStage().getScene().heightProperty());

            Button checkAndContinue = new Button("Check entry and continue");
            this.checkAndContinue =checkAndContinue;
            checkAndContinue.setFont(Font.font("Bauhaus 93", 36));
            checkAndContinue.setStyle("-fx-text-fill:#FFFFFF; -fx-background-color:  #975C4E; -fx-effect:  dropshadow( gaussian , rgba(0,0,0,0.4) , 10,0,0,1 )");

            Text title= new Text();
            title.setStyle("-fx-fill:#FFFFFF");
            title.setFont(Font.font("Bauhaus 93", 36));
            title.setText(titleText);
            this.title=title;

            Text subtitle = new Text();
            subtitle.setFont(Font.font("Bauhaus 93", 24));
            subtitle.setStyle("-fx-fill:#FFFFFF");
            subtitle.setText(subtitleText);
            this.subtitle=subtitle;

            HBox hbox= new HBox();
            hbox.getChildren().addAll(note1,note2,note3 );
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


}
