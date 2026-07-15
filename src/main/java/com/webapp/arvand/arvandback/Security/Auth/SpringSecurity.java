package com.webapp.arvand.arvandback.Security.Auth;

import com.webapp.arvand.arvandback.Security.Jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/api/**").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/api/**").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/api/search/**").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/api/layout/mobileHero").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/index.html").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/index.html").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/admin/**").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/css/**").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/js/**").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/admin/**").permitAll()
//                        .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers("/js/**", "/css/**", "/images/**").permitAll()
                                .anyRequest().permitAll()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
