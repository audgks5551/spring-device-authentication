package io.mhan.springsecuritydevice.users.repository;

import io.mhan.springsecuritydevice.users.entity.UserDevice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDeviceRepository extends CrudRepository<UserDevice, Long> {
    List<UserDevice> findByUserId(Long id);
}
