package com.pridesys.ticketing.common.dtos;

public record TokenResponseDto(
        String accessToken,
        String refreshToken,
        String tokenType,
        long accessTokenExpiresIn,
        long refreshTokenExpiresIn
) {}
