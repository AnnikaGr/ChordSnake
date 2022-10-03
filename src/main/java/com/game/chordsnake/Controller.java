package com.game.chordsnake;

import com.game.chordsnake.model.Board;
import com.game.chordsnake.model.Game;
import com.game.chordsnake.model.Song;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Controller {

    private final int singleGridWidth = 20;
    //radio button group for instrument selection
    @FXML
    private GridPane gridGame = new GridPane();
    @FXML
    private ToggleGroup instrumentGroup = new ToggleGroup();
    @FXML
    private RadioButton btnGuitar = new RadioButton();
    @FXML
    private RadioButton btnCello = new RadioButton();
    @FXML
    private RadioButton btnPiano = new RadioButton();
    //radio button group for song selection
    @FXML
    private ToggleGroup songGroup = new ToggleGroup();
    @FXML
    private RadioButton btnSong1 = new RadioButton();
    @FXML
    private RadioButton btnSong2 = new RadioButton();
    @FXML
    private RadioButton btnSong3 = new RadioButton();
    private Game gameModel;
    private Song songModel;
    private Board boardModel;
    private SingleGrid[][] board;

    @FXML
    public void initialize() {
        this.gameModel = new Game();
        this.boardModel = new Board();
        this.songModel = new Song();
        btnGuitar.setToggleGroup(instrumentGroup);
        btnCello.setToggleGroup(instrumentGroup);
        btnPiano.setToggleGroup(instrumentGroup);
        btnSong1.setToggleGroup(songGroup);
        btnSong2.setToggleGroup(songGroup);
        btnSong3.setToggleGroup(songGroup);
        board = new SingleGrid[boardModel.getWidth()][boardModel.getHeight()];
    }

    public void initializeGrid() {
        for (int i = 0; i < boardModel.getWidth(); i++) {
            for (int j = 0; j < boardModel.getHeight(); j++) {
                SingleGrid singleGrid = new SingleGrid(i, j, boardModel.getOneChord(boardModel.getWidth() * i + j));//TODO  maybe it's better to have a 2d array??
                board[i][j] = singleGrid;
                gridGame.add(board[i][j], i, j);
            }
        }
    }

    @FXML
    public void confirmInstrument() {
        int toggleGroupValue = instrumentGroup.getToggles().indexOf(instrumentGroup.getSelectedToggle());
        gameModel.setInstrumentChosenID(toggleGroupValue);
        //turn to choosing song page
        Main.setPane(2);
    }

    //when pressing on whole grid detected
    /*
    @FXML
    public void gridPressed(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != gridGame) {
            gameModel.setGameStarted(true);
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);
            System.out.println("Mouse pressed cell: " + colIndex + " And: " + rowIndex);
        }
    }*/

    @FXML
    public void confirmSong() {
        int toggleGroupValue = songGroup.getToggles().indexOf(songGroup.getSelectedToggle());
        gameModel.setSongChosenID(toggleGroupValue);
        songModel.setChordOrder(gameModel.getSongChosenID());
        boardModel.setBoard(songModel);
        //turn to game page
        initializeGrid();
        Main.setPane(3);
    }

    @FXML
    public void startGame(MouseEvent event) {
        //start from choosing instruments
        Main.setPane(1);
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
            text.setFont(Font.font(18));
            text.setText(gridValue);
            text.setVisible(true);
            System.out.println("grid value: " + gridValue);

            getChildren().addAll(recGrid, text);
        }
    }

}