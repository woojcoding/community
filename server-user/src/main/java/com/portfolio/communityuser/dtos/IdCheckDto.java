package com.portfolio.communityuser.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class IdCheckDto {

    @NotBlank(message = "{NotBlank.userDto.accountId}")
    @Size(min = 4, max = 11, message = "{Size.userDto.accountId}")
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "{Pattern.userDto.accountId}")
    private String accountId;
}
