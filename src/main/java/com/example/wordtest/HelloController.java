package com.example.wordtest;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static antlr.build.ANTLR.root;
import static com.example.wordtest.EditSceneController.loadListFromFile;

public class HelloController implements Initializable {

    public Button TestButton;
    public Button EditButton;

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color:#FABF40; -fx-background-radius: 15; -fx-border-radius:15";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color:#FAD835; -fx-background-radius: 15; -fx-border-radius:15";
    public ComboBox catComboBox;
    public TextField count;
    public ChoiceBox choiceBox;


    public String getComboBoxValue() {
        return catComboBox.getSelectionModel().toString();
    }

    public int getTextFieldValue() {
        String value = count.getText();
        try {
            int intValue = Integer.parseInt(value);
            return intValue;
        } catch (NumberFormatException e) {
            // Обработка ошибки, если в текстовом поле не число
            return 0;
        }
    }



    public void switchToEditScene(ActionEvent event) throws IOException{
        editSceneController.flag = 1;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EditScene.fxml")));
        Stage window = (Stage) EditButton.getScene().getWindow();
        window.setScene(new Scene(root));

    }

    //ComboBox<String> comboBox = catComboBox; // замените "myComboBox" на ID своего combobox
    //String selectedItem = comboBox.getSelectionModel().getSelectedItem().toString();

    public void checkAndGoToTest() throws IOException {

        int countQ = Integer.parseInt(count.getText());

        //String selectedValue = choiceBox.getValue().toString();
        String selectedValue2 = (String) catComboBox.getValue();

        System.out.println("Выбраная категория: " + selectedValue2 );


        FXMLLoader loader = new FXMLLoader(getClass().getResource("TestingScene.fxml"));
        Parent root = loader.load();

        TestingSceneController testingSceneController = loader.getController();

        // передача количества слов и категории в тестинг контроллер
        testingSceneController.setQuestionCount(Integer.parseInt(count.getText()));
        testingSceneController.setCategoryChoice(selectedValue2);


        String input = count.getText();

        try {
            int value = Integer.parseInt(input);
            switchToTestingScene();
            System.out.println("Введено число: " + value);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка: в поле должно быть введено число", ButtonType.OK);
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToTestingScene() throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TestingScene.fxml")));
        Stage window = (Stage) EditButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        catComboBox.getItems().clear();
        if (editSceneController.flag == 0) {
            loadListFromFile();
            editSceneController.flag = 1;
        }

        setCategories();


        try {
            TestButton.setOnMouseEntered(e -> TestButton.setStyle(HOVERED_BUTTON_STYLE));
            TestButton.setOnMouseExited(e -> TestButton.setStyle(IDLE_BUTTON_STYLE));

            EditButton.setOnMouseEntered(e -> EditButton.setStyle(HOVERED_BUTTON_STYLE));
            EditButton.setOnMouseExited(e -> EditButton.setStyle(IDLE_BUTTON_STYLE));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    EditSceneController editSceneController = new EditSceneController();

    ObservableList<String> categoriesForComboBox = EditSceneController.categories;

    public void setCategories() {
        // удаляем дубликаты из списка
        List<String> distinctCategories = categoriesForComboBox.stream()
                .distinct()
                .collect(Collectors.toList());

        // устанавливаем значения ComboBox
        catComboBox.getItems().clear();
        catComboBox.getItems().addAll(distinctCategories);
        catComboBox.getSelectionModel().selectFirst();

        choiceBox.getItems().clear();
        choiceBox.getItems().addAll(distinctCategories);
        choiceBox.getSelectionModel().selectFirst();
    }






    public void closeApplication() {
        Platform.exit();
    } //функция выхода из приложения
}