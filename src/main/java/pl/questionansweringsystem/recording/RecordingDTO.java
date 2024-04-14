package pl.questionansweringsystem.recording;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecordingDTO {

    private Long idRecording;
    private String filePath;
    private String transcribedText;
    private String answer;
    private LocalDateTime createDateTime;

    public RecordingDTO(Recording recording) {
        this.idRecording = recording.getIdRecording();
        this.filePath = recording.getFilePath();
        this.transcribedText = recording.getTranscribedText();
        this.answer = recording.getAnswer();
        this.createDateTime = recording.getCreateDateTime();
    }
}
