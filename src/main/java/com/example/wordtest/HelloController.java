package com.example.wordtest;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.example.wordtest.EditSceneController.loadListFromFile;
import static com.example.wordtest.EditSceneController.readWords;

public class HelloController implements Initializable {

    public Button TestButton;
    public Button EditButton;

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color:#7871FF; -fx-background-radius: 15; -fx-border-radius:15";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color:#8190FF; -fx-background-radius: 15; -fx-border-radius:15";
    public ComboBox catComboBox;
    public TextField count;
    public Button exitButton;
    public Button TestButton1;

    ObservableList<Word> words1 = EditSceneController.getWords();

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

    public void switchToTest() throws IOException {

        DataHolder.count = Integer.parseInt(count.getText());
        DataHolder.category = (String) catComboBox.getValue();


        boolean result = checkWordCountByCategory(words1, DataHolder.count, DataHolder.category); // вызываем метод getTrueFalse и сохраняем его результат в переменную
        if (result) {
            switchToTestingScene();
        } else {
            showErrorDialog(categoryCount(words1, (String) catComboBox.getValue()));
        }
    }

    public void switchToTestingScene() throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TestingScene.fxml")));
        Stage window = (Stage) EditButton.getScene().getWindow();
        window.setScene(new Scene(root));

    }

    public void showErrorDialog(int categoryCount) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText("В выбранной категории содержится только " + categoryCount + " слов. Измените количество");
        alert.showAndWait();
    }

    public boolean checkWordCountByCategory(ObservableList<Word> words, int count, String category) {
        System.out.println("переданная категория: " + category);
        System.out.println("сравнить с этим: " + count);
        int categoryCount = 0;
        if (category.equals("Все слова")) {
            categoryCount = words.size();
        } else {
            for (Word word : words) {
                if (word.getWordCategory().equals(category)) {
                    categoryCount++;
                }
            }
        }
        System.out.println("это: " + categoryCount);
        return count <= categoryCount;
    }

    public int categoryCount(ObservableList<Word> words, String category) {
        int count = 0;
        for (Word word : words) {
            if (word.getWordCategory().equals(category)) {
                count++;
            }
        }
        System.out.println("количество: " + count);
        return count;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ObservableList<Word> inputWords = null;
        try {
            inputWords = readWords("words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        words1 = inputWords;



        catComboBox.getItems().clear();
        if (editSceneController.flag == 0) {
            loadListFromFile();
            editSceneController.flag = 1;
        }

        setCategories();



        EditButton.setOnMouseEntered(e -> EditButton.setStyle(HOVERED_BUTTON_STYLE));
        EditButton.setOnMouseExited(e -> EditButton.setStyle(IDLE_BUTTON_STYLE));

        exitButton.setOnMouseEntered(e -> exitButton.setStyle(HOVERED_BUTTON_STYLE));
        exitButton.setOnMouseExited(e -> exitButton.setStyle(IDLE_BUTTON_STYLE));

        TestButton1.setOnMouseEntered(e -> TestButton1.setStyle(HOVERED_BUTTON_STYLE));
        TestButton1.setOnMouseExited(e -> TestButton1.setStyle(IDLE_BUTTON_STYLE));


    }

    EditSceneController editSceneController = new EditSceneController();

    ObservableList<String> categoriesForComboBox = EditSceneController.categories;

    //для проверки того что введенное количество слов не превышает количество слов в категории

    //ObservableList<Word> words1 = EditSceneController.getWords();



    static ObservableList<Word> readWords(String filename) throws IOException {

        ObservableList<Word> words1 = FXCollections.observableArrayList();

        BufferedReader reader = Files.newBufferedReader(Paths.get(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] names = line.split(",");

            // Add the student to the list
            words1.add(new Word(names[0], names[1], names[2]));

        }

        return words1;
    }

    public void setCategories() {
        // удаляем дубликаты из списка
        List<String> distinctCategories = categoriesForComboBox.stream()
                .distinct()
                .collect(Collectors.toList());

        // устанавливаем значения ComboBox
        catComboBox.getItems().clear();
        catComboBox.getItems().addAll(distinctCategories);
        catComboBox.getSelectionModel().selectFirst();
    }






    public void closeApplication() {
        Platform.exit();
    } //функция выхода из приложения
}