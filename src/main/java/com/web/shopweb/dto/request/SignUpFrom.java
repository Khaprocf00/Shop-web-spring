package com.web.shopweb.dto.request;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpFrom {
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Set<String> roles = new HashSet<>();
}
