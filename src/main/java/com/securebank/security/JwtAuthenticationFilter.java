package com.securebank.security;


import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {




    private static final Logger logger =

            LoggerFactory.getLogger(
                    JwtAuthenticationFilter.class
            );




    @Autowired
    private JwtService jwtService;




    @Autowired
    private CustomUserDetailsService userDetailsService;









    @Override
    protected void doFilterInternal(


            HttpServletRequest request,


            HttpServletResponse response,


            FilterChain filterChain)


            throws ServletException, IOException {




        String requestURI = request.getRequestURI();






        // ==================================================
        // ALLOW PROFILE IMAGES WITHOUT JWT
        // ==================================================


        if(requestURI.startsWith("/uploads/")) {


            logger.info(

                    "Static resource request allowed: {}",

                    requestURI

            );


            filterChain.doFilter(

                    request,

                    response

            );


            return;

        }








        try {




            logger.info(

                    "Incoming request: {}",

                    requestURI

            );






            String authHeader =

                    request.getHeader("Authorization");



            String token = null;


            String username = null;








            // ==================================================
            // EXTRACT TOKEN
            // ==================================================



            if(authHeader != null &&

                    authHeader.startsWith("Bearer ")) {



                token =

                        authHeader.substring(7);




                logger.info(

                        "JWT token received"

                );




                username =

                        jwtService.extractUsername(token);




                logger.info(

                        "Username extracted: {}",

                        username

                );



            }

            else {



                logger.debug(

                        "No JWT token found"

                );


            }









            // ==================================================
            // VALIDATE USER
            // ==================================================



            if(username != null &&


                    SecurityContextHolder

                    .getContext()

                    .getAuthentication() == null) {






                UserDetails userDetails =



                        userDetailsService

                        .loadUserByUsername(username);







                if(jwtService.isTokenValid(

                        token,

                        userDetails

                )) {







                    UsernamePasswordAuthenticationToken authentication =



                            new UsernamePasswordAuthenticationToken(



                                    userDetails,



                                    null,



                                    userDetails.getAuthorities()



                            );









                    authentication.setDetails(


                            new WebAuthenticationDetailsSource()

                            .buildDetails(request)


                    );









                    SecurityContextHolder

                            .getContext()

                            .setAuthentication(authentication);








                    // ==================================================
                    // DEBUG AUTHORITIES
                    // ==================================================


                    System.out.println(

                            "========== JWT USER =========="

                    );


                    System.out.println(

                            "USERNAME : "

                            + authentication.getName()

                    );



                    System.out.println(

                            "AUTHORITIES : "

                            + authentication.getAuthorities()

                    );




                    logger.info(

                            "JWT authentication successful for {}",

                            username

                    );





                }

                else {



                    logger.warn(

                            "Invalid JWT token"

                    );


                }





            }








        }

        catch(Exception exception) {



            logger.error(

                    "JWT authentication failed: {}",

                    exception.getMessage(),

                    exception

            );


        }








        // Continue request chain


        filterChain.doFilter(

                request,

                response

        );



    }



}