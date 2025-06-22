package br.com.ff.arch_beaver.common.security.utils;

import br.com.ff.arch_beaver.common.config.EnvironmentVariable;
import br.com.ff.arch_beaver.common.error.exception.auth.TokenException;
import br.com.ff.arch_beaver.common.utils.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class TokenJWTSecurity {

    private static final String SECRET = EnvironmentVariable.TOKEN_SECRET_PHRASE;
    private static final Long EXPIRATION = EnvironmentVariable.TOKEN_EXPIRATION;
    private static final Long EXPIRATION_REFRESH_TOKEN = EnvironmentVariable.EXPIRATION_REFRESH_TOKEN;
    private static final Long EXPIRATION_RECOVER_PASSWORD_TOKEN = EnvironmentVariable.EXPIRATION_RECOVER_PASSWORD_TOKEN;

    public String generateTokenWeb(String username) {
        return generateTokenWeb(username, EXPIRATION);
    }

    public String generateTokenMobile(String username) {
        Key signingKey = new SecretKeySpec(Base64.getEncoder().encode(SECRET.getBytes()), SignatureAlgorithm.HS512.getJcaName());

        HashMap<String, Object> map = new HashMap<>();

        map.put("connectionId", UUID.randomUUID());

        return Jwts.builder()
            .addClaims(map)
            .setSubject(username)
            .signWith(signingKey, SignatureAlgorithm.HS512)
            .compact();
    }

    public String generateRefreshToken(String username) {
        return generateTokenWeb(username, EXPIRATION_REFRESH_TOKEN);
    }

    public String generateRecoverPasswordToken(String username) {
        return generateTokenWeb(username, EXPIRATION_RECOVER_PASSWORD_TOKEN);
    }

    private String generateTokenWeb(String username, Long expiration) {
        Key signingKey = new SecretKeySpec(Base64.getEncoder().encode(SECRET.getBytes()), SignatureAlgorithm.HS512.getJcaName());
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(signingKey, SignatureAlgorithm.HS512)
            .compact();
    }

    public boolean tokenValid(String token) {
        var claims = getClaims(token);
        if (claims != null) {
            String username = claims.getSubject();
            var expirationDate = claims.getExpiration();
            var nowDate = new Date(System.currentTimeMillis());

            if (Objects.isNull(expirationDate)) {
                return username != null;
            }

            return username != null && nowDate.before(expirationDate);
        }
        return false;
    }

    public LocalDateTime getExpiration(String token) {
        var claims = getClaims(token);
        if (claims != null) {
            return DateUtil.toLocalDateTime(claims.getExpiration());
        }
        throw new TokenException("Não foi possivel acessar os dados dentro do Token");
    }

    private Claims getClaims(String token) {
        try {
            Key signingKey = new SecretKeySpec(Base64.getEncoder().encode(SECRET.getBytes()), SignatureAlgorithm.HS512.getJcaName());
            return
                Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                        .parseClaimsJws(token)
                        .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenException("Sessão do token expirada");
        } catch (Exception e) {
            throw new TokenException(e.getMessage(), e);
        }
    }

    public String getUserEmail(String token) {
        var claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    public Integer getValueInToken(String token, String key) {
        var claims = getClaims(token);
        if (claims != null) {
            return (Integer) claims.get(key);
        }
        return null;
    }

}
