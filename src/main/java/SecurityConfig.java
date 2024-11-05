import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/Foyer/actuator/**").permitAll() // Allow access to Actuator endpoints
                                .anyRequest().authenticated()                  // Require authentication for all other endpoints
                )
                .csrf().disable(); // Disable CSRF for simplicity (use cautiously in production)

        return http.build();
    }
}
