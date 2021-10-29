package com.example.minesweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main program to run the Minesweeper GUI and game
 */
public class Minesweeper extends Application {

    /**
     * Set up the Minesweeper board and the Menu Bar
     * @param stage The window to display the Minesweeper GUI application
     */
    @Override
    public void start(Stage stage) {
        Board board = new Board();

        stage.setTitle("Minesweeper");
        Scene scene = new Scene(board.createGame());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launch the GUI application
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}