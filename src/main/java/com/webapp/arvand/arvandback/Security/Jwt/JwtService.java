package com.webapp.arvand.arvandback.Security.Jwt;

import com.webapp.arvand.arvandback.Utills.PemUtils;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;
import java.util.Date;

@Service
public class JwtService {
    private final PemUtils pemUtils;
    private final long EXPIRATION = 1000 * 60 * 15;
    public JwtService(PemUtils pemUtils) {
        this.pemUtils = pemUtils;
    }

    public String generateToken(String id,String jti) {
        try {
            return Jwts.builder()
                    .setId(jti)
                    .setSubject(id)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                    .signWith(pemUtils.getPrivateKey(), SignatureAlgorithm.RS256)
                    .compact();
        }
        catch (Exception e) {
            return null;
        }
    }
    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(pemUtils.getPublicKey())
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception e) {
            return null;
        }
    }
    public String extractEmail(String token,String key) {
        return extractAllClaims(token).get("email", String.class);
    }
    public Long extractLongValue(String token,String key) {
        return Long.valueOf(extractAllClaims(token).get(key).toString());
    }

}
