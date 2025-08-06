package com.saad.wheelly.filter;

import com.saad.wheelly.securityconfig.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            log.info("doFilterInternal processing start");

            String jwt = this.resolveToken(request);

            if (jwt != null && jwtService.validateToken(jwt)) {

                String email = jwtService.getUserNameFromJwtToken(jwt);

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (email != null && authentication == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);


                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());


                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
                filterChain.doFilter(request, response);

            }else{

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;


            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String resolveToken(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        log.info("authHeader: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            return null;


        }

        return authHeader.substring(7);


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/v1/auth/");
    }
}
