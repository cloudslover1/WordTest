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

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color:#FABF40; -fx-background-radius: 15; -fx-border-radius:15";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color:#FAD835; -fx-background-radius: 15; -fx-border-radius:15";
    public ComboBox catComboBox;
    public TextField count;

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


/*
    public void checkAndGoToTest() throws IOException {


        //int countQ = Integer.parseInt(count.getText());

        //String selectedValue = choiceBox.getValue().toString();
        String selectedValue2 = (String) catComboBox.getValue();

        DataHolder.count = Integer.parseInt(count.getText());
        DataHolder.category = (String) catComboBox.getValue();

        //System.out.println("Выбраная категория: " + selectedValue2 );


        //FXMLLoader loader = new FXMLLoader(getClass().getResource("TestingScene.fxml"));
        //Parent root = loader.load();

        //TestingSceneController testingSceneController = loader.getController();

        // передача количества слов и категории в тестинг контроллер
        //testingSceneController.setQuestionCount(Integer.parseInt(count.getText()));
        //testingSceneController.setCategoryChoice(selectedValue2);


        //String input = count.getText();

        //ObservableList<Word> words = editSceneController.words;
        try {
            int value = Integer.parseInt(String.valueOf(Integer.parseInt(count.getText())));
            if (checkWordCountByCategory(words1, DataHolder.count, DataHolder.category)){

                System.out.println("Введено число: " + value);
                System.out.println("aaaaaaaaaaaa: " + checkWordCountByCategory(words1, DataHolder.count, DataHolder.category));
                switchToTestingScene();
            } else {
                showErrorDialog(categoryCount(words1, (String) catComboBox.getValue()));
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка: в поле должно быть введено число", ButtonType.OK);
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
*/
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
        for (Word word : words) {
            if (word.getWordCategory().equals(category)) {
                categoryCount++;
            }
        }
        System.out.println("это: " + categoryCount);
        return count  <= categoryCount;



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