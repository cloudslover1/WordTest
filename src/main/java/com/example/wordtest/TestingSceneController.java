package com.example.wordtest;

//import com.gtranslate.Audio;
//import com.gtranslate.Language;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.example.wordtest.EditSceneController.categories;
import static com.example.wordtest.EditSceneController.words;

public class TestingSceneController implements Initializable {
    public Button BackFromTestionButton;
    public TextField speechField;
    public Button soundButton;
    public Label questionWord;
    public Button choice1;
    public Button choice2;
    public Button choice3;
    public VBox containerBox;
    public HBox resultBox;
    public Label resultLabel;//лейбл вывода результата
    public HBox wordContainer;
    public HBox choicesContainer;
    public HBox backButtonContainer;

    public int allQuestions;

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color:#7871FF; -fx-background-radius: 15; -fx-border-radius:15";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color:#8190FF; -fx-background-radius: 15; -fx-border-radius:15";
    public ImageView imageV;

    public TestingSceneController() throws IOException {
    }


    private void changeImage() {
        String imageName = questionWord.getText();
        String imagePathJpg = "images" + File.separator + imageName + ".jpg";
        String imagePathPng = "images" + File.separator + imageName + ".png";

        Image image = null;

        File imageFileJpg = new File(imagePathJpg);
        File imageFilePng = new File(imagePathPng);

        if (imageFileJpg.exists()) {
            image = new Image(imageFileJpg.toURI().toString());
        } else if (imageFilePng.exists()) {
            image = new Image(imageFilePng.toURI().toString());
        }

        if (image != null) {
            imageV.setImage(image);
        } else {
            System.out.println("Изображение не найдено: " + imageName);
        }
    }
    Image img = new Image("sound1.png");
    ImageView view = new ImageView(img);


