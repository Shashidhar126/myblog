package com.myblog7.security;

import com.myblog7.exception.BlogAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import
        org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // inject dependencies
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // get JWT (token) from http request
        String token = getJWTfromRequest(request);
        // validate token
        try {
            if(StringUtils.hasText(token) && tokenProvider.validateToken(token)){//checks weather token is empty or not,if not empty checks validity of token using tokenProvider.validateToken(token
                // get username from token
                String username = tokenProvider.getUsernameFromJWT(token);//It extracts the username (or any other identifier) from the JWT token using the JwtTokenProvider.getUsernameFromJWT method.
                // load user associated with token
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);//It loads the user details associated with the extracted username by calling the customUserDetailsService.loadUserByUsername method. This typically involves retrieving user information from a database(autherization)
                UsernamePasswordAuthenticationToken authenticationToken = new//It creates an UsernamePasswordAuthenticationToken containing the user details, a null password (as tokens don't have passwords), and the user's authorities (roles and permissions).
                        UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // set spring security
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);//Set Authentication in SecurityContext: It sets the created authentication token in the Spring Security context using SecurityContextHolder.getContext().setAuthentication(authenticationToken). This step effectively logs the user in and associates their identity and authorities with the current request.
            }
        } catch (BlogAPIException e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request, response);
    }
    // Bearer <accessToken>
    private String getJWTfromRequest(HttpServletRequest request){//This method is used to extract the JWT token from the "Authorization" header of the incoming HTTP request. It checks if the header starts with "Bearer " and extracts the token part from it.
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
