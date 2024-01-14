package com.example.miniprojecttest.utils;

import com.example.miniprojecttest.member.model.entity.Member;
import com.example.miniprojecttest.member.model.entity.Seller;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtils {
    public static String generateAccessToken(Member member, String key, int expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("email", member.getEmail());
        claims.put("idx", member.getConsumerIdx());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getSignKey(key), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public static String generateAccessToken(Seller seller, String key, int expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("email", seller.getEmail());
        claims.put("idx", seller.getSellerIdx());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getSignKey(key), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public static Key getSignKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public static Boolean validate(String token, String username, String key) {
//        String usernameByToken = getUsername(token, username);
        String usernameByToken = getUsername(token, key);

        Date expireTime = extractAllClaims(token, key).getExpiration();
        Boolean result = expireTime.before(new Date(System.currentTimeMillis()));

        return usernameByToken.equals(username) && !result;
    }

    public static String getUsername(String token, String key) {
        return extractAllClaims(token, key).get("email", String.class);
    }

    public static Long getUserIdx(String token, String key) {
        return extractAllClaims(token, key).get("idx", Long.class);
    }

    public static Claims extractAllClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
