package com.example.minesweeper;

import javafx.scene.control.Button;

public class Reset {

    Button btnReset = new Button("Reset");
    Game game = new Game();

    public void restart() {
        for (Button[] button : Minesweeper.buttons) {
            for (Button b : button) {
                b.setStyle("-fx-background-color: #D3D3D3; -fx-border-width: 1px; -fx-border-color: black; ");
                b.setText("");
            }
        }

        Game.firstClick = false;

        // Reset Timer

        // Reset Mine Count

        // Reset Game

    }
}
