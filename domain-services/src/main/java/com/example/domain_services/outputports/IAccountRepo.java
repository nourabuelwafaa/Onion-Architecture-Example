package com.example.domain_services.outputports;

import com.example.domain_models.models.account.Account;

import java.util.concurrent.CompletableFuture;

public interface IAccountRepo {
    CompletableFuture<Account> getByMobileNumber(String mobileNumber);

    CompletableFuture<Void> save(Account account);

    CompletableFuture<Boolean> isAdmin(String mobileNumber);
}