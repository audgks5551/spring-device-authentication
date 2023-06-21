package io.mhan.springsecuritydevice.init;

import io.mhan.springsecuritydevice.users.entity.User;
import io.mhan.springsecuritydevice.users.entity.UserDevice;
import io.mhan.springsecuritydevice.users.repository.UserDeviceRepository;
import io.mhan.springsecuritydevice.users.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitData {

    @Bean
    public ApplicationRunner devInitData(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserDeviceRepository userDeviceRepository) {
        return args -> {
            User user = User.builder()
                    .username("user1")
                    .password(passwordEncoder.encode("1234"))
                    .build();

            User savedUser = userRepository.save(user);

//            String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36";
//
//            UserDevice userDevice = UserDevice.builder()
//                    .userAgent(userAgent)
//                    .user(savedUser)
//                    .build();
//
//            userDeviceRepository.save(userDevice);
        };
    }
}
