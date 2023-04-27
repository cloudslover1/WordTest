module com.example.wordtest {
    requires javafx.controls;
    requires javafx.fxml;
    requires freetts;
    requires cmu.time.awb;


    opens com.example.wordtest to javafx.fxml;
    exports com.example.wordtest;
}