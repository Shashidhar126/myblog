package com.myblog7.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {//Interface Implementation: JwtAuthenticationEntryPoint implements the AuthenticationEntryPoint interface, which requires you to provide an implementation for the commence method. This method is invoked by Spring Security when an unauthenticated request is detected.
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {//Handling Unauthenticated Requests: When a client makes a request to an endpoint that requires authentication, but the request does not include a valid JWT token or the token is invalid/expired, the authentication process fails, resulting in an AuthenticationException
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                authException.getMessage());
    }
}