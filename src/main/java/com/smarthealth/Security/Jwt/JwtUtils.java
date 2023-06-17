package com.smarthealth.Security.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;


    //crear token

    public String generateAccesToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignaturekey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //validad token de acceso

    public boolean istokenValid(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignaturekey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        }catch (Exception e){
            log.error("token invalido, error:".concat(e.getMessage()));
            return false;
        }

    }

    //obtener info del token
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignaturekey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //obtener un solo claim
    public <T> T getClaims(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    //obtener el username del token

    public String getUsernameFromToken(String token){
        return  getClaims(token, Claims::getSubject);
    }

    //obtener firma del token

    public Key getSignaturekey(){
        byte[] keybytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keybytes);
    }
}
