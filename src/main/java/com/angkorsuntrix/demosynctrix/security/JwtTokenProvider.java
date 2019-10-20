package com.angkorsuntrix.demosynctrix.security;

import com.angkorsuntrix.demosynctrix.payload.AccessToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenProvider { // use interfaces that what will make your code much more testable

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    // i reccomend to use configuration service
    // https://tuhrig.de/why-using-springs-value-annotation-is-bad/
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.expirationMils}")
    private int expirationInMils;

    public AccessToken generateToken(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date(); // if you created DateUtil class then use it everywhere
        // and for data manipulation consider to use java 8 classes.. e.g
        //         Date expirationDate = Date.from(LocalDateTime.now().plus(expirationInMils, ChronoUnit.MILLIS).toInstant(ZoneOffset.UTC));
        // https://www.journaldev.com/2800/java-8-date-localdate-localdatetime-instant
        Date expirationDate = new Date(now.getTime() + expirationInMils);

        String jwt = Jwts.builder()
                .setSubject(Long.toString(principal.getId())) // u do unnecessary unboxing here Long.toString is used to get string from long. If you already have obejct then just call toString
                .setIssuedAt(new Date()) // same as above
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // do not use deprecated methods . use #signWith(Key, SignatureAlgorithm)} instead
                .compact();
        return new AccessToken(jwt, expirationDate.getTime());
    }

    // can name it getUserId
    public Long getUserFromJwt(String token) { // could be package level, not public
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    // since ur method returns boolean.. u can follow convention by naming it isValid
    public boolean validateToken(String token) { // could be package level, not public
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid Jwt signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid Jwt token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired Jwt token");
        } catch (IllegalArgumentException ex) {
            logger.error("Jwt claims string is empty");
        }
        return false;
    }

}
