package com.example.wordtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    public Button TestButton;
    public Button EditButton;

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color:#FABF40; -fx-background-radius: 15; -fx-border-radius:15";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color:#FAD835; -fx-background-radius: 15; -fx-border-radius:15";


    public void switchToEditScene(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EditScene.fxml")));
        Stage window = (Stage) EditButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void switchToTestingScene(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TestingScene.fxml")));
        Stage window = (Stage) EditButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            TestButton.setOnMouseEntered(e -> TestButton.setStyle(HOVERED_BUTTON_STYLE));
            TestButton.setOnMouseExited(e -> TestButton.setStyle(IDLE_BUTTON_STYLE));

            EditButton.setOnMouseEntered(e -> EditButton.setStyle(HOVERED_BUTTON_STYLE));
            EditButton.setOnMouseExited(e -> EditButton.setStyle(IDLE_BUTTON_STYLE));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}