package com.game.chordsnake;

import com.game.chordsnake.model.Board;
import com.game.chordsnake.model.Game;
import com.game.chordsnake.model.Song;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


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

    public Controller(Game gameInstance){
        this.gameInstance = gameInstance;

    }

    @FXML
    public void initialize() {
        this.boardModel = new Board();
        this.songModel = new Song(gameInstance.getSongChosenID());
        boardModel.setBoard(songModel);
        labels = new Label[boardModel.getWidth()][boardModel.getHeight()];
        initializeGrid();

    }

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



    //when pressing on whole grid detected

    @FXML
    public void gridPressed(KeyEvent event) {
        /*Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != gridGame) {
            //gameModel.setGameStarted(true);
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);
            System.out.println("Mouse pressed cell: " + colIndex + " And: " + rowIndex);
        }*/
        System.out.println("Key pressed");
    }


    @FXML
    public void handleKeyPress (KeyEvent event){

        if(gameInstance.getGameStarted()==false){
            gameInstance.setGameStarted(true);

            if (event.getCode() == KeyCode.A) {
                if(gameInstance.getGameStarted())
                    System.out.println("Scene: A key was pressed");
                //TODO make move each second
                //while(true) {
                //delay(1000, () -> boardModel.updateArrangement());
                updateGrid();
                //}
            }
        }
        else if (!isInChordPopupState){
            if (event.getCode() == KeyCode.W) {
                boardModel.getCurrentSnake().setSnakeDirectionUp();

            }
            else if (event.getCode() == KeyCode.D) {
                boardModel.getCurrentSnake().setSnakeDirectionRight();

            }
            else if (event.getCode() == KeyCode.S) {
                boardModel.getCurrentSnake().setSnakeDirectionDown();
            }
            else if (event.getCode() == KeyCode.A) {
                boardModel.getCurrentSnake().setSnakeDirectionLeft();
            }
        }
        //TODO handle presses from chordPopup

    }

    public void updateGrid() {
        for (int i = 0; i < boardModel.getWidth(); i++) {
            for (int j = 0; j < boardModel.getHeight(); j++) {
                Node node = getNodeFromGridPane(gridGame,i,j);
                if (boardModel.arrangement[i][j] == "SH") {
                    System.out.println(i + j);
                }
                //boardModel.arrangement[i][j]

            }
        }
    }



    //single grid on gridpane; initialized with string value
    //TODO for different ones set to different icons/empty/chords

    // code from https://stackoverflow.com/questions/26454149/make-javafx-wait-and-continue-with-code
    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (gridPane.getColumnIndex(node)!= null && gridPane.getRowIndex(node)!= null && gridPane.getColumnIndex(node) == col && gridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

}