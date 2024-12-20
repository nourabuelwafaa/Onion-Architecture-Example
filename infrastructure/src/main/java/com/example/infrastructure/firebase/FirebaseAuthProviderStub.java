package com.example.infrastructure.firebase;

import com.example.domain_models.models.account.MobileNumber;
import com.example.domain_services.outputports.IAuthProvider;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Primary
@ConditionalOnProperty(
        name = "firebase.test-mode",
        havingValue = "true",
        matchIfMissing = false
)
public class FirebaseAuthProviderStub implements IAuthProvider {

    private final FirebaseAuthProvider firebaseAuthProvider;

    public FirebaseAuthProviderStub(FirebaseAuthProvider firebaseAuthProvider) {
        this.firebaseAuthProvider = firebaseAuthProvider;
    }

    @Override
    public CompletableFuture<String> getVerifiedMobileNumber(String idToken) {
        try {
            return CompletableFuture.completedFuture(MobileNumber.of(idToken).value());
        } catch (Exception e) {
            return firebaseAuthProvider.getVerifiedMobileNumber(idToken);
        }
    }
}