package com.game.chordsnake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        listPane.add(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("game-view.fxml"))));

        root.getChildren().add(listPane.get(0));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("ChordSnake");
        stage.setScene(scene);
        stage.show();
    }
}