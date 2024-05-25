package pl.questionansweringsystem.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserRequest {

    private String username;
    private List<UserRequestCredentials> credentials;
    private UserRequestAttributes attributes;
    private Boolean enabled;
}
