package pl.questionansweringsystem.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.questionansweringsystem.user.dto.AdminAuthResponse;
import pl.questionansweringsystem.user.dto.UserRequestAttributes;
import pl.questionansweringsystem.user.dto.UserRequest;
import pl.questionansweringsystem.user.dto.UserResponse;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String X_WWW_FORM_URLENCODED_VALUE = "application/x-www-form-urlencoded";

    @Value("${keycloak.admin.auth.url}")
    private String adminAuthUrl;
    @Value("${keycloak.admin.auth.client.id}")
    private String adminAuthClientId;
    @Value("${keycloak.admin.auth.client.secret}")
    private String adminAuthClientSecret;
    @Value("${keycloak.admin.auth.username}")
    private String adminAuthUsername;
    @Value("${keycloak.admin.auth.password}")
    private String adminAuthPassword;
    @Value("${keycloak.admin.auth.granttype}")
    private String adminAuthGrantType;
    @Value("${keycloak.admin.users.url}")
    private String usersUrl;

    private final String ID_CLAIM_VALUE = "id";
    private final String PASSWORD_TYPE_VALUE = "password";

    public Long getId() {
        final Jwt user = (Jwt) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Map<String, Object> customClaims = user.getClaims();
        if (customClaims.containsKey(ID_CLAIM_VALUE)) {
            return Long.valueOf(String.valueOf(customClaims.get(ID_CLAIM_VALUE)));
        }
        return null;
    }

    public void add(UserRequest request) {
        String adminToken = getAdminToken();
        request.getCredentials().get(0).setType(PASSWORD_TYPE_VALUE);
        request.getCredentials().get(0).setTemporary(Boolean.FALSE);
        request.setAttributes(new UserRequestAttributes(getNewUserId(adminToken)));
        request.setEnabled(Boolean.TRUE);
        restTemplate.exchange(usersUrl,
                HttpMethod.POST,
                new HttpEntity<>(request, new HttpHeaders() {{
                    add(AUTHORIZATION, "Bearer " + adminToken);
                }}),
                Object.class);
    }

    private String getAdminToken() {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>() {{
            add("client_id", adminAuthClientId);
            add("client_secret", adminAuthClientSecret);
            add("username", adminAuthUsername);
            add("password", adminAuthPassword);
            add("grant_type", adminAuthGrantType);
        }};
        ResponseEntity<AdminAuthResponse> response = restTemplate.exchange(adminAuthUrl,
                HttpMethod.POST,
                new HttpEntity<>(request, new HttpHeaders() {{
                    add(CONTENT_TYPE, X_WWW_FORM_URLENCODED_VALUE);
                }}),
                AdminAuthResponse.class);
        return response.getBody().getAccessToken();
    }

    private Long getNewUserId(String token) {
        ResponseEntity<UserResponse[]> response = restTemplate.exchange(usersUrl,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders() {{
                    add(AUTHORIZATION, "Bearer " + token);
                }}),
                UserResponse[].class);
        return Arrays.stream(response.getBody())
                .map(r -> r.getAttributes().getId().get(0))
                .map(Long::parseLong)
                .max(Comparator.naturalOrder())
                .orElseThrow(NoSuchElementException::new) + 1;
    }
}
