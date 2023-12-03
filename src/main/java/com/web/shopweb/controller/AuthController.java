package com.web.shopweb.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopweb.dto.request.SignInFrom;
import com.web.shopweb.dto.request.SignUpFrom;
import com.web.shopweb.dto.respone.JwtResponse;
import com.web.shopweb.dto.respone.ResponeMessage;
import com.web.shopweb.entity.RoleEntity;
import com.web.shopweb.entity.RoleName;
import com.web.shopweb.entity.UserEntity;
import com.web.shopweb.security.jwt.JwtProvider;
import com.web.shopweb.security.userPrincipal.UserPrincipal;
import com.web.shopweb.service.RoleService;
import com.web.shopweb.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
        @Autowired
        private UserService userService;
        @Autowired
        private RoleService roleService;
        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JwtProvider jwtProvider;

        @PostMapping("/signUp")
        public ResponseEntity<ResponeMessage> doSignUp(@RequestBody SignUpFrom signUpFrom) {
                boolean isExistUserName = userService.existsByUsername(signUpFrom.getUsername());
                if (isExistUserName) {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                                        ResponeMessage.builder()
                                                        .status("FAILED")
                                                        .message("This username is already exited")
                                                        .data("")
                                                        .build());
                }
                boolean isExistPhoneNumber = userService.existsByPhoneNumber(signUpFrom.getPhoneNumber());
                if (isExistPhoneNumber) {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                                        ResponeMessage.builder()
                                                        .status("FAILED")
                                                        .message("This phone number is already exited")
                                                        .data("")
                                                        .build());
                }
                boolean isExistEmail = userService.existsByEmail(signUpFrom.getEmail());
                if (isExistEmail) {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                                        ResponeMessage.builder()
                                                        .status("FAILED")
                                                        .message("This email is already exited")
                                                        .data("")
                                                        .build());
                }

                Set<RoleEntity> roles = new HashSet<>();
                if (signUpFrom.getRoles() == null || signUpFrom.getRoles().isEmpty()) {
                        RoleEntity roleEntity = roleService.findByName(RoleName.USER)
                                        .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
                        roles.add(roleEntity);
                } else {
                        signUpFrom.getRoles().forEach(role -> {
                                switch (role) {
                                        case "admin":
                                                RoleEntity adminRole = roleService.findByName(RoleName.ADMIN)
                                                                .orElseThrow(() -> new RuntimeException(
                                                                                "Failed -> NOT FOUND ROLE"));
                                                roles.add(adminRole);
                                                break;
                                        case "pm":
                                                RoleEntity pmRole = roleService.findByName(RoleName.PM)
                                                                .orElseThrow(() -> new RuntimeException(
                                                                                "Failed -> NOT FOUND ROLE"));
                                                roles.add(pmRole);
                                                break;
                                        case "user":
                                                RoleEntity userRole = roleService.findByName(RoleName.USER)
                                                                .orElseThrow(() -> new RuntimeException(
                                                                                "Failed -> NOT FOUND ROLE"));
                                                roles.add(userRole);
                                                break;
                                }
                        });
                }
                UserEntity userEntity = UserEntity.builder()
                                .username(signUpFrom.getUsername())
                                .password(passwordEncoder.encode(signUpFrom.getPassword()))
                                .fullName(signUpFrom.getFullName())
                                .email(signUpFrom.getEmail())
                                .phoneNumber(signUpFrom.getPhoneNumber())
                                .roles(roles)
                                .build();

                return ResponseEntity.ok().body(
                                ResponeMessage.builder()
                                                .status("FAILED")
                                                .message("Account Created Successfully!")
                                                .data(userService.save(userEntity))
                                                .build());

        }

        @PostMapping("/signIn")
        public ResponseEntity<?> doSignIn(@RequestBody SignInFrom signInFrom) {
                try {
                        Authentication authentication = authenticationManager
                                        .authenticate(new UsernamePasswordAuthenticationToken(
                                                        signInFrom.getUsername(),
                                                        signInFrom.getPassword()));
                        String token = jwtProvider.generateToken(authentication);
                        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                        return new ResponseEntity<>(
                                        JwtResponse.builder()
                                                        .status("OK")
                                                        .type("Bearer")
                                                        .fullName(userPrincipal.getFullName())
                                                        .roles(userPrincipal.getAuthorities())
                                                        .token(token)
                                                        .build(),
                                        HttpStatus.OK);
                } catch (AuthenticationException e) {
                        return new ResponseEntity<>(
                                        ResponeMessage.builder()
                                                        .status("FAILED")
                                                        .message("Invalid username or password!")
                                                        .data("")
                                                        .build(),
                                        HttpStatus.UNAUTHORIZED);
                }

        }
}
