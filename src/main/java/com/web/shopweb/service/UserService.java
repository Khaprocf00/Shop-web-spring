package com.web.shopweb.service;

import java.util.Optional;

import com.web.shopweb.entity.UserEntity;

public interface UserService {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<UserEntity> findByUsername(String username);

    UserEntity save(UserEntity userEntity);
}
