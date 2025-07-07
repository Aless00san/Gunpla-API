package com.aless00san.springboot.gunpladb.security.filter;

import static com.aless00san.springboot.gunpladb.JwtTokenConfig.CONTENT_TYPE;
import static com.aless00san.springboot.gunpladb.JwtTokenConfig.HEADER_STRING;
import static com.aless00san.springboot.gunpladb.JwtTokenConfig.SECRET_KEY;
import static com.aless00san.springboot.gunpladb.JwtTokenConfig.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aless00san.springboot.gunpladb.entities.system.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        if (authenticationManager instanceof org.springframework.security.authentication.ProviderManager) {
            org.springframework.security.authentication.ProviderManager pm = (org.springframework.security.authentication.ProviderManager) authenticationManager;
        }

        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        User user = null;
        String username = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();

        } catch (Exception e) {
            System.err.println("Error parsing request: " + e.getMessage());
        }

        if (username == null || password == null) {
            throw new RuntimeException("Username or password cannot be null");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            Authentication result = authenticationManager.authenticate(authToken);
            System.out.println("Authentication SUCCESS!");
            return result;
        } catch (Exception e) {
            System.err.println("Authentication exception: " + e.getClass().getName() + " - " + e.getMessage());
            throw e;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult
                .getPrincipal();
        String username = user.getUsername();
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();

        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(authorities))
                .add("username", username)
                .build();

        String jws = Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + 86400000)) //24 hours
                .issuedAt(new Date())
                .claims(claims)
                .signWith(SECRET_KEY)
                .compact();

        // response.addHeader(HEADER_STRING, TOKEN_PREFIX + jws);

        // Instead of adding to header, set as httpOnly cookie
        Cookie cookie = new Cookie("auth_token", jws);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in production with HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(86400); // 24 hours in seconds 
        
        response.addCookie(cookie);

        Map<String, String> body = new HashMap<>();
        body.put("token", jws);
        body.put("username", username);

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Error during authentication, username or password is incorrect");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(CONTENT_TYPE);
    }
}