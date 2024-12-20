package com.example.domain_services.outputports;


import java.util.concurrent.CompletableFuture;

public interface IInvitationRepo {

    CompletableFuture<Boolean> hasInvitation(String mobileNumber);
}