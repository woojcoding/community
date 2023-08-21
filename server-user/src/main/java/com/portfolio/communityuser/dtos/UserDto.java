package com.portfolio.communityuser.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "{notBlank.userDto.accountId}")
    @Size(min = 4, max = 11, message = "{size.userDto.accountId}")
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "{pattern.userDto.accountId}")
    private String accountId;

    /**
     * 패스워드
     */
    @NotBlank(message = "{notBlank.userDto.password}")
    @Size(min = 4, max = 11, message = "{size.userDto.password}")
    @Pattern(regexp = "^(?!.*(.)\\1\\1)[a-zA-Z0-9!@#$%^&*()-_+=]{4,11}$",
            message = "{pattern.userDto.password}")
    private String password;

    /**
     * 이름
     */
    @NotBlank(message = "{notBlank.userDto.name}")
    @Size(min = 2, max = 4, message = "{size.userDto.name}")
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
