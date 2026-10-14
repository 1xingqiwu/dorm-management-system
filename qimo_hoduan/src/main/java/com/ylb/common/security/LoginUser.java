package com.ylb.common.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ylb.entity.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUser implements UserDetails {

    private SysUser sysUser;

    // 必须保留无参构造
    public LoginUser() {}

    public LoginUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    @Override
    @JsonIgnore // 🔴 关键：告诉 Jackson，序列化时不要把这个字段写进 JSON
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (sysUser == null) return Collections.emptyList();

        String roleName = switch (sysUser.getUserType()) {
            case 0 -> "ROLE_STUDENT";
            case 1 -> "ROLE_COUNSELOR";
            case 2 -> "ROLE_DORM_MANAGER";
            case 3 -> "ROLE_ADMIN";
            default -> "ROLE_USER";
        };
        return Collections.singletonList(new SimpleGrantedAuthority(roleName));
    }

    @Override
    @JsonIgnore // 忽略密码字段，防止反序列化冲突
    public String getPassword() {
        return sysUser != null ? sysUser.getPassword() : null;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return sysUser != null ? sysUser.getUsername() : null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() { return true; }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() { return sysUser != null && sysUser.getStatus() == 1; }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    @JsonIgnore
    public boolean isEnabled() { return sysUser != null && sysUser.getIsDeleted() == 0; }
}