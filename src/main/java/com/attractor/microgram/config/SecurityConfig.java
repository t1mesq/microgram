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

    private final DataSource dataSource;
    private static final String USER_QUERY = "select EMAIL, PASSWORD, ENABLED " +
                                             "from USERS where EMAIL = ?;";
    private static final String AUTHORITIES_QUERY = """
            select u.email, r.role from users u, roles r
            where r.id = u.role_id
            and u.email = ?
            ;
            """;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_QUERY)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

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
                .csrf(AbstractHttpConfigurer::disable)
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
