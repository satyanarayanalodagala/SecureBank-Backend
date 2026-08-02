package com.securebank.config;


import java.util.List;

import com.securebank.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {



    private final JwtAuthenticationFilter jwtAuthenticationFilter;



    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;

    }








    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {



        http



        // ================= CORS =================

        .cors(Customizer.withDefaults())



        // ================= CSRF =================

        .csrf(csrf -> csrf.disable())



        // ================= SESSION =================

        .sessionManagement(session ->

                session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )

        )



        // ================= AUTHORIZATION =================

        .authorizeHttpRequests(auth -> auth





                // ================= PUBLIC CUSTOMER =================


                .requestMatchers(

                        "/api/customers/register",

                        "/api/customers/login",

                        "/api/customers/forgot-password",

                        "/api/customers/reset-password",

                        "/api/forgot-password/**",

                        "/uploads/**"

                )

                .permitAll()






                // ================= PUBLIC ADMIN =================


                .requestMatchers(

                        "/api/admin/register",

                        "/api/admin/login"

                )

                .permitAll()







                // ================= ADMIN APIs =================


                .requestMatchers(

                        "/api/admin/**"

                )

                .hasAuthority(
                        "ROLE_ADMIN"
                )







                // ================= ADMIN TRANSACTION APIs =================


                .requestMatchers(

                        "/api/transactions/admin/**"

                )

                .hasAuthority(
                        "ROLE_ADMIN"
                )








                // ================= SWAGGER =================


                .requestMatchers(

                        "/swagger-ui/**",

                        "/v3/api-docs/**"

                )

                .permitAll()








                // ================= ERROR =================


                .requestMatchers(

                        "/error"

                )

                .permitAll()






                // ================= EVERYTHING ELSE =================


                .anyRequest()

                .authenticated()



        )








        // ================= JWT FILTER =================


        .addFilterBefore(

                jwtAuthenticationFilter,

                UsernamePasswordAuthenticationFilter.class

        );





        return http.build();

    }









    // ================= CORS CONFIGURATION =================


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {



        CorsConfiguration configuration =

                new CorsConfiguration();



        configuration.setAllowedOrigins(

                List.of(

                		"http://localhost:4200",
                        "https://satya-securebank.netlify.app"
                       

                )

        );




        configuration.setAllowedMethods(

                List.of(

                        "GET",

                        "POST",

                        "PUT",

                        "DELETE",

                        "OPTIONS"

                )

        );





        configuration.setAllowedHeaders(

                List.of("*")

        );





        configuration.setAllowCredentials(true);







        UrlBasedCorsConfigurationSource source =

                new UrlBasedCorsConfigurationSource();





        source.registerCorsConfiguration(

                "/**",

                configuration

        );





        return source;

    }










    // ================= AUTH MANAGER =================


    @Bean
    public AuthenticationManager authenticationManager(

            AuthenticationConfiguration configuration

    )

    throws Exception {


        return configuration.getAuthenticationManager();

    }









    // ================= PASSWORD ENCODER =================


    @Bean
    public PasswordEncoder passwordEncoder() {


        return new BCryptPasswordEncoder();

    }


}