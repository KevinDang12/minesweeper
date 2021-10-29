package com.example.minesweeper;

import javafx.scene.image.ImageView;

import java.util.*;

/**
 * List of minesweeper game icons to display on the tiles
 */
public class Icons {

    /**
     * Display the Flag icon on the button
     * @return ImageView of the Flag Icon
     */
    public ImageView getFlag() {
        return new ImageView(Objects.requireNonNull(getClass().getResource("/com/example/minesweeper/images/flag.png")).toExternalForm());
    }

    /**
     * Display the Mine icon on the button
     * @return ImageView of the Mine Icon
     */
    public ImageView getMine() {
        return new ImageView(Objects.requireNonNull(getClass().getResource("/com/example/minesweeper/images/bomb.png")).toExternalForm());
    }

    /**
     * Display the number on the button base on the number of adjacent mines around the tile
     * @param index Retrieve the number icon using an index, being the number of mines adjacent around the tile
     * @return An ImageView of the number icon
     */
    public ImageView getNumber(int index) {
        ImageView[] images = numberList();
        return images[index - 1];
    }

    /**
     * Create a list of images containing the number icons, the icons are retrieved based on their index
     * @return An ImageView list of number icons
     */
    private ImageView[] numberList() {
        String path = "/com/example/minesweeper/images/";
        String ext = ".png";
        ImageView[] images = new ImageView[8];

        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource(path + (i+1) + ext)).toExternalForm());
            images[i] = imageView;
        }
        return images;
    }
}
