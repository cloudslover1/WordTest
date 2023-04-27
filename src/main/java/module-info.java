module com.example.wordtest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wordtest to javafx.fxml;
    exports com.example.wordtest;
}