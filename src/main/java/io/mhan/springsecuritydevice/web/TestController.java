package io.mhan.springsecuritydevice.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/device")
    public String identifyDevice(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent;
    }

    @GetMapping("/check")
    public String check() {
        return "check";
    }
}
