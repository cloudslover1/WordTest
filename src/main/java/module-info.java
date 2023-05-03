module com.example.wordtest {
    requires javafx.controls;
    requires javafx.fxml;
    //requires freetts;
    //requires cmu.time.awb;
    //requires gtranslateapi;
    requires java.logging;
    requires antlr;
    requires freetts;


    opens com.example.wordtest to javafx.fxml;
    exports com.example.wordtest;
}