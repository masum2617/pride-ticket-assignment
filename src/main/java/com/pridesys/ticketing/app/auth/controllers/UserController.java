package com.pridesys.ticketing.app.auth.controllers;

import com.pridesys.ticketing.app.auth.dto.LoginRequestDto;
import com.pridesys.ticketing.app.auth.dto.UserRegRequestDto;
import com.pridesys.ticketing.app.auth.service.UserService;
import com.pridesys.ticketing.app.common.response.Response;
import com.pridesys.ticketing.app.common.response.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Response registerUser(@RequestBody UserRegRequestDto userRegRequestDto) {
        return userService.register(userRegRequestDto);
    }
    @PostMapping("/login")
    public Response userLogin(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.userLogin(loginRequestDto);
    }

    @GetMapping("/test")
    public Response test() {
        return ResponseUtils.createSuccessResponse("SUCCESS TEST");
    }
}
