package com.example.application_services.services;


import com.example.domain_models.models.account.Account;
import com.example.domain_models.exceptions.AppException;
import com.example.domain_models.models.account.Tokens;
import com.example.domain_models.outputports.IJWTGenerator;
import com.example.domain_services.outputports.IAccountRepo;
import com.example.domain_services.outputports.IAuthProvider;
import com.example.domain_services.outputports.IInvitationRepo;
import com.example.domain_services.outputports.ILogger;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AccountsService {
    private final IAccountRepo accountRepo;
    private final IInvitationRepo invitationRepo;
    private final IAuthProvider authProvider;
    private final IJWTGenerator jwtGenerator;

    private final ILogger logger;

    public AccountsService(
            IAccountRepo accountRepo,
            IInvitationRepo invitationRepo,
            IAuthProvider authProvider,
            IJWTGenerator jwtGenerator,
            ILogger logger
    ) {
        this.accountRepo = accountRepo;
        this.invitationRepo = invitationRepo;
        this.authProvider = authProvider;
        this.jwtGenerator = jwtGenerator;
        this.logger = logger;
    }

    public CompletableFuture<AuthenticateResponse> handle(Authenticate command) {
        return CompletableFuture.supplyAsync(() -> {
            logger.log("Handling authentication for idToken: {}" + command.idToken());

            String mobileNumber = authProvider.getVerifiedMobileNumber(command.idToken()).join();

            logger.log("check for account: {}" + command);

            Account account = existingAccountOrNewAccount(mobileNumber, command);

            logger.log(" finish check for account: {}" + command.idToken());

            Tokens tokens = account.authenticate(jwtGenerator, command.deviceId(), command.deviceType());

            accountRepo.save(account);
            logger.log("Authentication successful for account: {}" + account.getMobileNumber());

            return new AuthenticateResponse(tokens.accessToken(), tokens.refreshToken());
        });
    }

    private Account existingAccountOrNewAccount(String mobileNumber, Authenticate request) {
        logger.log("get the account by mobile number");
        var account = accountRepo.getByMobileNumber(mobileNumber).join();
        if (accountExists(account)) {
            return account;
        }
        return createNewAccount(mobileNumber, request.deviceType(), request.deviceId());
    }

    private static boolean accountExists(Account existingAccount) {
        return existingAccount != null;
    }

    private Account createNewAccount(String mobileNumber, String aDeviceType, String aDeviceId) {
        boolean isAdmin = accountRepo.isAdmin(mobileNumber).join();
        boolean hasNoInvitations = !invitationRepo.hasInvitation(mobileNumber).join();
        assertIsAdminOrInvited(mobileNumber, isAdmin, hasNoInvitations);

        return Account.newAccount(mobileNumber, isAdmin, aDeviceType, aDeviceId);
    }

    private void assertIsAdminOrInvited(String mobileNumber, boolean isAdmin, boolean hasNoInvitations) {
        if (!isAdmin && hasNoInvitations) {
            logger.log("Account not invited: {}" + mobileNumber);
            throw new AppException.RequirementException("Account not invited to use the App");
        }
    }
}
