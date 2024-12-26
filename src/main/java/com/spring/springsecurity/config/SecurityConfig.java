package com.spring.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                // .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request ->

                        request.requestMatchers("/hello").permitAll().anyRequest().authenticated()


                );

        return http.build();
    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        /* Implement working with multiple users using InmemoryUserDetailsManager
//
//        UserDetails user1 = User.withDefaultPasswordEncoder().username("Murad").password("123").build();
//
//        UserDetails user2 = User.withDefaultPasswordEncoder().username("Farid").password("12").build();
//        return new InMemoryUserDetailsManager(user1, user2); */
//
//        return null;
//
//    }

}
