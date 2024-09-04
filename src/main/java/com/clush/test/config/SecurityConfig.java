package com.clush.test.config;

import com.clush.test.security.exception.CustomAuthenticationEntryPoint;
import com.clush.test.security.filter.JwtAuthenticationFilter;
import com.clush.test.security.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(JwtAuthenticationProvider jwtAuthenticationProvider, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);

        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers("/signup", "/login").permitAll()
//                                .anyRequest().authenticated()
                                .anyRequest().permitAll()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(jwtAuthenticationProvider);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
