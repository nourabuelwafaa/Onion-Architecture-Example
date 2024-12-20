package com.example.domain_models.outputports;


import com.example.domain_models.models.account.Account;
import com.example.domain_models.models.device.Device;

public interface IJWTGenerator {
    String generateAccessToken(Account account, Device device);

    String generateRefreshToken(Account account, Device device);
}