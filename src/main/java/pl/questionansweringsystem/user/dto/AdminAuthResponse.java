package pl.questionansweringsystem.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdminAuthResponse {

    @JsonProperty("access_token")
    private String accessToken;
}
