package com.aless00san.springboot.gunpladb.security.filter;

import static com.aless00san.springboot.gunpladb.JwtTokenConfig.CONTENT_TYPE;
import static com.aless00san.springboot.gunpladb.JwtTokenConfig.SECRET_KEY;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.aless00san.springboot.gunpladb.security.SimpleGrantedAuthJsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Get token from cookie instead of header
        String token = getTokenFromCookie(request);

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
            String username = claims.getSubject();
            Object authorities = claims.get("authorities");

            Collection<? extends GrantedAuthority> auths = Arrays.asList(new ObjectMapper()
                    .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthJsonCreator.class)
                    .readValue(authorities.toString().getBytes(), SimpleGrantedAuthority[].class));

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
                    auths);
            SecurityContextHolder.getContext().setAuthentication(authToken);

            chain.doFilter(request, response);
        } catch (Exception e) {
            Map<String, String> body = new java.util.HashMap<>();
            body.put("message", "Error during authentication, username or password is incorrect");
            body.put("error", e.getMessage());

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(CONTENT_TYPE);
        }
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        System.out.println("Cookies found: " + (cookies != null ? cookies.length : 0));

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("Cookie: " + cookie.getName() + " = " + cookie.getValue());
                if ("auth_token".equals(cookie.getName())) {
                    System.out.println("Found auth_token cookie!");
                    return cookie.getValue();
                }
            }
        }
        System.out.println("No auth_token cookie found");
        return null;
    }

}
