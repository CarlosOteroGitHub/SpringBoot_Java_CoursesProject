package com.backend.alumns.auxiliar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
        return http.build();
    }
}