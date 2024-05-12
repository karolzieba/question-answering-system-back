package pl.questionansweringsystem.speechtotext;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class SpeechToTextService {

    public String recognizeTextFromFile(String filePath) throws Exception {
        String speechKey = System.getenv("SPEECH_KEY");
        String speechRegion = System.getenv("SPEECH_REGION");
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
        speechConfig.setSpeechRecognitionLanguage("en-US");
        AudioConfig audioConfig = AudioConfig.fromWavFileInput(filePath);
        SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig);
        Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
        SpeechRecognitionResult speechRecognitionResult = task.get();
        if (speechRecognitionResult.getReason() == ResultReason.NoMatch) {
            throw new Exception("Speech could not be recognized.");
        }
        else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
            CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);
            throw new Exception("Speech recognition from file canceled. Reason: "
                    + cancellation.getReason()
                    + " | Error details: " + cancellation.getErrorDetails() + ".");
        }
        return speechRecognitionResult.getText();
    }
}
