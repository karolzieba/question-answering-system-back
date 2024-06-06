package pl.questionansweringsystem.questionAnswering;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class QuestionAnsweringService {

    public String getAnswer(String question) {
        RestTemplate rtTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Ocp-Apim-Subscription-Key", "");
        headers.add("Content-Type", "application/json");
        Request request = new Request(question);
        ResponseEntity <Response> response = rtTemplate.exchange("",
        HttpMethod.POST, new HttpEntity<>(request, headers), Response.class);
        return response.getBody().getAnswers().get(0).getAnswer();
    }
}
