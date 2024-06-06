package pl.questionansweringsystem.recording.dto;

import lombok.Data;
import pl.questionansweringsystem.recording.Recording;

import java.time.LocalDateTime;

@Data
public class RecordingResponse {

    private Long idRecording;
    private String filePath;
    private String transcribedText;
    private String answer;
    private LocalDateTime createDateTime;

    public RecordingResponse(Recording recording) {
        this.idRecording = recording.getIdRecording();
        this.filePath = recording.getFilePath();
        this.transcribedText = recording.getTranscribedText();
        this.answer = recording.getAnswer();
        this.createDateTime = recording.getCreateDateTime();
    }
}
