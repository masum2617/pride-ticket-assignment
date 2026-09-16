package com.pridesys.ticketing.app.auth.service;

import com.pridesys.ticketing.app.auth.dto.LoginRequestDto;
import com.pridesys.ticketing.app.auth.dto.LoginResponseDTO;
import com.pridesys.ticketing.app.auth.dto.UserInfoRequestDto;
import com.pridesys.ticketing.app.auth.dto.UserRegRequestDto;
import com.pridesys.ticketing.app.auth.entity.RoleEntity;
import com.pridesys.ticketing.app.auth.entity.UserEntity;
import com.pridesys.ticketing.app.auth.repository.RoleRepo;
import com.pridesys.ticketing.app.auth.repository.UserRepo;
import com.pridesys.ticketing.app.common.response.Response;
import com.pridesys.ticketing.app.common.response.ResponseUtils;
import com.pridesys.ticketing.common.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @Override
    public Response register(UserRegRequestDto userRegRequestDto) {
        if (userRegRequestDto.getEmail() == null || userRegRequestDto.getEmail().trim().isEmpty()) {
            return ResponseUtils.createFailedResponse("Email address is required");
        }
        if (userRegRequestDto.getPassword() == null || userRegRequestDto.getPassword().isBlank()) {
            return ResponseUtils.createFailedResponse("Password is required");
        }
        if (userRegRequestDto.getRoleId() == null) {
            return ResponseUtils.createFailedResponse("Role is required");
        }
        if (userRepo.existsByEmail(userRegRequestDto.getEmail())) {
            return ResponseUtils.createFailedResponse("Email already registered");
        }

        try {
            RoleEntity role = roleRepo.findById(userRegRequestDto.getRoleId())
                    .orElseThrow(() -> new RuntimeException(
                            "Role not found with id: " + userRegRequestDto.getRoleId()));

            String hashPwd = passwordEncoder.encode(userRegRequestDto.getPassword());

            UserEntity user = new UserEntity();
            user.setFullName(userRegRequestDto.getFullName());
            user.setEmail(userRegRequestDto.getEmail());
            user.setPassword(passwordEncoder.encode(userRegRequestDto.getPassword()));
            user.setMobile(userRegRequestDto.getMobileNumber());
            user.setDesignation(userRegRequestDto.getDesignation());
            user.setOffice(userRegRequestDto.getOffice());
            user.setActiveFlag(1);

            //set role
            user.getRoles().add(role);

            UserEntity savedUser = userRepo.save(user);

            if(savedUser.getId() != null) {
                return ResponseUtils.createSuccessResponse("User Created");
            } else {
                return ResponseUtils.createFailedResponse("User Creation Failed!");
            }
        } catch (Exception ex) {
            return ResponseUtils.createFailedResponse(ex.getMessage());
        }
    }

    @Override
    public Response userLogin(LoginRequestDto loginRequestDto) {
        Authentication request = UsernamePasswordAuthenticationToken.unauthenticated(
                loginRequestDto.email(), loginRequestDto.password());

        Authentication result = authenticationManager.authenticate(request);

        if (result == null || !result.isAuthenticated()) {
            return ResponseUtils.createFailedResponse("Invalid username or password");

        }

        String jwt = jwtTokenService.generateToken(result);

        return ResponseUtils.createSuccessResponse("Login Successful", jwt);
    }

    @Override
    public Response updateInfo(UserInfoRequestDto userInfoRequestDto) {
        if (userInfoRequestDto.getId() == null) {
            return ResponseUtils.createFailedResponse("User id is required");
        }

        UserEntity user = userRepo.findById(userInfoRequestDto.getId())
                .orElseThrow(() -> new RuntimeException("No User Found"));

        if(userInfoRequestDto.getPassword() != null) {
            String hashPwd = passwordEncoder.encode(userInfoRequestDto.getPassword());
            user.setPassword(hashPwd);
        }
        user.setFullName(userInfoRequestDto.getFullName());
        user.setMobile(userInfoRequestDto.getMobile());
        user.setDesignation(userInfoRequestDto.getDesignation());
        user.setOffice(userInfoRequestDto.getOffice());

        userRepo.save(user);

        return ResponseUtils.createSuccessResponse("User Updated");
    }

}
