package com.pridesys.ticketing.app.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegRequestDto {

    private String email;
    private String password;
    private String fullName;
    private String mobileNumber;
    private String designation;
    private String office;
    private Long roleId;
}
