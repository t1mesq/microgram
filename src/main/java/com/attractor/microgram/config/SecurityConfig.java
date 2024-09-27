package com.attractor.microgram.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/profile")
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll())
//                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(HttpMethod.POST, "/publication/create").permitAll()
                                .requestMatchers(HttpMethod.POST, "/publication/edit").permitAll()
                                .requestMatchers(HttpMethod.GET, "/publications/deletePublication/").permitAll()
                                .requestMatchers(HttpMethod.POST, "/comments/sendCommentMessage").permitAll()
                                .requestMatchers(HttpMethod.GET, "/comments/deleteComment/").permitAll()
//                        .requestMatchers(HttpMethod.POST, "auth/login").permitAll()
//                      .requestMatchers(HttpMethod.GET, "/profile").authenticated()
//                      .requestMatchers(HttpMethod.GET, "/publications/getPublications").authenticated()
//                        .requestMatchers(HttpMethod.GET, "/subscriptions").authenticated()
//                                .requestMatchers(HttpMethod.GET, "/publication/create").authenticated()
                       .anyRequest().permitAll()
                );
        return http.build();
    }
}
