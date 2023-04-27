package com.example.wordtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class TestingSceneController {
    public Button BackFromTestionButton;
    public TextField speechField;




    public void letsHearIt(ActionEvent event) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
            voice.setRate(200);
            voice.setPitch(130);
            voice.setVolume(3);
            voice.speak(speechField.getText());
            voice.deallocate();
        }

    }

    public void switchToMainMenu2(ActionEvent event) throws IOException {
        //"hello-view.fxml"
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Stage window = (Stage) BackFromTestionButton.getScene().getWindow();
        window.setScene(new Scene(root));

    }
}
