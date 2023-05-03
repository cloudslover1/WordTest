package com.example.wordtest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EditSceneController implements Initializable {
    static final ObservableList<String> categories = FXCollections.observableArrayList();
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
    //public ObservableList<String> categories = FXCollections.observableArrayList();

    public static ObservableList<String> getCategories() {
        return categories;
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        flag = 1;
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


    static ObservableList<Word> words = FXCollections.observableArrayList();//каждый раз создается новый список, нужно вынести его из метода

    public static ObservableList<Word> getWords() {
        return words;
    }
    public void addWord(ActionEvent event) {
        Word word = new Word(wordInput.getText(), translateInput.getText(), (String) categoriesList.getValue());
        //words = tableList.getItems();//каждый раз создается новый список, нужно вынести его из метода
        words.add(word);
        //tableList.setItems(words);
        changeCategory(event);
        wordInput.setText("");
        translateInput.setText("");

    }

    public void removeWord(ActionEvent event) {
        Word selectedWord = tableList.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            tableList.getItems().remove(selectedWord);
            words.remove(selectedWord);
        }
    }

    public int flag = 0;

    public void setCategories() {
        // удаляем дубликаты из списка
        List<String> distinctCategories = categories.stream()
                .distinct()
                .collect(Collectors.toList());

        // устанавливаем значения ComboBox
        categoriesList.getItems().clear();
        categoriesList.getItems().addAll(distinctCategories);
        categoriesList.getSelectionModel().selectFirst();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //loadListFromFile();


        categoriesList.setItems(categories);
        categoriesList.setValue(categories.get(0));

        setCategories();


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
                if (!item.trim().equals("Все слова")) { // проверка на значение "Все слова"
                    categories.add(item.trim());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(null);
                    alert.setContentText("Значение 'Все слова' недопустимо!");
                    alert.showAndWait();
                }
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

            // Удаление всех объектов в листе, у которых значение поля wordCategory совпадает со значением удаляемого элемента
            words.removeIf(word -> word.getWordCategory().equals(selectedItem));

            categoriesList.getSelectionModel().clearSelection(); // очистка выбора ComboBox
            int itemsSize = categories.size();
            if (selectedIndex < itemsSize) {
                categoriesList.getSelectionModel().select(selectedIndex); // выбор следующего элемента
            } else if (itemsSize > 0) {
                categoriesList.getSelectionModel().select(itemsSize - 1); // выбор предыдущего элемента
            }
        }
    }


    public void changeCategory(ActionEvent event){
        wordTable.setCellValueFactory(new PropertyValueFactory<>("word"));
        translateTable.setCellValueFactory(new PropertyValueFactory<>("translate"));

        // Получаем текущее значение ComboBox
        String currentCategory = (String) categoriesList.getValue();

        // Создаем новый список для объектов, соответствующих текущей категории
        ObservableList<Word> filteredList = FXCollections.observableArrayList();

        // Если выбрано значение "Все слова", добавляем все слова в список
        if (currentCategory.equals("Все слова")) {
            filteredList.addAll(words);
        } else {
            // Наполняем новый список объектами, соответствующими текущей категории
            for (Word word : words) {
                if (word.getWordCategory().equals(currentCategory)) {
                    filteredList.add(word);
                }
            }

            // Сортируем список по значению поля wordCategory
            filteredList.sort(Comparator.comparing(Word::getWordCategory));
        }

        // Привязываем список к TableView
        tableList.setItems(filteredList);

        categoriesList.setItems(categories);

        System.out.println("Метод сработал");
    }

    /**
     * Write the list of students to a simple text file with first and last names separated by a comma
     */
    private static void writeToTextFile(String filename, ObservableList<Word> words)
            throws IOException {

        FileWriter writer = new FileWriter(filename);
        for (Word word : words) {
            writer.write(word.getWord() + "," + word.getTranslate() + ","  + word.getWordCategory() + "\n");
        }
        writer.close();
    }

    /**
     * Read the comma-separated list of student names from the text file
     */
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

    public void saveListToFile() {
        try {
            File file = new File("myList.txt"); // создание объекта File с именем файла
            FileWriter writer = new FileWriter(file, false); // создание FileWriter, перезаписывающего файл
            for (String item : categories) { // перебор элементов списка
                writer.write(item + "\n"); // запись элемента в файл
            }
            writer.flush(); // запись буферизованных данных в файл
            writer.close(); // закрытие FileWriter
            //System.out.println("List saved to file successfully.");
        } catch (IOException e) {
            //System.out.println("An error occurred while saving the list to file.");
            e.printStackTrace();
        }
    }
    public static void loadListFromFile() {
        try {
            File file = new File("myList.txt"); // создание объекта File с именем файла
            BufferedReader reader = new BufferedReader(new FileReader(file)); // создание BufferedReader
            String line;
            while ((line = reader.readLine()) != null) { // чтение файла построчно
                categories.add(line); // добавление элемента в список
            }
            reader.close(); // закрытие BufferedReader
            System.out.println("List loaded from file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading the list from file.");
            e.printStackTrace();
        }
    }
}
