package com.pridesys.ticketing.app.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoRequestDto {

    private Long id;
    private String fullName;
    private String mobile;
    private String designation;
    private String office;
    private String password;

}
