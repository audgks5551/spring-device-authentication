package io.mhan.springsecuritydevice.users.service;

import io.mhan.springsecuritydevice.users.entity.User;
import io.mhan.springsecuritydevice.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByIdElseThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
}
