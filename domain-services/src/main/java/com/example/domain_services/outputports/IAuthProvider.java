package com.example.domain_services.outputports;

import java.util.concurrent.CompletableFuture;

public interface IAuthProvider {
    CompletableFuture<String> getVerifiedMobileNumber(String idToken);
}