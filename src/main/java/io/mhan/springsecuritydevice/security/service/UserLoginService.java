package io.mhan.springsecuritydevice.security.service;

import io.mhan.springsecuritydevice.security.entity.SecurityUser;
import io.mhan.springsecuritydevice.users.entity.User;
import io.mhan.springsecuritydevice.users.entity.UserDevice;
import io.mhan.springsecuritydevice.users.repository.UserDeviceRepository;
import io.mhan.springsecuritydevice.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLoginService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserDeviceRepository userDeviceRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        List<String> userAgents = userDeviceRepository.findByUserId(user.getId())
                .stream()
                .map(UserDevice::getDeviceId)
                .toList();

        return new SecurityUser(user.getId(), user.getUsername(), user.getPassword(), userAgents, false);
    }
}
