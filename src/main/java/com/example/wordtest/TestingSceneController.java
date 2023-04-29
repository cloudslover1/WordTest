package com.example.wordtest;

import com.gtranslate.Audio;
import com.gtranslate.Language;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class TestingSceneController<JavaLayerException extends Throwable> {
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

 //   public void letsHearIt2(ActionEvent event) throws IOException, JavaLayerException, javazoom.jl.decoder.JavaLayerException {
  //      InputStream sound = null;
    //    System.out.println("Hello World!");
      //  Audio audio = Audio.getInstance();
        //sound = audio.getAudio("Hello World", Language.ENGLISH);
        //au//dio.play(sound);
        //sound.close();
    //}

    public void switchToMainMenu2(ActionEvent event) throws IOException {
        //"hello-view.fxml"
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Stage window = (Stage) BackFromTestionButton.getScene().getWindow();
        window.setScene(new Scene(root));

    }
}
