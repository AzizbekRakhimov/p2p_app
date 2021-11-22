package uz.azizbek.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import uz.azizbek.model.Users;

import java.util.Date;

@Component
@Log
public class JwtProvider {

    private final String secret = "cardSecuritySecret";

    public String generateToken(Users users) {
        long expireDate = 60000;

        return Jwts.builder()
                .setSubject(users.getUsername())
                .setExpiration(new Date(expireDate))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoginFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }


}
