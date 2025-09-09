package co.com.pragma.api.config;

import co.com.pragma.api.helper.JwtAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private static final String[] PUBLIC_PATHS = {
            "/api/v1/login",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/favicon.ico"
    };

    @Bean
    public AuthenticationWebFilter jwtAuthenticationWebFilter(
            JwtAuthenticationConverter jwtConverter,
            ReactiveAuthenticationManager authenticationManager) {

        AuthenticationWebFilter filter = new AuthenticationWebFilter(authenticationManager);
        filter.setServerAuthenticationConverter(jwtConverter);
        filter.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        // Solo aplicar el filtro a rutas de API protegidas
        filter.setRequiresAuthenticationMatcher(exchange -> {
            String path = exchange.getRequest().getPath().value();

            // No aplicar filtro a rutas públicas
            for (String publicPath : PUBLIC_PATHS) {
                if (publicPath.endsWith("/**")) {
                    String basePath = publicPath.substring(0, publicPath.length() - 3);
                    if (path.startsWith(basePath)) {
                        return ServerWebExchangeMatcher.MatchResult.notMatch();
                    }
                } else if (path.equals(publicPath)) {
                    return ServerWebExchangeMatcher.MatchResult.notMatch();
                }
            }

            // Aplicar filtro solo a rutas de API
            if (path.startsWith("/api/")) {
                return ServerWebExchangeMatcher.MatchResult.match();
            }

            return ServerWebExchangeMatcher.MatchResult.notMatch();
        });

        return filter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            AuthenticationWebFilter jwtAuthenticationWebFilter) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(PUBLIC_PATHS).permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/usuarios").hasAnyAuthority("ADMIN", "ASESOR")
                        .pathMatchers("/api/**").authenticated()
                        .anyExchange().permitAll() // Permitir otras rutas no API
                )
                .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return authentication -> Mono.just(authentication);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
