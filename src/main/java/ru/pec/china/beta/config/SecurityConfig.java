package ru.pec.china.beta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.pec.china.beta.security.RestAccessDeniedHandler;
import ru.pec.china.beta.security.RestAuthenticationFailureHandler;
import ru.pec.china.beta.security.RestAuthenticationSuccessHandler;
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity()
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf->csrf.
                        ignoringRequestMatchers(
                                "/api/v1/cargo/save",
                                "/api/v1/cargo/upload",
                                "/api/v1/cargo/delete/** "
                        )
                )
                .authorizeHttpRequests((auth)->auth
                        .requestMatchers(
                                "/administration/**"
                        )
                        .hasAnyRole("ADMIN")

                        .requestMatchers(
                                "/css/**",
                                "/auth/**"
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )

                .formLogin((form) ->form.loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/auth/login?error")
                        .permitAll()
                )
                .logout((logout)->logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .build();

    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new RestAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new RestAuthenticationSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new RestAccessDeniedHandler();
    }

}

