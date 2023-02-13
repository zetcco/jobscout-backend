package com.zetcco.jobscoutserver.services.auth;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.support.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final static String SIGNING_KEY = "566D597133743677397A244226452948404D635166546A576E5A723475377821";

    public String getUserEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }
    
    public Claims extractClaims(String jwtToken) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(getSigningKey()).build();
        return parser.parseClaimsJws(jwtToken).getBody();
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Key getSigningKey() {
        byte[] key_bytes = Decoders.BASE64.decode(SIGNING_KEY);
        return Keys.hmacShaKeyFor(key_bytes);
    }

    public boolean isTokenValid(String jwt_token, User user) {
        final String tokeN_email = getUserEmail(jwt_token);
        return tokeN_email.equals(user.getEmail());
    }

    public String generateToken(User user) {
        String token =  Jwts.builder()
                            .setSubject(user.getEmail())
                            .claim("role", user.getRole())
                            .claim("email", user.getEmail())
                            .setIssuedAt(new Date(System.currentTimeMillis()))
                            .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                            .compact();
        return token;
    }
}
