package com.securebank.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;


import java.nio.charset.StandardCharsets;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.function.Function;





@Service
public class JwtService {




    // Minimum 32+ characters for HS256

    private static final String SECRET_KEY =
            "securebank-jwt-secret-key-for-authentication-2026";




    private static final long EXPIRATION_TIME =
            1000 * 60 * 60 * 10;   // 10 hours







    private SecretKey getSigningKey() {


        return Keys.hmacShaKeyFor(

                SECRET_KEY.getBytes(
                        StandardCharsets.UTF_8)

        );

    }









    // ================= GENERATE TOKEN =================



    public String generateToken(
            String username,
            String role) {



        Map<String,Object> claims =
                new HashMap<>();


        claims.put(
                "role",
                role
        );




        return Jwts.builder()


                .claims(claims)


                .subject(username)


                .issuedAt(
                        new Date()
                )


                .expiration(

                        new Date(
                                System.currentTimeMillis()
                                + EXPIRATION_TIME
                        )

                )


                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256
                )


                .compact();


    }









    // Generate token using UserDetails

    public String generateToken(
            UserDetails userDetails) {



        return generateToken(
                userDetails.getUsername(),
                userDetails.getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority()
        );

    }









    // ================= EXTRACT USERNAME =================



    public String extractUsername(
            String token) {


        return extractClaim(
                token,
                Claims::getSubject
        );

    }









    // ================= EXTRACT ROLE =================



    public String extractRole(
            String token) {


        return extractClaim(

                token,

                claims ->
                        claims.get(
                                "role",
                                String.class)

        );

    }









    // ================= CLAIM EXTRACTOR =================



    public <T> T extractClaim(

            String token,

            Function<Claims,T> resolver) {



        Claims claims =
                parseClaims(token);



        return resolver.apply(claims);

    }









    // ================= TOKEN VALIDATION =================



    public boolean isTokenValid(

            String token,

            UserDetails userDetails) {



        try {



            String username =
                    extractUsername(token);



            return username != null

                    && username.equals(
                            userDetails.getUsername())

                    && !isTokenExpired(token);



        }

        catch(Exception e) {


            return false;

        }


    }









    public boolean validateToken(

            String token,

            UserDetails userDetails) {



        return isTokenValid(
                token,
                userDetails
        );

    }









    // ================= EXPIRATION =================



    private boolean isTokenExpired(
            String token) {


        return extractExpiration(token)
                .before(new Date());

    }







    private Date extractExpiration(
            String token) {


        return extractClaim(
                token,
                Claims::getExpiration
        );

    }









    // ================= PARSE JWT =================



    private Claims parseClaims(
            String token) {



        return Jwts.parser()

                .verifyWith(
                        getSigningKey()
                )

                .build()

                .parseSignedClaims(token)

                .getPayload();

    }



}