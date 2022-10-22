package com.game.chordsnake;

import com.game.chordsnake.model.Board;
import com.game.chordsnake.model.Game;
import com.game.chordsnake.model.Song;
import com.game.chordsnake.view.ChordPopup;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Controller {
    @FXML
    private GridPane gridGame = new GridPane();
    @FXML
    private GridPane chordChunk;
    @FXML
    private ImageView albumCover = new ImageView();
    @FXML
    private Text message;
    private Song songModel;
    private Board boardModel;
    private Label[][] labels;
    private Game gameInstance;
    private boolean isInChordPopupState = false;
    private int success = 1;
    private int noteCounter;
    private final int singleGridWidth = 40;
    private int speed = 1;
    private ChordPopup popup;
    private List<String> savedChords = new ArrayList<>();

    AnimationTimer animationTimer = new AnimationTimer() {
        long lastTick = 0;

        public void handle(long now) {
            if (lastTick == 0) {
                lastTick = now;
                updateGrid();
                return;
            }

            if (now - lastTick > 1000000000 / speed) {
                lastTick = now;
                updateGrid();
            }
        }

    };

    public Controller(Game gameInstance) {
        this.gameInstance = gameInstance;
    }

    public void initializeGrid() {
        success = 1;
        this.boardModel = new Board();
        this.songModel = new Song(gameInstance.getSongChosenID());
        boardModel.setBoard(songModel);
        boardModel.getCurrentSnake().setInsId(gameInstance.getInstrumentChosenID());
        labels = new Label[boardModel.getWidth()][boardModel.getHeight()];
        Image image = new Image(Objects.requireNonNull(Main.class.getResource("assets/album" + gameInstance.getSongChosenID() + ".jpg")).toString());
        albumCover.setImage(image);

        for (int i = 0; i < boardModel.getWidth(); i++) {
            for (int j = 0; j < boardModel.getHeight(); j++) {
                labels[i][j] = new Label();
                labels[i][j].setAlignment(Pos.CENTER);
                labels[i][j].setPrefWidth(singleGridWidth);
                labels[i][j].setPrefHeight(singleGridWidth);

                if (boardModel.getOneChord(i, j) == "T") {
                    labels[i][j].setStyle("-fx-border-color: black;" + "-fx-background-image:url(\"https://i.ibb.co/Rb2bK5q/trash.png\");"+ "-fx-background-size: 40 40; ");
                } else if (boardModel.getOneChord(i, j) == "Z") {
                    labels[i][j].setStyle("-fx-border-color: black;" + "-fx-background-color: #709255");
                } else if (boardModel.getOneChord(i, j) == "I") {
                    labels[i][j].setStyle("-fx-border-color: black;" + "-fx-background-image:url(\"https://i.ibb.co/thfw3R8/music-note.png\");"+ "-fx-background-size: 40 40; ");
                } else if (boardModel.getOneChord(i, j) != "SH"){ //chords
                    labels[i][j].setText(boardModel.arrangement[i][j]);
                    labels[i][j].setStyle("-fx-border-color: black;" + "-fx-background-color: #dda15e");
                } else {
                    labels[i][j].setStyle("-fx-border-color: black;" + "-fx-background-color: #fbec88");
                }
                gridGame.add(labels[i][j], i, j);

                Button button = new Button("");
                button.setStyle("-fx-background-color: transparent");
                button.setOnMouseClicked(e -> {
                    cellOnMouseClick(e);
                });
                button.setOnDragDetected(e -> {
                    cellDragDetected(e);
                });
                gridGame.add(button, i, j);
            }
        }
    }

    public void updateGridLayout() {
        for (int i = 0; i < boardModel.getWidth(); i++) {
            for (int j = 0; j < boardModel.getHeight(); j++) {
                labels[i][j].setText(null);
                if (boardModel.getOneChord(i, j) == "T") {
                    labels[i][j].setStyle("-fx-border-color: black;" + "-fx-background-image:url(\"https://i.ibb.co/Rb2bK5q/trash.png\");"+ "-fx-background-size: 40 40; ");
                } else if (boardModel.getOneChord(i, j) == "Z") {
                    labels[i][j].setStyle("-fx-border-color: black;" + "-fx-background-color: #709255");
                } else if (boardModel.getOneChord(i, j) == "I") {
                    labels[i][j].setStyle("-fx-border-color: black;" + "-fx-background-image:url(\"https://i.ibb.co/thfw3R8/music-note.png\");"+ "-fx-background-size: 40 40; ");
                } else if (boardModel.getOneChord(i, j) != "SH"){ //chords
                    labels[i][j].setText(boardModel.arrangement[i][j]);
                    labels[i][j].setStyle("-fx-border-color: black;" + "-fx-background-color: #dda15e");
                }
                for (int[] position : boardModel.getCurrentSnake().getSnakePosition()) {
                    if(position[0] == i && position[1] == j) {
                        if (boardModel.getOneChord(i, j) != "SH") labels[i][j].setText(boardModel.arrangement[i][j]);
                        labels[i][j].setStyle("-fx-border-color: black;" + "-fx-background-color: #fbec88");
                    }
                }

            }
        }
    }


    public void updateGrid() {
        if(success!= 2 && checkWon()){ //check won if chord wasnt appended this tick
            System.out.println("You won");
            stopAnimation();
            gameInstance.setGameStarted(false);
            Main.setPane(5);
        }

        if (success == 0) { //success = false;
            stopAnimation();
            gameInstance.setGameStarted(false);
            Main.setPane(4); //fail page
        } else if (success == 2) { //chord was appended
            stopAnimation();
            success = boardModel.updateArrangement();
            this.popup = new ChordPopup("Select the notes of " + boardModel.getChordToCheck(), "Insert Chord Name here");
            isInChordPopupState = true;
            noteCounter = 0;
            popup.resetNoteText();
            boardModel.resetNoteText();
            popup.getPopup().show(Main.getPrimaryStage());

            popup.getCheckAndContinue().setOnMouseClicked(e -> {
                this.boardModel.setNoteText(0, popup.getTextInput(0));
                this.boardModel.setNoteText(1, popup.getTextInput(1));
                this.boardModel.setNoteText(2, popup.getTextInput(2));
                if (boardModel.checkNotes()) {
                    if(checkWon()){
                        stopAnimation();
                        gameInstance.setGameStarted(false);
                        Main.setPane(5);
                    }
                    continueGame();
                } else {
                    success = 0;
                    gameInstance.setGameStarted(false);
                    Main.setPane(4); //fail
                }
                isInChordPopupState = false;
                popup.getPopup().hide();
            });

            updateGridLayout();
        } else if (success == 1) { //no chord was appended
            startAnimation();
            success = boardModel.updateArrangement();
            updateGridLayout();
        } else throw new IllegalStateException("Integer Success doesn´t hold any of the values 1,2,3");


    }

    public boolean checkWon(){
        if(!savedChords.isEmpty()){
            List<String> fullCollectedChords= new ArrayList<>();
            fullCollectedChords.addAll(savedChords);
            fullCollectedChords.addAll(boardModel.getCurrentSnake().getCollectedChordsWithoutHead());
            return songModel.checkCorrectOrder(fullCollectedChords);
        }
        else {
         return songModel.checkCorrectOrder(boardModel.getCurrentSnake().getCollectedChordsWithoutHead());
        }
    }

    // --- Event Handlers -------------------------------------------------------------------------------
    @FXML
    public void randomizeBoard() {
        boardModel.shuffleBoard();
        updateGridLayout();
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        int headX = boardModel.getCurrentSnake().getSnakeHeadX();
        int headY = boardModel.getCurrentSnake().getSnakeHeadY();
        int newHeadX = headX;
        int newHeadY = headY;

        if (gameInstance.getGameStarted() == false) {
            if (success == 1) {
                gameInstance.setGameStarted(true);
                // inspired by https://github.com/Gaspared/snake/blob/master/Main.java
                startAnimation();
            }

        }
        if (gameInstance.getGameStarted() && !isInChordPopupState) {
            if (event.getCode() == KeyCode.W) {
                boardModel.getCurrentSnake().setSnakeDirectionUp();
            } else if (event.getCode() == KeyCode.D) {
                boardModel.getCurrentSnake().setSnakeDirectionRight();
            } else if (event.getCode() == KeyCode.S) {
                boardModel.getCurrentSnake().setSnakeDirectionDown();
            } else if (event.getCode() == KeyCode.A) {
                boardModel.getCurrentSnake().setSnakeDirectionLeft();
            }
        } else throw new IllegalStateException("Illegal Game State");

    }

    public void cellOnMouseClick(MouseEvent e) {
        if (e.getButton().equals(MouseButton.PRIMARY)) {
            if (e.getClickCount() == 2) {
                Node tmp = (Node) e.getSource();
                int row = gridGame.getRowIndex(tmp);
                int col = gridGame.getColumnIndex(tmp);

                boolean snakeOnTrash = containsArray(boardModel.getCurrentSnake().getSnakePosition(), boardModel.getTrashPosition());
                boolean clickedOnTrash = boardModel.getTrashPosition()[0] == col && boardModel.getTrashPosition()[1] == row;
                if (clickedOnTrash && snakeOnTrash) {
                    List<int[]> snakePositions = boardModel.getCurrentSnake().getSnakePosition();
                    if (snakePositions.size() == 1) { //only snake head exists
                        e.consume();
                    }
                    int[] lastElementPosition = snakePositions.get(boardModel.getCurrentSnake().getSnakePosition().size() - 1);
                    boardModel.arrangement[lastElementPosition[0]][lastElementPosition[1]] = "Z";
                    boardModel.getCurrentSnake().removeLastElement();
                }
            }
        }
    }

    public void cellDragDetected(MouseEvent event) {
        Node tmp = (Node) event.getSource();
        int row = gridGame.getRowIndex(tmp);
        int col = gridGame.getColumnIndex(tmp);

        boolean snakeOnInstrument = containsArray(boardModel.getCurrentSnake().getSnakePosition(), boardModel.getInstrumentPosition());
        boolean clickedOnInstrument = boardModel.getInstrumentPosition()[0] == col && boardModel.getInstrumentPosition()[1] == row;
        if (clickedOnInstrument && snakeOnInstrument) {
            Dragboard db = gridGame.startDragAndDrop(TransferMode.ANY);
            /* Put a string on a dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString("started");
            db.setContent(content);
            event.consume();
        }
    }

    @FXML
    public void savingGridOnDragOver(DragEvent event) {
        if (event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    public void savingGridOnDragDropped(DragEvent event) {

        List<String> collectedChords = boardModel.getCurrentSnake().getCollectedChordsWithoutHead();
        if (collectedChords.size() == 0) { //only snake head exists
            event.consume();
        } else if (collectedChords.size() > 10) {
            this.message.setText("Your snake is already too long to save it!");
            event.consume();
        } else {
            int counter = 0;
            savedChords.addAll(collectedChords);
            for (String chord : collectedChords
            ) {
                chordChunk.add(new Label(chord), counter, 0);
                counter++;
            }
            List<int[]> collectedChordsPositions = boardModel.getCurrentSnake().getSnakePosition().subList(1, boardModel.getCurrentSnake().getSnakePosition().size());
            for (int[] position : collectedChordsPositions
            ) {
                boardModel.arrangement[position[0]][position[1]] = "Z";
            }

            boardModel.getCurrentSnake().clearSnake();
        }
    }

    @FXML
    public void playSong() {
        for (String chord : savedChords) {
            try {
                Clip sound = AudioSystem.getClip();
                sound.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(Main.class.getResource("music/" + gameInstance.getInstrumentChosenID() +"/" + chord + ".wav"))));
                sound.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
    }


    // -- Animation start and stop ----------------------------------------------

    public void continueGame() {
        startAnimation();
    }

    public void startAnimation() {
        animationTimer.start();
    }

    public void stopAnimation() {
        animationTimer.stop();
    }

    // -- Util ------------------------------------------------------------------
    public static boolean containsArray(List<int[]> list, int[] probe) {
        for (int[] element : list) {
            if (Arrays.equals(element, probe)) {
                return true;
            }
        }
        return false;
    }
}