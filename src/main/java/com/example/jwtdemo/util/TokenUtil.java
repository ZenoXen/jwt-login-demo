package com.example.jwtdemo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ZhaoHe(hezhao @ dianhun.cn)
 * @date 2022/7/20 16:11
 */

public class TokenUtil {
    private static final String SECRET = "dde7fa6b-4591-4496-ae2c-cf7c3f84e81e";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String generateJwt(UserDetails userDetails) {
        Map<String, Object> claims = Map.of("authorities", List.of("admin", "super-admin"));
        return Jwts.builder().setIssuer("zenox")
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 360000))
                .setIssuedAt(new Date())
                .signWith(KEY).compact();
    }

    public static String extractUsernameFromJwt(String jwt) {
        return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(jwt).getBody().getSubject();
    }

    public static boolean checkJwtValidity(String jwt) {
        return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(jwt).getBody().getExpiration().after(new Date());
    }

    public static void main(String[] args) {
        String token = generateJwt(new User("zenox", "", List.of()));
        System.out.println(token);
        System.out.println(extractUsernameFromJwt(token));
        System.out.println(checkJwtValidity(token));
    }
}
