package com.game.chordsnake;

import com.game.chordsnake.model.Board;
import com.game.chordsnake.model.Game;
import com.game.chordsnake.model.Song;
import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.List;


public class Controller {

    private final int singleGridWidth = 27;
    int speed = 1;
    //radio button group for instrument selection
    @FXML
    private GridPane gridGame= new GridPane();
    @FXML
    private GridPane chordChunk;
    private Song songModel;
    private Board boardModel;
    private Label[][] labels;
    private Game gameInstance;
    private boolean isInChordPopupState = false;
    private int success = 1;

    AnimationTimer animationTimer= new AnimationTimer() {
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

    // code from https://stackoverflow.com/questions/26454149/make-javafx-wait-and-continue-with-code
    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

    public static boolean containsArray(List<int[]> list, int[] probe) {
        for (int[] element : list) {
            if (Arrays.equals(element, probe)) {
                return true;
            }
        }
        return false;
    }

    public void initialize() {

    }

    public void initializeGrid() {
        this.boardModel = new Board();
        this.songModel = new Song(gameInstance.getSongChosenID());
        boardModel.setBoard(songModel);
        labels = new Label[boardModel.getWidth()][boardModel.getHeight()];

        for (int i = 0; i < boardModel.getWidth(); i++) {
            for (int j = 0; j < boardModel.getHeight(); j++) {
                labels[i][j] = new Label(boardModel.getOneChord(i, j));

                gridGame.add(labels[i][j], i, j);

                Button button = new Button("");
                button.setStyle("-fx-background-color: #00000000; -fx-border-color: #FFFFFF;");
                button.setOnMouseClicked(e -> {
                    cellOnMouseClick(e);
                });
                button.setOnDragDetected(e -> {
                    cellDragDetected(e);
                });
                gridGame.add(button, i, j);
            }
        }

        /*for (int i = 0; i< numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                Button button = createButton("");
                String content="";
                if(board.grid[i][j].hasMine){
                    content= "mine";
                }
                else if (board.grid[i][j].hasWater){
                    content = "water";
                }
                else if (board.grid[i][j].hasWell){
                    content = "well";
                }
                else {
                    content = Integer.toString(board.grid[i][j].numSurroundingMines);
                }
                //
                Label label = createCellContent(content);
                grid.add(label,j,i);
                grid.add(button, j, i );
            }
        }*/
    }

//single grid on gridpane; initialized with string value
    //TODO for different ones set to different icons/empty/chords

    public void updateGrid() {
        if (success==0) {
            //success = false;
            Main.setPane(4); //fail page
        } else if (success==2){ //chord was appended
            success = boardModel.updateArrangement();
            view.ChordPopup popup = new view.ChordPopup("Select the notes of the chord!", "Insert Chord Name here");
            stopAnimation();

            popup.getPopup().show(Main.getPrimaryStage());
            popup.getCheckAndContinue().setOnMouseClicked(e->{
                continueGame();
                popup.getPopup().hide();
            });

            updateGridLayout();
        }
     else if (success==1){ //no chord was appended
        success = boardModel.updateArrangement();
        updateGridLayout();
    }
     else throw new IllegalStateException("Integer Success doesn´t hold any of the values 1,2,3");

    }

    public void updateGridLayout() {
        for (int i = 0; i < boardModel.getWidth(); i++) {
            for (int j = 0; j < boardModel.getHeight(); j++) {
                //Node node = getNodeFromGridPane(gridGame, i, j);
                labels[i][j].setText(boardModel.arrangement[i][j]);
            }
        }
    }


    //not using this atm

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (gridPane.getColumnIndex(node) == col && gridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }


// --- Event Handlers -------------------------------------------------------------------------------

    @FXML
    public void handleKeyPress(KeyEvent event) {
        System.out.println("handle key press");
        int headX = boardModel.getCurrentSnake().getSnakeHeadX();
        int headY = boardModel.getCurrentSnake().getSnakeHeadY();
        int newHeadX = headX;
        int newHeadY = headY;
        if (gameInstance.getGameStarted() == false) {
            gameInstance.setGameStarted(true);
            // inspired by https://github.com/Gaspared/snake/blob/master/Main.java
            startAnimation();
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
        } else {
            //TODO handle presses from chordPopup
        }

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
    public void randomizeBoard() {
        boardModel.shuffleBoard();
        updateGridLayout();
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
        }

        int counter = 0;
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


    // -- Animation start and stop ----------------------------------------------

    public void startAnimation(){
        animationTimer.start();
    }

    public void stopAnimation(){
        animationTimer.stop();
    }

    public void continueGame(){
        startAnimation();
    }


}