package ua.project.dropmarket.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.project.dropmarket.service.UserService;

@Configuration
@EnableWebSecurity
public class Security {

    private final UserService userService;

    @Autowired
    public Security(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authenticationManager(httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                        .userDetailsService(userService)
                        .passwordEncoder(passwordEncoder())
                        .and()
                        .build());
        httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                "/info",
                                "/regis",
                                "/details/{id}",
                                "/login",
                                "/static/**",
                                "/resources",
                                "/resources/**")
                        .permitAll()
                        .requestMatchers("/")
                        .hasRole("User")
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout((logout) -> logout
                        .permitAll()
                        .logoutSuccessUrl("/"));
        return httpSecurity.build();
    }
}