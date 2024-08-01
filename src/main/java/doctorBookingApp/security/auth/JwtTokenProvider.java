package doctorBookingApp.security.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Date;


@Slf4j
@Component
public class JwtTokenProvider {

    private String jwtSecret = "984hg493gh0439rthr0429uruj2309yh937gc763fe87t3f89723gf";
    private long jwtLifetime = 60000;

    public String createToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtLifetime);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token); // изменено с parseClaimsJwt на parseClaimsJws
            return true;
        } catch (SignatureException e) {
            throw new InvalidJwtException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            throw new InvalidJwtException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            throw new InvalidJwtException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new InvalidJwtException("JWT claims string is empty");
        }
    }

    public String getManagerNameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token) // изменено с parseClaimsJwt на parseClaimsJws
                .getBody();

        return claims.getSubject();
    }
}
