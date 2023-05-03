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
    public Label resultLabel;
    public HBox wordContainer;
    public HBox choicesContainer;
    public HBox backButtonContainer;


    public TestingSceneController() throws IOException {
    }


    Image img = new Image("sound1.png");
    ImageView view = new ImageView(img);

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

    //public void letsHearIt2(ActionEvent event) throws IOException, JavaLayerException, javazoom.jl.decoder.JavaLayerException {
   //     InputStream sound = null;
    //    System.out.println("Hello World!");
   //     Audio audio = Audio.getInstance();
    //    sound = audio.getAudio("Hello World", Language.ENGLISH);
    //    audio.play(sound);
   //     sound.close();
   // }

    public int questionCount = 5;
    public String categoryChoice = "Все слова";

    ObservableList<Word> randomWords = FXCollections.observableArrayList();

    public void switchToMainMenu2(ActionEvent event) throws IOException {
        //"hello-view.fxml"
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Stage window = (Stage) BackFromTestionButton.getScene().getWindow();
        window.setScene(new Scene(root));

    }

    public void printSettings() {
        System.out.println("Question count: " + questionCount);
        System.out.println("Category choice: " + categoryChoice);
        //printRandomWordList1(questionCount, categoryChoice);
        //setQuestionAndChoices(randomWords);
    }

    public void setQuestionCount(int count) {
        questionCount = count;
    }

    public void setCategoryChoice(String category) {
        categoryChoice = category;
    }

    ObservableList<Word> filteredWords;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        resultLabel.setVisible(false);
        //loadListFromFile();

        //questionCount = HelloController.getTextFieldValue();
        //categoryChoice = HelloController.getComboBoxValue();


        //тут написан какой то полный булщит, попытайся сделать через новую сцену


        ObservableList<String> categories = EditSceneController.getCategories();
        //randomWords = generateRandomWordList(words, questionCount, categoryChoice);


        //generateRandomWordList(5, "Все слова");
        questionCount = 4;
        categoryChoice = "новая категория";


        filteredWords = chooseWords(words, categoryChoice);
        displayQuestion(questionCount, filteredWords, questionWord, choice1, choice2, choice3);

        //randomWords = generateRandomWordList(words, questionCount, categoryChoice);
        //setQuestionAndChoices(randomWords);
        //setQuestionAndChoices(randomWords);
        printSettings();


        soundButton.setGraphic(view);
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

    /*
    public static ObservableList<Word> generateRandomWordList(ObservableList<Word> words, int count, String category) {
        if (words.isEmpty()) {
            throw new IllegalArgumentException("Input list cannot be empty");
        }

        Random rand = new Random();
        ObservableList<Word> newWords = FXCollections.observableArrayList();
        int wordsAdded = 0;
        while (wordsAdded < count) {
            int index = rand.nextInt(words.size());
            Word word = words.get(index);
            if (word.getWordCategory().equals(category)) {
                newWords.add(new Word(word.getWord(), word.getTranslate(), word.getWordCategory()));
                System.out.println("Добавлено слово: " + word.getWord().toString());
                System.out.println(wordsAdded);
                wordsAdded++;
            }
        }
        return newWords;
    }
*/
    public ObservableList<Word> chooseWords(ObservableList<Word> words, String categ) {
        ObservableList<Word> filteredWords = FXCollections.observableArrayList();
        for (Word word : words) {
            if (word.getWordCategory().equals(categ)) {
                filteredWords.add(word);
                System.out.println("добавлено слово: " + word.getWord());
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
            questionCount--;
            filteredWords.remove(0);
        }
    }


    public void checkAnswer(ObservableList<Word> words, Label questionTextField, Button clickedButton) {
        String question = questionTextField.getText();
        String selectedAnswer = clickedButton.getText();
        for (Word word : words) {
            if (word.getWord().equals(question)) {
                if (word.getTranslate().equals(selectedAnswer)) {
                    System.out.println("Правильно!");
                } else {
                    System.out.println("Неверно!");
                }
                break;
            }
        }
    }



/*

    public void setQuestionAndChoices(ObservableList<Word> wordsList, ObservableList<Word> choicesList) {
        // Выбираем первый элемент из списка слов и присваиваем его значение полю вопроса
        Word questionWord = wordsList.get(0);
        //questionWord.setText(questionWord.getWord());

        // Получаем случайные объекты из списка вариантов ответа
        Random random = new Random();
        Word choiceWord1 = choicesList.get(random.nextInt(choicesList.size()));
        Word choiceWord2 = choicesList.get(random.nextInt(choicesList.size()));

        // Задаем значения поля translate для кнопки 1
        choice1.setText(choiceWord1.getTranslate());

        // Задаем значения поля translate для кнопки 2
        choice2.setText(choiceWord2.getTranslate());

        // Выбираем случайный элемент из списка вариантов ответа, не совпадающий с choiceWord1 и choiceWord2,
        // и задаем его значение поля translate для кнопки 3
        Word choiceWord3;
        do {
            choiceWord3 = choicesList.get(random.nextInt(choicesList.size()));
        } while (choiceWord3.equals(choiceWord1) || choiceWord3.equals(choiceWord2));
        choice3.setText(choiceWord3.getTranslate());
    }

    public void setQuestionAndChoices(ObservableList<Word> randomWords) {
        // Берем объект списка случайных слов по индексу
        Word word = randomWords.get(0);

        // Меняем текст лейбла на значение поля word объекта
        questionWord.setText(word.getWord());

        // Получаем список кнопок
        List<Button> choices = new ArrayList<>();
        choices.add(choice1);
        choices.add(choice2);
        choices.add(choice3);

        // Выбираем случайную кнопку и меняем ее текст на значение поля translate
        Button randomButton = choices.get((int) (Math.random() * 3));
        randomButton.setText(word.getTranslate());

        // Удаляем слово, которое уже использовали из списка
        randomWords.remove(word);

        // Получаем случайные слова из списка для оставшихся кнопок
        List<Word> remainingWords = new ArrayList<>(randomWords);

        // Находим оставшиеся кнопки и меняем их на значения поля translate у случайных слов
        for (Button button : choices) {
            if (button == randomButton) {
                continue; // Пропускаем кнопку, которую уже заполнили
            }
            Word randomWord = remainingWords.get((int) (Math.random() * remainingWords.size()));
            button.setText(randomWord.getTranslate());
            remainingWords.remove(randomWord);
        }

        // Добавляем обработчики для кнопок выбора ответа
        for (Button button : choices) {
            button.setOnAction(event -> {
                if (button.getText().equals(word.getTranslate())) {
                    showResultDialog("Правильно!");
                } else {
                    showResultDialog("Неправильно. Правильный ответ: " + word.getTranslate());
                }

                // Если список случайных слов пустой, то добавляем все слова из words и перемешиваем их
                if (randomWords.isEmpty()) {
                    randomWords.addAll(words);
                    Collections.shuffle(randomWords);
                }

                // Устанавливаем новый вопрос и варианты ответов
                setQuestionAndChoices(randomWords);
            });
        }
    }
*/
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

        if (questionCount > 0) {
            displayQuestion(questionCount, filteredWords, questionWord, choice1, choice2, choice3);
            questionCount--;
            System.out.println("количество оставшихся слов: " + questionCount);

        }
        else {
            wordContainer.setVisible(false);
            choicesContainer.setVisible(false);
            backButtonContainer.setVisible(false);
            resultLabel.setVisible(true);
        }
    }



    //public void setQuestionAndChoices1(ActionEvent event) {
    //    setQuestionAndChoices(randomWords);
    //}
}
