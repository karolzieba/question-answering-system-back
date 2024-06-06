package pl.questionansweringsystem.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class UserRequestAttributes {

    private List<String> id;

    public UserRequestAttributes(Long id) {
        this.id = Collections.singletonList(String.valueOf(id));
    }
}
