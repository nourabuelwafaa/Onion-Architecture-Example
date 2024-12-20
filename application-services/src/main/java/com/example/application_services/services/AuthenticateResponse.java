package com.example.application_services.services;

public record AuthenticateResponse(String accessToken, String refreshToken) {
}