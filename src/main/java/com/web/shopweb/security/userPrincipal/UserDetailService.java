package com.web.shopweb.security.userPrincipal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.shopweb.entity.UserEntity;
import com.web.shopweb.service.UserService;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Failed -> NOT FOUND USER at username: " + username));
        return UserPrincipal.build(userEntity);
    }

}
