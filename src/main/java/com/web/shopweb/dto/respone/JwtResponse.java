package com.web.shopweb.dto.respone;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String status;
    private String token;
    private String type;
    private String fullName;
    private Collection<? extends GrantedAuthority> roles;
}
