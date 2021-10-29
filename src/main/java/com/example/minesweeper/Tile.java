package com.example.minesweeper;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;

/**
 * A Minesweeper tile used in the game board
 */
public class Tile extends StackPane {

    /** Icon that the Tile will display. */
    private final Icons icon = new Icons();

    /** The game board the tile is a part of */
    private final Board board;

    /** The x and y coordinates of the tile on the board */
    public int x, y;

    /** Checks whether this tile has a mine or not */
    public boolean hasMine;

    /** Checks whether the tile has been clicked on */
    public boolean isClicked = false;

    /** The number of mines adjacent around the selected tile */
    public int numOfAdjacentMines;

    /** Checks whether the tile has a flag marker */
    public boolean isFlagged = false;

    /** The button that the user can click on */
    public Button button = new Button();

    /**
     * Set up the tile on the game board using a button, allows the
     * user to clear the board for mines (using the primary mouse button)
     * and place down a flag for a possible mine location (using the secondary mouse button)
     *
     * @param x the x-coordinate of the tile on the game board
     * @param y the y-coordinate of the tile on the game board
     */
    public Tile (int x, int y, Board board) {
        this.x = x;
        this.y = y;

        this.board = board;

        String closed = "-fx-background-color: #D3D3D3; -fx-border-width: 1px; -fx-border-color: black;";
        button.setStyle(closed);
        button.setPrefSize(40, 40);

        button.setOnMouseClicked(event -> {

            // Open the tile and its adjacent tiles after primary mouse click
            if (event.getButton() == MouseButton.PRIMARY && !isFlagged) {
                if (!board.firstClick) {
                    board.boardSetUp(this);
                }
                clearArea();
            }

            // Place or remove a flag marker on the game board on a secondary mouse click
            else if (!isClicked && board.firstClick && event.getButton() == MouseButton.SECONDARY) {

                // Place down a flag marker on the tile and decrement the total mine count
                if (!isFlagged) {
                    button.setGraphic(icon.getFlag());
                    isFlagged = true;
                    board.mineCount -= 1;


                // Remove a flag marker on the tile and increment the total mine count
                } else {
                    button.setGraphic(null);
                    isFlagged = false;
                    board.mineCount += 1;
                }

                if (board.mineCount >= 0)
                    board.setMineCount(board.mineCount);
            }

            // The player wins the game when the total mine count reaches 0
            // and all the tiles not containing a mine is selected
            if (board.mineCount == 0 && board.checkWin()) {
                board.setOutcomeMessage("You Found All The Mines!");
                board.endGame();
            }
        });
    }

    /**
     * Set up the tile to whether it would include a mine or not
     * @param hasMine True if the tile has a mine, else False if the tile does not have a mine
     */
    public void setMines(boolean hasMine) {
        this.hasMine = hasMine;
    }

    /**
     * Clear the tile and the area around the tile
     */
    private void clearArea() {
        if (isClicked)
            return;

        // End the game after a tile containing a mine has been selected, indicate the tile that containing a mine
        if (hasMine) {
            board.revealMines();
            String minePopped = "-fx-background-color: red; -fx-border-width: 1px; -fx-border-color: red;";
            button.setStyle(minePopped);
            board.setOutcomeMessage("You Clicked On A Mine!");
            return;
        }

        // Open the tile that the player selected if it does not contain a flag marker
        if (!isFlagged) {
            isClicked = true;
            if (numOfAdjacentMines - 1 >= 0)
                button.setGraphic(icon.getNumber(numOfAdjacentMines));
            String cleared = "-fx-background-color: #ffffff; -fx-border-width: 1px; -fx-border-color: black;";
            button.setStyle(cleared);
        }

        // Clear the adjacent tiles if the current tile contains no adjacent mines
        if (this.numOfAdjacentMines == 0) {
            board.checkAdjacent(this).forEach(Tile::clearArea);
        }
    }
}
