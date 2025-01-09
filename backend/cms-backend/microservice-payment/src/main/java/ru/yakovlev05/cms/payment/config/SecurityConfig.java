package ru.yakovlev05.cms.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

//    private static final String LIST_YOOKASSA_IPS = """
//            hasIpAddress('185.71.76.0/27') or
//            hasIpAddress('185.71.77.0/27') or
//            hasIpAddress('77.75.153.0/25') or
//            hasIpAddress('77.75.156.11') or
//            hasIpAddress('77.75.156.35') or
//            hasIpAddress('77.75.154.128/25') or
//            hasIpAddress('2a02:5180::/32')""";

    /*
    Не будем использовать белый список ip, так как это на данный момент не удобно дебажить.
    Вместо этого будем в метаданных передавать секретный id
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
//                        .requestMatchers("/api/v1/payment/webhook/event").access(new WebExpressionAuthorizationManager(LIST_YOOKASSA_IPS))
                        .requestMatchers("/api/v1/payment/webhook/event").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().permitAll())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return user -> null;
    }
}
