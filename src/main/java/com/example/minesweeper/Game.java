package com.example.minesweeper;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    // Game Setup
    int mines = 20;
    boolean win = false;
    static boolean firstClick = false;
    Random rand = new Random();
    Button button;
    static Boolean[][] mineSet;
    static Boolean[][] isClicked;

    int [] area = new int[] {
            -1, -1,
            -1, 0,
            -1, 1,
            0, -1,
            0, 1,
            1, -1,
            1, 0,
            1, 1,
    };

    // Game rules
    public void startGame() {
        // when user click on board initialize game by adding mines to board game
//        System.out.println(row + " " + col);

        mineSet = new Boolean[Minesweeper.SIZE][Minesweeper.SIZE];
        isClicked = new Boolean[Minesweeper.SIZE][Minesweeper.SIZE];

        for (int r = 0; r < Minesweeper.SIZE; r++) {
            for (int c = 0; c < Minesweeper.SIZE; c++) {
                mineSet[r][c] = false;
                isClicked[r][c] = false;
            }
        }
        int i = 0;
        while (i < mines) {
            int row = rand.nextInt(Minesweeper.SIZE);
            int col = rand.nextInt(Minesweeper.SIZE);

            if (!mineSet[row][col]) {
                mineSet[row][col] = true;
                i++;
            }
        }
    }

    public void open(int x, int y) {
        Button button = Minesweeper.buttons[x][y];

        if (isClicked[x][y])
            return;

        isClicked[x][y] = true;
        if (button.getText().equals("0")) {
            button.setStyle(" -fx-background-color: #ffffff; -fx-border-width: 1px; -fx-border-color: black; -fx-text-fill: black");
        }

        if (button.getText().equals("0")) {
            // Check adjacent tiles
            for (int i = 0; i < area.length; i++) {
                checkAdjacent(area[i], area[++i]);
                open(area[i], area[++i]);
            }
        }
    }

    public List<Boolean> checkAdjacent(int row, int col) {

        List<Boolean> adjacent = new ArrayList<>();

        for (int i = 0; i < area.length; i++) {
            int adjacentX = area[i];
            int adjacentY = area[++i];

            int currentX = adjacentX + row;
            int currentY = adjacentY + col;

            if (currentX >= 0 && currentY >= 0 && currentX < Minesweeper.SIZE && currentY < Minesweeper.SIZE) {
                if (mineSet[currentX][currentY]) {
                    adjacent.add(mineSet[row][col]);
                }
            }
        }
        return adjacent;
    }

//    public void clearArea(Button[][] buttons, int row, int col) {
//
//        if (!firstClick) {
//            firstClick = true;
//            startGame();
//        }
//
//        if (row < 0 || col < 0 || row >= Minesweeper.SIZE || col >= Minesweeper.SIZE || buttons[row][col].getStyle().
//                contains("-fx-background-color: #ffffff; -fx-border-width: 1px; -fx-border-color: black; ") || mineSet[row][col]) {
//            // Add condition for mines in a specific area
//            return;
//        }
//
//        button = buttons[row][col];
//        button.setStyle("-fx-background-color: #ffffff; -fx-border-width: 1px; -fx-border-color: black; ");
//
//        long count = checkAdjacent(row, col).stream().count();
//        if (count > 0) {
//            button.setText(count + "");
//            return;
//        }
//
////        System.out.println(row + " " + col);
//        clearArea(buttons, row + 1, col);
//        clearArea(buttons, row, col + 1);
//        clearArea(buttons, row, col - 1);
//        clearArea(buttons, row - 1, col);
//    }

//    public static List<Board> checkAdjacent(Board tile) {
//
//        List<Board> grid = new ArrayList<>();
//
//        int [] area = new int[] {
//                -1, -1,
//                -1, 0,
//                -1, 1,
//                0, -1,
//                0, 0,
//                0, 1,
//                1, -1,
//                1, 0,
//                1, 1,
//        };
//
//        for (int i = 0; i < area.length; i++) {
//            int adjacentX = area[i];
//            int adjacentY = area[++i];
//
//            int currentX = adjacentX + tile.x;
//            int currentY = adjacentY + tile.y;
//
//            if (currentX >= 0 && currentY >= 0
//                    && currentX < X_TILE && currentY < Y_TILE) {
//                grid.add(board[currentX][currentY]);
//            }
//        }
//        return grid;
//    }
    // Return Number of Mines around the tile

    // Display number of Mines on Tile

    // Game methods

}
