package pl.filmveeb.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/films").permitAll()
                .antMatchers("/films/*").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/filmDetails/*").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/user/resetPassword").permitAll()
                .antMatchers("/changePassword").permitAll()

                .antMatchers("/css/**").permitAll()
                .antMatchers("/img/**").permitAll()

                .antMatchers("/editFilm").hasAuthority("ADMIN")

//                .antMatchers("/login/**").authenticated()
//                .antMatchers("/myFilms").authenticated()

                .anyRequest().authenticated()

                .and()
                .csrf().disable()
                .cors().disable()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login-process")
                .failureUrl("/login?error=1")
                .defaultSuccessUrl("/index")

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("select u.email, u.password, 1 from user u where u.email=?")
                .authoritiesByUsernameQuery("select u.email, u.role, 1 from user u where u.email=?")
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
    }
}
