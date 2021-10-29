module com.example.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.minesweeper to javafx.fxml;
    exports com.example.minesweeper;
}