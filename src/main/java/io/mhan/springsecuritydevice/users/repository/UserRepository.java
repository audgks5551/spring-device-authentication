package io.mhan.springsecuritydevice.users.repository;

import io.mhan.springsecuritydevice.users.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
