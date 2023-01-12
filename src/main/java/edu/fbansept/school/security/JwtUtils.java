package edu.fbansept.school.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

@Service
public class JwtUtils {

    //Récupère les données du JWT (hors date de creation, date d'expiration)
    public Claims extractBody(String jwt) {
        return Jwts.parser()
                .setSigningKey("azerty")
                .parseClaimsJws(jwt)
                .getBody();
    }

    public String generateJwt(MyUserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .signWith(SignatureAlgorithm.HS256, "azerty")
                .compact();
    }
}
