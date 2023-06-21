package io.mhan.springsecuritydevice.security.filter;

import io.mhan.springsecuritydevice.security.entity.SecurityUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DeviceAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals("/users/devices/authentication") || request.getRequestURI().equals("/users/devices/check") || request.getRequestURI().equals("/users/devices/test")) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();

        if (!isAuthenticated) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        if (securityUser.isDeviceAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        String deviceId = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("deviceId")) {
                    deviceId = cookie.getValue();
                    break;
                }
            }
        }

        String finalDeviceId = deviceId;
        boolean isPresent = securityUser.getDeviceIds().stream()
                .anyMatch(id -> id.equals(finalDeviceId));

        if (isPresent) {
            securityUser.deviceAuthenticationComplete();
            filterChain.doFilter(request, response);
            return;
        }

        response.sendRedirect("/users/devices/authentication");
    }
}
