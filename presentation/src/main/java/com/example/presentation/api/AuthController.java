package com.example.presentation.api;

import com.example.application_services.services.AccountsService;
import com.example.application_services.services.Authenticate;
import com.example.domain_services.outputports.ILogger;
import com.example.presentation.api.common.DeferredResults;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class AuthController {
    private final AccountsService accountsService;
    private final ILogger logger;

    public AuthController(AccountsService accountsService, ILogger logger) {
        this.accountsService = accountsService;
        this.logger = logger;
    }

    @PostMapping("/v1/accounts/authenticate")
    public DeferredResult<ResponseEntity<?>> login(@RequestBody Authenticate authenticate) {
        logger.log("Processing authentication request");
        return DeferredResults.from(
                accountsService.handle(authenticate)
                        .thenApply(ResponseEntity::ok)
        );
    }
}

