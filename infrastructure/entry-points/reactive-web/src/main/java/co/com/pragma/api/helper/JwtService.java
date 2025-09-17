package co.com.pragma.api.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Service
public class JwtService {

    private final SecretKey key;
    private final String issuer;
    private final String audience;
    private final Long ttlMinutes;
    private final String secret;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${app.issuer}") String issuer,
            @Value("${app.audience}") String audience,
            @Value("${security.jwt.ttl-minutes}") Long ttlMinutes) {
        this.secret = secret;
        this.issuer = issuer;
        this.audience = audience;
        this.ttlMinutes = ttlMinutes;
        byte[] bytes = secret.matches("^[A-Za-z0-9+/=]+$") ?
                io.jsonwebtoken.io.Decoders.BASE64.decode(secret) :
                secret.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        this.key = io.jsonwebtoken.security.Keys.hmacShaKeyFor(bytes);
    }

//    @PostConstruct
//    public void init() {
//        byte[] bytes = secret.matches("^[A-Za-z0-9+/=]+$") ? Decoders.BASE64.decode(secret) : secret.getBytes(StandardCharsets.UTF_8);
//        this.key = Keys.hmacShaKeyFor(bytes);
//    }

    public String generate(Long userId, String email, String role) {
        Instant now = Instant.now();
        return Jwts.builder()
                .issuer(issuer)
                .audience().add(audience).and()
                .subject(userId.toString())
                .claim("uid", userId)
                .claim("email", email)
                .claim("rol", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(Duration.ofMinutes(ttlMinutes))))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public Jws<Claims> parseAndValidate(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .clockSkewSeconds(Duration.ofSeconds(120).getSeconds())
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token);
    }
}