    //для озвучивания
    /** Метод использующий библиотеку freetts для озвучивания слов*/
    public void letsHearIt(ActionEvent event) {
       System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
       Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
            voice.setRate(200);
            voice.setPitch(130);
            voice.setVolume(3);
            voice.speak(questionWord.getText());
            voice.deallocate();
        }

    }

    public int questionCount;
    public String categoryChoice;

    ObservableList<Word> randomWords = FXCollections.observableArrayList();

    /** Метод для перехода в главное меню*/
    public void switchToMainMenu2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Stage window = (Stage) BackFromTestionButton.getScene().getWindow();
        window.setScene(new Scene(root));

    }

    public void printSettings() {
        System.out.println("Question count: " + questionCount);
        System.out.println("Category choice: " + categoryChoice);
    }

    public void setQuestionCount(int count) {
        questionCount = count;
    }

    public void setCategoryChoice(String category) {
        categoryChoice = category;
    }

    ObservableList<Word> filteredWords;

    /** Метод initialize используется для вызова методов и */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        resultLabel.setVisible(false);

        //обращение к классу DataHolder чтобы достать значения из первой сцены
        questionCount = DataHolder.count;
        categoryChoice = DataHolder.category;

        allQuestions = questionCount;

        //получаем категории из EditController
        ObservableList<String> categories = EditSceneController.getCategories();

        //
        filteredWords = chooseWords(words, categoryChoice);
        displayQuestion(questionCount, filteredWords, questionWord, choice1, choice2, choice3);


        printSettings();//вывод для отладки
        soundButton.setGraphic(view);

        choice1.setOnMouseEntered(e -> choice1.setStyle(HOVERED_BUTTON_STYLE));
        choice1.setOnMouseExited(e -> choice1.setStyle(IDLE_BUTTON_STYLE));

        choice2.setOnMouseEntered(e -> choice2.setStyle(HOVERED_BUTTON_STYLE));
        choice2.setOnMouseExited(e -> choice2.setStyle(IDLE_BUTTON_STYLE));

        choice3.setOnMouseEntered(e -> choice3.setStyle(HOVERED_BUTTON_STYLE));
        choice3.setOnMouseExited(e -> choice3.setStyle(IDLE_BUTTON_STYLE));

        choice3.setOnMouseEntered(e -> choice3.setStyle(HOVERED_BUTTON_STYLE));
        choice3.setOnMouseExited(e -> choice3.setStyle(IDLE_BUTTON_STYLE));

        soundButton.setOnMouseEntered(e -> soundButton.setStyle(HOVERED_BUTTON_STYLE));
        soundButton.setOnMouseExited(e -> soundButton.setStyle(IDLE_BUTTON_STYLE));

        BackFromTestionButton.setOnMouseEntered(e -> BackFromTestionButton.setStyle(HOVERED_BUTTON_STYLE));
        BackFromTestionButton.setOnMouseExited(e -> BackFromTestionButton.setStyle(IDLE_BUTTON_STYLE));
    }




    // Часть с коннектом контроллеров

    // Получаем FXMLLoader
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditScene.fxml"));

    // Загружаем FXML-файл и получаем корневой узел сцены
    Parent root = fxmlLoader.load();

    // Получаем экземпляр контроллера
    EditSceneController EditSceneController = fxmlLoader.getController();

    // Получаем список категорий
    //ObservableList<String> categories = EditSceneController.getCategories();

    ObservableList<String> categoriesForCombobox = categories;


    FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("hello-view.fxml"));
    Parent root2 = fxmlLoader2.load();
    HelloController HelloController = fxmlLoader2.getController();

    //конец коннектов

    public ObservableList<Word> chooseWords(ObservableList<Word> words, String categ) {
        ObservableList<Word> filteredWords = FXCollections.observableArrayList();
        if (categ.equals("Все слова")) {
            filteredWords.addAll(words);
        } else {
            for (Word word : words) {
                if (word.getWordCategory().equals(categ)) {
                    filteredWords.add(word);
                    System.out.println("добавлено слово: " + word.getWord());
                }
            }
        }
        return filteredWords;
    }

    public void displayQuestion(int questionCount, ObservableList<Word> filteredWords, Label questionTextField, Button... answerButtons) {
        if (questionCount > 0) {
            Word word = filteredWords.get(0);
            questionTextField.setText(word.getWord());
            List<Button> buttons = Arrays.asList(answerButtons);
            Collections.shuffle(buttons);
            Set<String> usedTranslations = new HashSet<>();
            for (int i = 0; i < buttons.size(); i++) {
                Button button = buttons.get(i);
                if (button.getId().equals("choice1") || button.getId().equals("choice2") || button.getId().equals("choice3")) {
                    String answer;
                    if (i == 0) {
                        answer = word.getTranslate();
                        usedTranslations.add(answer);
                    } else {
                        String translation;
                        do {
                            translation = words.get((int) (Math.random() * words.size())).getTranslate();
                        } while (usedTranslations.contains(translation));
                        answer = translation;
                        usedTranslations.add(answer);
                    }
                    button.setText(answer);
                }
            }
            filteredWords.remove(0);
        }
        changeImage();
    }

    public int correctAnwsers = 0;

    public void checkAnswer(ObservableList<Word> words, Label questionTextField, Button clickedButton) {
        String question = questionTextField.getText();
        String selectedAnswer = clickedButton.getText();
        for (Word word : words) {
            if (word.getWord().equals(question)) {
                if (word.getTranslate().equals(selectedAnswer)) {
                    System.out.println("Правильно!");
                    correctAnwsers++;
                } else {
                    System.out.println("Неверно!");
                }
                break;
            }
        }
    }

    public void showResultDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("title");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




    public void handleButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();
        switch (clickedButton.getId()) {
            case "choice1":
                System.out.println("Вы нажали на кнопку 1 с текстом: " + buttonText);
                checkAnswer(words, questionWord, choice1);
                break;
            case "choice2":
                System.out.println("Вы нажали на кнопку 2 с текстом: " + buttonText);
                checkAnswer(words, questionWord, choice2);
                break;
            case "choice3":
                System.out.println("Вы нажали на кнопку 3 с текстом: " + buttonText);
                checkAnswer(words, questionWord, choice3);
                break;
            default:
                System.out.println("Неизвестная кнопка с текстом: " + buttonText);
                break;
        }
    }

    public void displayQuestionOnButton(ActionEvent event) {


        handleButtonClick(event);

        if (questionCount > 1) {
            displayQuestion(questionCount, filteredWords, questionWord, choice1, choice2, choice3);
            questionCount--;
            System.out.println("количество оставшихся слов: " + questionCount);

        }
        else {
            wordContainer.setVisible(false);
            choicesContainer.setVisible(false);
            //backButtonContainer.setVisible(false);
            resultLabel.setText("Вы ответили правильно на "+ correctAnwsers + " из "+ allQuestions + " вопросов");
            resultLabel.setVisible(true);
        }
    }
}
