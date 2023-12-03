package com.web.shopweb.service;

import java.util.Optional;

import com.web.shopweb.entity.RoleEntity;
import com.web.shopweb.entity.RoleName;

public interface RoleService {
    Optional<RoleEntity> findByName(RoleName name);
}
