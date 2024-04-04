package com.example.project.config;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class AppConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(
                                                Authorize -> Authorize.requestMatchers("api/**").authenticated()
                                                                .anyRequest().permitAll())
                                .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
                                .csrf(csrf -> csrf.disable())
                                .cors(cors -> cors.configurationSource(request -> {
                                        CorsConfiguration cfg = new CorsConfiguration();
                                        cfg.setAllowedOrigins(Arrays.asList(
                                                        "https://localhost:3000",
                                                        "https://localhost:4000"

                                ));
                                        cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                                        cfg.setAllowedHeaders(
                                                        Arrays.asList("Authorization", "Content-Type",
                                                                        "X-Requested-With", "Accept", "Origin"));
                                        ;
                                        cfg.setExposedHeaders(Arrays.asList("Authorization"));
                                        cfg.setMaxAge(3600L);
                                        cfg.setAllowCredentials(true);
                                        return cfg;
                                }))
                                .httpBasic(withDefaults())
                                .formLogin(withDefaults());
                return http.build();
        }

        @Bean
        public UrlBasedCorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Arrays.asList(
                                "https://localhost:3000",
                                "https://localhost:4000"));
                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(
                                Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin"));
                config.setExposedHeaders(Arrays.asList("Authorization"));
                config.setMaxAge(3600L);
                config.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                return source;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
        @Bean
        public ModelMapper modelMapper() {
                return new ModelMapper();
        }
}
