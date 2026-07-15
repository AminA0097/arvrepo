package com.webapp.arvand.arvandback.Security.Jwt;

import com.webapp.arvand.arvandback.Guava.GuavaCache;
import com.webapp.arvand.arvandback.Guava.GuavaService;
import com.webapp.arvand.arvandback.Security.Auth.UserDetail;
import com.webapp.arvand.arvandback.Utills.ApiErrorType;
import com.webapp.arvand.arvandback.Utills.ApiException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private GuavaService guavaService;
    public JwtFilter(JwtService jwtService, GuavaService guavaService) {
        this.jwtService = jwtService;
        this.guavaService = guavaService;
    }


    /**
     * Extracts JWT token from cookie named "access_token".
     *
     * @param request HTTP request
     * @return token value or null if not found
     */
    private String extractCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("auth_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = extractCookie(request);
        System.out.println(request.getRequestURI());
        if (token != null) {
            try {
                Claims claims = jwtService.extractAllClaims(token);
                if (claims == null) {
                    throw new ApiException(ApiErrorType.UNAUTHORIZED,null);
                }
                GuavaCache cache = guavaService.checkExistAncCompare(claims);

                if (cache == null) {
                    filterChain.doFilter(request, response);
                    return;
                }

                List<SimpleGrantedAuthority> authorities = cache.getRoles()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                UserDetail principal =
                        new UserDetail(cache.getUserId(), cache.getUsername(), null, authorities);

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                principal,
                                null,
                                authorities
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
