package pl.questionansweringsystem.questionAnswering;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response {
    private List<Answer> answers;
    @Data
    @NoArgsConstructor
    public static class Answer {
        private String answer;
    }
}
