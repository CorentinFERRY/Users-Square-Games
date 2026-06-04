package com.campus.Users_Square_Games.web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, HandlerExceptionResolver handlerExceptionResolver){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Récupère le header Authorization
        final String authHeader = request.getHeader("Authorization");
        // Si pas de token ou mal formaté, on laisse passer sans authentifier
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        try {
            // On extrait le token (en supprimant le "Bearer ")
            final String jwt = authHeader.substring(7);
            // On extrait le username depuis le token
            final String username = jwtService.extractUsername(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Si username valide et pas encore authentifié dans ce contexte
            if(username != null && authentication == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                // On vérifie que le token est valide
                if(jwtService.isTokenValid(jwt, userDetails)) {
                    String role = jwtService.extractRole(jwt);
                    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
                    // Crée l'objet d'authentification et l'injecte dans le contexte Spring Security
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            // On passe au filtre suivant dans la chaîne
            filterChain.doFilter(request,response);
        } catch (Exception execption) {
            handlerExceptionResolver.resolveException(request,response,null,execption);
        }

    }
}
