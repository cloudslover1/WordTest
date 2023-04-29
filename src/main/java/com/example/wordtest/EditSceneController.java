package com.example.wordtest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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

    @FXML
    public TableView<Word> tableList;
    @FXML
    public TableColumn<Word, String> wordTable;
    @FXML
    public TableColumn<Word, String> translateTable;
    public TextField wordInput;
    public TextField translateInput;
    public Button AddCategory;
    public Button deleteCategory;
    public ComboBox categoriesList;


    //images for button

    Image img = new Image("delete.png");
    ImageView view = new ImageView(img);
    Image img2 = new Image("add.png");
    ImageView view2 = new ImageView(img2);

    Image img3 = new Image("add2.png");
    ImageView view3 = new ImageView(img3);

    Image img4 = new Image("delete2.png");
    ImageView view4 = new ImageView(img4);

    //List<String> categories;
    ObservableList<String> categories = FXCollections.observableArrayList("Все слова", "Глаголы", "Существительные");



    public void switchToMainMenu(ActionEvent event) throws IOException {
        //"hello-view.fxml"
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Stage window = (Stage) BackFromEditButton.getScene().getWindow();
        window.setScene(new Scene(root));

        // Write the list to a text file
        try {
            writeToTextFile("words.txt", words);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void comboBoxChange(ActionEvent event) {
        categoriesList.setItems(categories);
    }

    ObservableList<Word> words = FXCollections.observableArrayList();//каждый раз создается новый список, нужно вынести его из метода
    public void addWord(ActionEvent event) {
        Word word = new Word(wordInput.getText(), translateInput.getText());
        //words = tableList.getItems();//каждый раз создается новый список, нужно вынести его из метода
        words.add(word);
        tableList.setItems(words);
        wordInput.setText("");
        translateInput.setText("");

    }

    public void removeWord(ActionEvent event){
        int selectedId = tableList.getSelectionModel().getSelectedIndex();
        tableList.getItems().remove(selectedId);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        categoriesList.setItems(categories);
        categoriesList.setValue(categories.get(0));

        wordTable.setCellValueFactory(new PropertyValueFactory<Word, String>("word"));
        translateTable.setCellValueFactory(new PropertyValueFactory<Word, String>("translate"));


        // Now, read the file into a new List<Student>
        ObservableList<Word> inputWords = null;
        try {
            inputWords = readWords("words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        words = inputWords;
        tableList.setItems(words);

        DeleteButton.setGraphic(view);
        DeleteButton.setOnMouseEntered(e -> DeleteButton.setStyle(HOVERED_BUTTON_STYLE_delete));
        DeleteButton.setOnMouseExited(e -> DeleteButton.setStyle(IDLE_BUTTON_STYLE_delete));

        AddButton.setGraphic(view2);
        AddButton.setOnMouseEntered(e -> AddButton.setStyle(HOVERED_BUTTON));
        AddButton.setOnMouseExited(e -> AddButton.setStyle(IDLE_BUTTON_STYLE));

        AddCategory.setGraphic(view3);
        AddCategory.setOnMouseEntered(e -> AddCategory.setStyle(HOVERED_BUTTON));
        AddCategory.setOnMouseExited(e -> AddCategory.setStyle(IDLE_BUTTON_STYLE));

        deleteCategory.setGraphic(view4);
        deleteCategory.setOnMouseEntered(e -> deleteCategory.setStyle(HOVERED_BUTTON));
        deleteCategory.setOnMouseExited(e -> deleteCategory.setStyle(IDLE_BUTTON_STYLE));

        BackFromEditButton.setOnMouseEntered(e -> BackFromEditButton.setStyle(HOVERED_BUTTON));
        BackFromEditButton.setOnMouseExited(e -> BackFromEditButton.setStyle(IDLE_BUTTON_STYLE));

        //EditButton.setOnMouseEntered(e -> EditButton.setStyle(HOVERED_BUTTON_STYLE));
        //EditButton.setOnMouseExited(e -> EditButton.setStyle(IDLE_BUTTON_STYLE));
    }

    public void addCategory(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog(); // создание диалогового окна
        dialog.setTitle("Добавить элемент");
        dialog.setHeaderText("Введите название новой категории");
        dialog.setContentText("Категория:");

        dialog.showAndWait().ifPresent(item -> { // отображение диалогового окна и добавление элемента в список
            if (!item.trim().isEmpty()) { // проверка на пустую строку
                categories.add(item.trim());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Строка не может быть пустой!");
                alert.showAndWait();
            }
        });
    }

    public void removeCategory(ActionEvent event) {
        String selectedItem = (String) categoriesList.getSelectionModel().getSelectedItem(); // получение выбранного элемента из ComboBox
        if (selectedItem != null && !selectedItem.equals("Все слова")) { // проверка на null и на "Item 1"
            int selectedIndex = categoriesList.getSelectionModel().getSelectedIndex(); // получение индекса выбранного элемента в ComboBox
            categories.remove(selectedItem); // удаление элемента из списка
            categoriesList.getSelectionModel().clearSelection(); // очистка выбора ComboBox
            int itemsSize = categories.size();
            if (selectedIndex < itemsSize) {
                categoriesList.getSelectionModel().select(selectedIndex); // выбор следующего элемента
            } else if (itemsSize > 0) {
                categoriesList.getSelectionModel().select(itemsSize - 1); // выбор предыдущего элемента
            }
        }
    }

    /**
     * Write the list of students to a simple text file with first and last names separated by a comma
     */
    private static void writeToTextFile(String filename, ObservableList<Word> words)
            throws IOException {

        FileWriter writer = new FileWriter(filename);
        for (Word word : words) {
            writer.write(word.getWord() + "," + word.getTranslate() + "\n");
        }
        writer.close();
    }

    /**
     * Read the comma-separated list of student names from the text file
     */
    private static ObservableList<Word> readWords(String filename) throws IOException {

        ObservableList<Word> words1 = FXCollections.observableArrayList();

        BufferedReader reader = Files.newBufferedReader(Paths.get(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] names = line.split(",");

            // Add the student to the list
            words1.add(new Word(names[0], names[1]));

        }

        return words1;
    }
}
