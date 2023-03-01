package com.backend.alumns.auxiliar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    private static final String[] NO_AUTH_LIST = {
        "/swagger/index.html",
        "/login"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authz) -> authz
                .antMatchers(NO_AUTH_LIST).permitAll()
                .antMatchers(HttpMethod.POST, "/**").authenticated()
                .antMatchers(HttpMethod.PATCH, "/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/**").authenticated())
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
        return http.build();
    }
}