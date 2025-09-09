package co.com.pragma.api.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {

    private final JwtService jwtService;
    private static final List<String> SWAGGER_PATHS = List.of(
            "/v3/api-docs",
            "/swagger-ui",
            "/webjars"
    );

    public JwtAuthenticationConverter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().value();

        // No procesar rutas de Swagger
        if (SWAGGER_PATHS.stream().anyMatch(path::startsWith) ||
                path.equals("/api/v1/login")) {
            return Mono.empty();
        }

        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) return Mono.empty();

        String token = header.substring(7);
        return Mono.fromCallable(() -> jwtService.parseAndValidate(token))
                .map(Jws::getPayload)
                .map(this::toAuthentication)
                .onErrorResume(e -> Mono.empty());
    }

    private Authentication toAuthentication(Claims claims) {
        String userId = claims.getSubject();
        Collection<String> roles = extractRolesFromClaims(claims);

        var authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        var authentication = new UsernamePasswordAuthenticationToken(userId, "N/A", authorities);
        authentication.setDetails(claims);
        return authentication;
    }

    private Collection<String> extractRolesFromClaims(Claims claims) {
        Object rawRoles = claims.get("roles");
        if (rawRoles == null) {
            Object singleRole = claims.get("rol");
            if (singleRole != null) {
                return Set.of(singleRole.toString());
            }
            return Collections.emptyList();
        }
        if (rawRoles instanceof Collection<?>) {
            return ((Collection<?>) rawRoles).stream().map(Objects::toString).collect(Collectors.toSet());
        }
        return Set.of(rawRoles.toString());
    }
}
