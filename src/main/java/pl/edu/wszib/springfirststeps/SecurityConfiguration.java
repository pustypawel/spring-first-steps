package pl.edu.wszib.springfirststeps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .requestMatchers(request -> request.getMethod().equalsIgnoreCase("get")).authenticated()
                .requestMatchers(request -> request.getMethod().equalsIgnoreCase("post")).permitAll()
//                .anyRequest()
                .and()
                .httpBasic()
        .and().csrf().disable();
    }

    @Autowired
    public void configureUsers(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}haslo123")
                .roles("USER");
    }
}
