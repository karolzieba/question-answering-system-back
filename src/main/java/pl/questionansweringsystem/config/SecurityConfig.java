package pl.questionansweringsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String REALM_ACCESS_VALUE = "realm_access";
    private final String ROLES_VALUE = "roles";
    private final String ROLE_PREFIX_VALUE = "ROLE_";
    private final String USER_ROLE_VALUE = "user";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/api/user").permitAll()
                        .requestMatchers("/files/**").permitAll()
                        .anyRequest().hasRole(USER_ROLE_VALUE))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwtAuthenticationConverter()))
                .build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(source -> {
            Map<String, Object> realmAccess = source.getClaimAsMap(REALM_ACCESS_VALUE);
            List<String> roles = (List<String>) realmAccess.get(ROLES_VALUE);
            return roles.stream()
                    .map(rn -> new SimpleGrantedAuthority(ROLE_PREFIX_VALUE + rn))
                    .collect(Collectors.toList());
        });
        return jwtConverter;
    }
}