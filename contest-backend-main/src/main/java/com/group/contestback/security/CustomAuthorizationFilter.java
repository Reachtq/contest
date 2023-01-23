package com.group.contestback.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static java.util.Arrays.stream;
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private String secret;

    private final String[] tokenExceptions = {
            "api-docs",
            "configuration",
            "swagger-resources",
            "configuration",
            "swagger-ui.html",
            "webjars",
            "/api/user/add"
    };

    public CustomAuthorizationFilter(String secret) {
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info(secret);
        if(request.getServletPath().equals("/login")
                || Arrays.stream(tokenExceptions).anyMatch(request.getServletPath()::contains)) {
            log.info("inside exception");
            log.info(request.getServletPath());
            try{
                filterChain.doFilter(request,response);
            } catch (Exception e) {
                response.setHeader("error",e.getMessage());
                log.error("exception: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {

            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            log.info("authentication header is present " + String.valueOf(authorizationHeader != null));
            if(authorizationHeader != null) {
                try {
                    Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(authorizationHeader);
                    String username = decodedJWT.getSubject();
                    log.info("user " + username + " tries to access " + request.getServletPath());

                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role->{
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request,response);

                } catch (Exception e) {
                    response.setHeader("error",e.getMessage());
                    log.error("exception: " + e.getMessage());
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                response.setHeader("error","no authentication provided");
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                log.error("no authentication provided");
                filterChain.doFilter(request,response);
            }
        }
    }
}
