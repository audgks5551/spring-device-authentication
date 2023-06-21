package io.mhan.springsecuritydevice.web;

import io.mhan.springsecuritydevice.security.entity.SecurityUser;
import io.mhan.springsecuritydevice.users.service.UserDeviceService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/devices")
public class UserDeviceController {

    private final UserDeviceService userDeviceService;

    @GetMapping("/authentication")
    public String showAuthentication() {
        return "authentication";
    }

    @GetMapping("/check")
    public String showCheck(
            @AuthenticationPrincipal SecurityUser securityUser,
            HttpSession httpSession) {
        if (securityUser.isDeviceAuthenticated()) {
            return "redirect:/";
        }

        Random random = new Random();

        int randomNumber = random.nextInt(900000) + 100000;

        httpSession.setAttribute("device-check-number", String.valueOf(randomNumber));

        // 이메일 또는 카카오톡으로 번호 전송

        return "check";
    }

    @PostMapping("/check")
    public String check(
            @AuthenticationPrincipal SecurityUser securityUser,
            String code,
            HttpSession httpSession,
            HttpServletResponse response
            ) {
        if (securityUser.isDeviceAuthenticated()) {
            return "redirect:/";
        }

        String deviceCheckUUID = (String) httpSession.getAttribute("device-check-number");

        if (deviceCheckUUID.isBlank() || !deviceCheckUUID.equals(code)) {
            return "redirect:/users/devices/authentication";
        }

        String deviceId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("deviceId",deviceId);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*365);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        userDeviceService.createAndSave(deviceId, securityUser.getId());
        securityUser.deviceAuthenticationComplete();

        return "redirect:/success";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(HttpSession httpSession) {
        return (String) httpSession.getAttribute("device-check-number");
    }
}
