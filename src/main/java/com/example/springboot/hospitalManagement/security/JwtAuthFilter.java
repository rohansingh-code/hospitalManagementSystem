package com.example.springboot.hospitalManagement.security;

import com.example.springboot.hospitalManagement.Entity.User;
import com.example.springboot.hospitalManagement.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j // This annotation gives you the 'log' object used
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("incoming request: {}", request.getRequestURI());

        final String requestTokenHeader = request.getHeader("Authorization");

        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Splits "Bearer <token>" and takes the second part
        String token = requestTokenHeader.split("Bearer ")[1];
        String username = authUtil.getUsernameFromToken(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Fetches the user directly from the repository
            User user = userRepository.findByUsername(username).orElseThrow();

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}