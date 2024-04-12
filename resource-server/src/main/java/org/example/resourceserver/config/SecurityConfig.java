package org.example.resourceserver.config;

import org.example.resourceserver.converter.KeyCloakRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.example.resourceserver.constant.KeyCloakRoles.ADMIN;
import static org.example.resourceserver.constant.KeyCloakRoles.USER;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());

        http
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        (authz) -> authz
                                .requestMatchers(HttpMethod.GET, "/apis/devices").hasAnyRole(USER)
                                .requestMatchers("/apis/devices/**").hasRole(ADMIN)
                                .anyRequest()
                                .authenticated()
                )
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtConverter);
        ;

        return http.build();
    }
}
