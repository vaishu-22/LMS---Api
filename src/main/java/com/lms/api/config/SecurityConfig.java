package com.lms.api.config;

import com.lms.api.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ❌ Disable CSRF (since JWT is stateless)
                .csrf(csrf -> csrf.disable())

                // ✅ Allow CORS (optional but helps with testing)
                .cors(cors -> {})

                // ✅ Define authorization rules
                .authorizeHttpRequests(auth -> auth

                        // ✅ Public user endpoints
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()

                        // ✅ Swagger endpoints
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // ✅ Enrollment endpoints — make fully open for testing
                        .requestMatchers("/api/enrollments/**").permitAll()

                        // ✅ Course endpoints — keep public for now
                        .requestMatchers("/api/courses/**").permitAll()

                        // ✅ Anything else must be authenticated
                        .anyRequest().authenticated()
                )


                // ✅ Stateless (no sessions)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // ✅ Add JWT filter AFTER login/register are permitted
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ Authentication manager (needed for login)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        configuration.addAllowedOriginPattern("*");  // allow all origins (Swagger/Postman)
        configuration.addAllowedHeader("*");         // allow all headers
        configuration.addAllowedMethod("*");         // allow GET, POST, PUT, DELETE, etc.
        configuration.setAllowCredentials(true);

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source =
                new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
