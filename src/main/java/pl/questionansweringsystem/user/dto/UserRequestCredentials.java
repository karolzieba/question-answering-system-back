package pl.questionansweringsystem.user.dto;

import lombok.Data;

@Data
public class UserRequestCredentials {

    private Boolean temporary;
    private String type;
    private String value;
}
