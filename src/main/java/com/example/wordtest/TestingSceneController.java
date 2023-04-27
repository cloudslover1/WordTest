package com.example.wordtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TestingSceneController {
    public Button BackFromTestionButton;

    public void switchToMainMenu2(ActionEvent event) throws IOException {
        //"hello-view.fxml"
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Stage window = (Stage) BackFromTestionButton.getScene().getWindow();
        window.setScene(new Scene(root));

    }
}
