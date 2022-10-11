package com.game.chordsnake;

import com.game.chordsnake.model.Board;
import com.game.chordsnake.model.Game;
import com.game.chordsnake.model.Song;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;


public class Controller {

    private final int singleGridWidth = 27;
    //radio button group for instrument selection
    @FXML
    private GridPane gridGame;

    private Song songModel;
    private Board boardModel;
    private Label[][] labels;

    private Game gameInstance;
    private boolean isInChordPopupState = false;

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

    @FXML
    public void initialize() {
        this.boardModel = new Board();
        this.songModel = new Song(gameInstance.getSongChosenID());
        boardModel.setBoard(songModel);
        labels = new Label[boardModel.getWidth()][boardModel.getHeight()];
        initializeGrid();

    }


    //when pressing on whole grid detected

    public void initializeGrid() {
        //labels = new Label[boardModel.getWidth()][boardModel.getHeight()];
        for (int i = 0; i < boardModel.getWidth(); i++) {
            for (int j = 0; j < boardModel.getHeight(); j++) {
                labels[i][j] = new Label(boardModel.getOneChord(boardModel.getWidth() * i + j));
                gridGame.add(labels[i][j], i, j);
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

    @FXML
    public void handleKeyPress(KeyEvent event) {
        System.out.println("key event");
        if (gameInstance.getGameStarted() == false) {
            gameInstance.setGameStarted(true);
        }
        if (gameInstance.getGameStarted() && !isInChordPopupState) {
            if (event.getCode() == KeyCode.W) {
                boardModel.getCurrentSnake().setSnakeDirectionUp();
                if (boardModel.getCurrentSnake().getSnakeHeadY() +1 >=0) {
                    boardModel.getCurrentSnake().setSnakeDirectionUp();
                    updateGrid();
                }

            } else if (event.getCode() == KeyCode.D) {
                boardModel.getCurrentSnake().setSnakeDirectionRight();
                if (boardModel.getCurrentSnake().getSnakeHeadX() +1 < boardModel.getWidth()) {
                    boardModel.getCurrentSnake().setSnakeDirectionRight();
                    updateGrid();
                }

            } else if (event.getCode() == KeyCode.S) {
                boardModel.getCurrentSnake().setSnakeDirectionDown();
                if (boardModel.getCurrentSnake().getSnakeHeadY() - 1 <boardModel.getHeight()) {
                    boardModel.getCurrentSnake().setSnakeDirectionDown();
                    updateGrid();
                }
            } else if (event.getCode() == KeyCode.A) {
                //SNAKE NOT DIE
                if (boardModel.getCurrentSnake().getSnakeHeadX() - 1 >= 0) {
                    boardModel.getCurrentSnake().setSnakeDirectionLeft();
                    System.out.println("SNAKE HEAD " + boardModel.getCurrentSnake().getSnakeHeadX() + " " + boardModel.getCurrentSnake().getSnakeHeadY());
                    updateGrid();
                    //while(true) {
                    //    delay(3000, () -> boardModel.updateArrangement());
                    //    updateGrid();
                    //}
                }

            }
        }
        //TODO handle presses from chordPopup
    }

    //single grid on gridpane; initialized with string value
    //TODO for different ones set to different icons/empty/chords

    public void updateGrid() {
        boardModel.updateArrangement();
        for (int i = 0; i < boardModel.getWidth(); i++) {
            for (int j = 0; j < boardModel.getHeight(); j++) {
                //Node node = getNodeFromGridPane(gridGame, i, j);
                labels[i][j].setText(boardModel.arrangement[i][j]);
            }
        }
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (gridPane.getColumnIndex(node) != null && gridPane.getRowIndex(node) != null && gridPane.getColumnIndex(node) == col && gridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}