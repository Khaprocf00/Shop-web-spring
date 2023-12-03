package com.web.shopweb.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.shopweb.entity.RoleEntity;
import com.web.shopweb.entity.RoleName;
import com.web.shopweb.repository.RoleRepository;
import com.web.shopweb.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<RoleEntity> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }

}
