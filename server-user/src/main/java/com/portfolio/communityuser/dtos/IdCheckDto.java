package com.portfolio.communityuser.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class IdCheckDto {

    @NotBlank(message = "{notBlank.userDto.accountId}")
    @Size(min = 4, max = 11, message = "{size.userDto.accountId}")
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "{pattern.userDto.accountId}")
    private String accountId;
}
