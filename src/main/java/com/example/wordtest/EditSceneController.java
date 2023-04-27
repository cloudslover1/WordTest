package com.example.wordtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditSceneController implements Initializable {
    public Button BackFromEditButton;
    public Button DeleteButton;

    public Button AddButton;

    private static final String IDLE_BUTTON_STYLE_delete = "-fx-background-color:white; -fx-background-radius: 50; -fx-border-radius:50";
    private static final String HOVERED_BUTTON_STYLE_delete = "-fx-background-color:#EBF3F9; -fx-background-radius: 50; -fx-border-radius:50";

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color:#FABF40; -fx-background-radius: 50; -fx-border-radius:50";
    private static final String HOVERED_BUTTON = "-fx-background-color:#FAD835; -fx-background-radius: 50; -fx-border-radius:50";


    //images for button

    Image img = new Image("delete.png");
    ImageView view = new ImageView(img);
    Image img2 = new Image("add.png");
    ImageView view2 = new ImageView(img2);

    public void switchToMainMenu(ActionEvent event) throws IOException {
        //"hello-view.fxml"
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Stage window = (Stage) BackFromEditButton.getScene().getWindow();
        window.setScene(new Scene(root));

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DeleteButton.setGraphic(view);
        DeleteButton.setOnMouseEntered(e -> DeleteButton.setStyle(HOVERED_BUTTON_STYLE_delete));
        DeleteButton.setOnMouseExited(e -> DeleteButton.setStyle(IDLE_BUTTON_STYLE_delete));

        AddButton.setGraphic(view2);
        AddButton.setOnMouseEntered(e -> AddButton.setStyle(HOVERED_BUTTON));
        AddButton.setOnMouseExited(e -> AddButton.setStyle(IDLE_BUTTON_STYLE));

        BackFromEditButton.setOnMouseEntered(e -> BackFromEditButton.setStyle(HOVERED_BUTTON));
        BackFromEditButton.setOnMouseExited(e -> BackFromEditButton.setStyle(IDLE_BUTTON_STYLE));

        //EditButton.setOnMouseEntered(e -> EditButton.setStyle(HOVERED_BUTTON_STYLE));
        //EditButton.setOnMouseExited(e -> EditButton.setStyle(IDLE_BUTTON_STYLE));
    }
}
