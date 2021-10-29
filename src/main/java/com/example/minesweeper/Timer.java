package com.example.minesweeper;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Record the game time of minesweeper
 */
public class Timer {

    /** Display the total game time on the menu bar */
    public Label timerCountLabel;

    /** Keep count the elapsed time in seconds */
    private long time;

    /** Timeline being used to update the game timer */
    private Timeline timer;

    /**
     * Start the game time at 0 seconds and increment the time each second,
     * the game time does not increase at the 60 minutes mark
     */
    public void start() {
        timer = new Timeline();
        time = 1;

        // set the number of time cycle to repeat infinitely
        timer.setCycleCount(-1);

        // Increment the time by one every 1000 millisecond (1 second)
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(1000), e -> {
            if (time <= 3600) {
                changeCurrentTime(time++);
            }
        }));

        timer.play();
    }

    /**
     * After each second, print the current game time using the mm:ss (minute:seconds) format
     * @param seconds The total amount of time passed in seconds
     */
    private void changeCurrentTime(long seconds) {
        long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        long remainingSeconds = seconds - TimeUnit.MINUTES.toSeconds(minutes);
        String time = String.format("%02d:%02d", minutes, remainingSeconds);
        timerCountLabel.setText(time);
    }

    /**
     * Stop the game time
     */
    public void timeStop() {
        timer.stop();
    }
}
