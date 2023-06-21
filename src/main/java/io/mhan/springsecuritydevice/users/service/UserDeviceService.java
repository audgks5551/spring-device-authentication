package io.mhan.springsecuritydevice.users.service;

import io.mhan.springsecuritydevice.users.entity.User;
import io.mhan.springsecuritydevice.users.entity.UserDevice;
import io.mhan.springsecuritydevice.users.repository.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDeviceService {

    private final UserDeviceRepository userDeviceRepository;
    private final UserService userService;

    public UserDevice createAndSave(String deviceId, Long userId) {
        User user = userService.findByIdElseThrow(userId);

        UserDevice userDevice = UserDevice.builder()
                .deviceId(deviceId)
                .user(user)
                .build();

        return userDeviceRepository.save(userDevice);
    }
}
