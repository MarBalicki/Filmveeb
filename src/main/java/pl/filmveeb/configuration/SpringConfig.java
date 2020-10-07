package pl.filmveeb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfig {

    @Bean
    public PasswordEncoder passwordEncoderBCrypt() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rowPassword) {
                return BCrypt.hashpw(rowPassword.toString(), BCrypt.gensalt(4));
            }

            @Override
            public boolean matches(CharSequence rowPassword, String encodedPassword) {
                return BCrypt.checkpw(rowPassword.toString(), encodedPassword);
            }
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
