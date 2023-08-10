package com.portfolio.communityuser.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 회원에 대한 정보를 담고 있는 UserDto입니다.
 */
@Getter
@Setter
public class UserDto implements UserDetails {
    /**
     * 계정 Id
     */
    private String accountId;

    /**
     * 비밀번호
     */
    private String password;

    /**
     * 이름
     */
    private String name;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.accountId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
