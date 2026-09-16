package com.pridesys.ticketing.app.auth.service;

import com.pridesys.ticketing.app.auth.dto.LoginRequestDto;
import com.pridesys.ticketing.app.auth.dto.UserInfoRequestDto;
import com.pridesys.ticketing.app.auth.dto.UserRegRequestDto;
import com.pridesys.ticketing.app.common.response.Response;

public interface UserService {
    Response register(UserRegRequestDto userRegRequestDto);
    Response userLogin(LoginRequestDto loginRequestDto);
    Response updateInfo(UserInfoRequestDto userInfoRequestDto);
}
