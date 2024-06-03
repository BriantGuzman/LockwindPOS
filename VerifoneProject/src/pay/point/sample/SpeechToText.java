package pay.point.sample;

/*
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.speech.*;
import javax.speech.recognition.*;

public class SpeechToText {

  public static void main(String[] args) {

    try {
      // Set up the speech recognition engine
      Central.registerEngineCentral(
          "com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
      Synthesizer synth = Central.createSynthesizer(null);
      synth.allocate();
      synth.resume();
      Recognizer rec = Central.createRecognizer(null);
      rec.allocate();

      // Create a new file to store the transcribed text
      FileWriter writer = new FileWriter("transcription.txt");

      // Define the grammar for the speech recognition engine
      String grammar = "#JSGF V1.0; grammar phrase; public <phrase> = (hello | goodbye | how are you);";
      rec.loadJSGF(new StringGrammar("phrase", grammar));

      // Start listening for user input
      rec.addResultListener(new ResultAdapter() {
        public void resultAccepted(ResultEvent event) {
          try {
            // Get the transcribed text and write it to the file
            Result result = (Result) event.getSource();
            String text = result.getBestFinalResultNoFiller();
            writer.write(text);
            writer.flush();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });
      rec.commitChanges();
      rec.requestFocus();
      System.out.println("Speak now...");

      // Wait for the user to finish speaking
      Scanner scanner = new Scanner(System.in);
      scanner.nextLine();

      // Clean up resources
      writer.close();
      rec.deallocate();
      synth.deallocate();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

*/