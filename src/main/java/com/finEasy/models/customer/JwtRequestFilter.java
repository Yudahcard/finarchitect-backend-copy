package com.finEasy.models.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        logger.info("Processing request in JwtRequestFilter for URI: {}", request.getRequestURI());

        // Token validation logic here
        final String requestUri = request.getRequestURI();

        if (requestUri.equals("/authenticate") || requestUri.equals("/register") || requestUri.equals("/signup") || requestUri.equals("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

//        filterChain.doFilter(request, response);
    }
}
