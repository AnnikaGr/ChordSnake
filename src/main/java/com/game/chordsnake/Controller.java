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
    private GridPane gridGame = new GridPane();

    private Song songModel;
    private Board boardModel;
    private SingleGrid[][] board;

    private Game gameInstance;
    private boolean isInChordPopupState = false;

    public Controller(Game gameInstance){
        this.gameInstance = gameInstance;
    }

    @FXML
    public void initialize() {
        this.boardModel = new Board();
        this.songModel = new Song(gameInstance.getSongChosenID());

        board = new SingleGrid[boardModel.getWidth()][boardModel.getHeight()];

        boardModel.setBoard(songModel);
        initializeGrid();

        //Main.getPrimaryStage().getScene();


    }

    public void initializeGrid() {
        for (int i = 0; i < boardModel.getWidth(); i++) {
            for (int j = 0; j < boardModel.getHeight(); j++) {
                SingleGrid singleGrid = new SingleGrid(i, j, boardModel.getOneChord(boardModel.getWidth() * i + j));//TODO  maybe it's better to have a 2d array??
                board[i][j] = singleGrid;
                gridGame.add(board[i][j], i, j);
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


                //boardModel.arrangement[i][j]

            }
        }
    }



    //single grid on gridpane; initialized with string value
    //TODO for different ones set to different icons/empty/chords
    private class SingleGrid extends StackPane {

        private final int col;
        private final int row;
        Rectangle recGrid = new Rectangle(singleGridWidth, singleGridWidth, Paint.valueOf("#ffe9c6"));
        Text text = new Text();
        private String value;

        public SingleGrid(int x, int y, String gridValue) {

            col = x;
            row = y;
            value = gridValue;

            recGrid.setStroke(Color.BLACK);
            text.setFont(Font.font(15));
            text.setText(gridValue);
            text.setVisible(true);
            System.out.println("grid value: " + gridValue);

            getChildren().addAll(recGrid, text);
        }
    }

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
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }



}