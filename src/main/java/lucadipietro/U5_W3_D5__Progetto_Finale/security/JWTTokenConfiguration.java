package lucadipietro.U5_W3_D5__Progetto_Finale.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lucadipietro.U5_W3_D5__Progetto_Finale.entities.User;
import lucadipietro.U5_W3_D5__Progetto_Finale.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenConfiguration {
    private String secret;

    public JWTTokenConfiguration(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String createToken(User user){
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .subject(String.valueOf(user.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String token){
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex){
            throw new UnauthorizedException("Errore col token, preghiamo di riprovare a fare il login!");
        }
    }

    public String extractIdFromToken(String token){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }
}
