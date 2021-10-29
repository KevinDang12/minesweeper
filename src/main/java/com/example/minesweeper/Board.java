package com.example.minesweeper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

/**
 * The minesweeper game board that allows the player to select a tile for mines
 */
public class Board {

    /** The board size */
    private static final int BOARD_SIZE = 10;

    /** Checks the first time the user clicked on any tile */
    public boolean firstClick = false;

    /** Set up the game timer */
    private final Timer timer = new Timer();

    /** The total number of mines on the board */
    public int mineCount;

    /** Label to display the number of mines on the board */
    private Label minesCountLabel;

    /** Label to display the outcome message at the end of the game */
    private Label outcomeMessageLabel;

    /** Grid layout of the game board */
    private final Tile[][] board = new Tile[BOARD_SIZE][BOARD_SIZE];

    /**
     * Set up the window containing the Menu Bar and the game board
     * @return screen of the game containing the menu bar and the game board
     */
    public Parent createGame() {
        firstClick = false;

        // Set up menu bar labels
        Label lblMines = new Label("Mines Remaining");
        Label lblTimer = new Label("Time");

        minesCountLabel = new Label("00");
        timer.timerCountLabel = new Label("00:00");
        outcomeMessageLabel = new Label();
        outcomeMessageLabel.setFont(Font.font("Arial", 20));

        // Set up the alignment of the labels
        BorderPane screen = new BorderPane();
        VBox mines = new VBox();
        VBox time = new VBox();
        VBox menuBar = new VBox(50);
        Button btnReset = new Button("Reset");

        mines.getChildren().addAll(lblMines, minesCountLabel);
        time.getChildren().addAll(lblTimer, timer.timerCountLabel);
        menuBar.getChildren().addAll(btnReset, mines, time);
        menuBar.setPadding(new Insets(15));

        // Set up the grid layout of the game board
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setPadding(new Insets(10));
        grid.setPrefSize(500, 500);

        // Create tile for each cell
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Tile tile = new Tile(x, y, this);
                board[y][x] = tile;
                grid.add(tile.button, y, x);
            }
        }

        // Set the alignment of the menu bar and the game board
        screen.setCenter(grid);
        screen.setLeft(menuBar);
        screen.setBottom(outcomeMessageLabel);
        BorderPane.setAlignment(outcomeMessageLabel, Pos.TOP_CENTER);

        // Stop the timer and create a new game window
        btnReset.setOnAction(e -> {
            timer.timeStop();
            boardReset();
            minesCountLabel.setText("00");
            outcomeMessageLabel.setText("");
            timer.timerCountLabel.setText("00:00");
        });

        return screen;
    }

    /**
     * Reset the parameter of each tile in the game board. All the mines are removed, and each tile is cleared.
     */
    private void boardReset() {

        firstClick = false;

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Tile tile = board[y][x];
                tile.button.setStyle("-fx-background-color: #D3D3D3; -fx-border-width: 1px; -fx-border-color: black;");
                tile.setMines(false);
                tile.numOfAdjacentMines = 0;
                tile.isClicked = false;
                tile.isFlagged = false;
                tile.button.setGraphic(null);
                tile.button.setDisable(false);
            }
        }
    }

    /**
     * Build a grid layout and assign each tile a row and column, and randomly generate mines
     */
    public void boardSetUp(Tile firstTile) {
        firstClick = true;
        mineCount = 0;
        double chanceOfMines = 0.25;
        firstTile.setMines(false);

        // Set up the game board with the mines initialized
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Tile tile = board[y][x];

                if (tile.x == firstTile.x && tile.y == firstTile.y)
                    continue;

                tile.setMines(Math.random() < chanceOfMines);

                if (tile.hasMine) {
                    mineCount = mineCount + 1;
                }
            }
        }

        // Calculate the number of adjacent mines on each tile
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Tile tile = board[y][x];

                tile.numOfAdjacentMines = getNumOfAdjacentMines(checkAdjacent(tile));

            }
        }

        setMineCount(mineCount);
        timer.start();
    }

    /**
     * Count the number of remaining mines that are not flagged on the board
     * @param number the remaining number of mines
     */
    public void setMineCount(int number) {
        String count = String.format("%02d", number);
        minesCountLabel.setText(count);
    }

    /**
     * Display the outcome message of the game
     * @param message Message to output to the screen
     */
    public void setOutcomeMessage(String message) {
        outcomeMessageLabel.setText(message);
    }

    /**
     * Returns list of tiles that are adjacent to the given Tile
     * @param tile The position of the tile to check for mines
     * @return The list of tiles around the button to check for mines
     */
    public List<Tile> checkAdjacent(Tile tile) {

        int[] area = new int[] {
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1,
        };

        List<Tile> adjacent = new ArrayList<>();

        int tileX = tile.x;
        int tileY = tile.y;

        for (int i = 0; i < area.length; i += 2) {
            int adjacentX = area[i];
            int adjacentY = area[i + 1];

            int currentX = adjacentX + tileX;
            int currentY = adjacentY + tileY;

            if (currentX >= 0 && currentY >= 0 && currentX < BOARD_SIZE && currentY < BOARD_SIZE) {
                adjacent.add(board[currentY][currentX]);
            }
        }
        return adjacent;
    }

    /**
     * Count the number of mines in the list of adjacent tiles
     * @param adjTiles List of adjacent tiles
     * @return The number of tiles containing a mine
     */
    private int getNumOfAdjacentMines(List<Tile> adjTiles) {
        int count = 0;
        for (Tile tile : adjTiles) {
            if (tile.hasMine)
                count++;
        }

        return count;
    }

    /**
     * If the player selects a mine, show the mine that the player clicked, the incorrect tiles not containing a mine,
     * and any remaining mines on the board
     */
    public void revealMines() {
        timer.timeStop();

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Tile tile = board[y][x];
                tile.button.setDisable(true);
                Icons icons = new Icons();

                // Reveal any unselected mines in the tile
                if (tile.hasMine && !tile.isFlagged) {
                    tile.button.setGraphic(icons.getMine());
                    tile.button.setStyle("-fx-background-color: white; -fx-border-width: 1px; -fx-border-color: black;");
                }

                // Reveal any tiles not containing a mine and has a flag marker on the tile
                if (!tile.hasMine && tile.isFlagged) {
                    tile.button.setGraphic(null);
                    tile.button.setStyle("-fx-background-color: red; -fx-border-width: 1px; -fx-border-color: black;");
                }
            }
        }
    }

    /**
     * Check whether all the buttons on the board were selected and the mines are all flagged
     * @return True if all the buttons except the mines were selected, else False if a button was not selected
     */
    public boolean checkWin() {

        // Check if each tile not containing a mine are not selected
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Tile tile = board[y][x];

                if (!tile.hasMine && !tile.isClicked) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Disable all the buttons on the board after winning the game
     */
    public void endGame() {
        timer.timeStop();

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Tile tile = board[y][x];
                tile.button.setDisable(true);
            }
        }
    }
}
