package com.angkorsuntrix.demosynctrix.service;

import com.angkorsuntrix.demosynctrix.domain.Token;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AuthenticationService {

    private static final long EXPIRED_IN = TimeUnit.HOURS.toMillis(1);
    private static SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static void addToken(HttpServletResponse res, String username) throws InvalidKeyException, IOException {
        final String tokenString = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRED_IN))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        final Token token = new Token(tokenString);
        PrintWriter out = res.getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        out.print(new Gson().toJson(token));
        out.flush();
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader("Authorization");
        if (token != null) {
            final String user = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            }
        }
        return null;
    }
}
