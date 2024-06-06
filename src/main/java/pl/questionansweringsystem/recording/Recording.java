package pl.questionansweringsystem.recording;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "RECORDING")
@Data
@NoArgsConstructor
public class Recording {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RECORDING")
    private Long idRecording;
    @Column(name = "FILE_PATH")
    private String filePath;
    @Column(name = "TRANSCRIBED_TEXT")
    private String transcribedText;
    @Column(name = "ANSWER")
    private String answer;
    @Column(name = "CREATE_DATE_TIME")
    private LocalDateTime createDateTime;

    public Recording(String filePath, String transcribedText, String answer, LocalDateTime createDateTime) {
        this.filePath = filePath;
        this.answer = answer;
        this.transcribedText = transcribedText;
        this.createDateTime = createDateTime;
    }
}
