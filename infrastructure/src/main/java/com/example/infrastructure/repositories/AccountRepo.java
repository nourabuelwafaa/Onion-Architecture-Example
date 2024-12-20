package com.example.infrastructure.repositories;

import static com.mongodb.client.model.Filters.*;

import com.example.domain_models.models.account.Account;
import com.example.domain_services.outputports.IAccountRepo;
import com.example.infrastructure.db.AccountEntity;
import com.example.infrastructure.db.RevokedAccountEntity;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
public class AccountRepo implements IAccountRepo {
    private static final String ACCOUNTS_COLLECTION = "accounts";
    private static final String REVOKED_ACCOUNT_COLLECTION = "revoked_accounts";

    private final MongoCollection<AccountEntity> collection;
    private final List<String> admins;

    public AccountRepo(
            @Value("${accounts.admins}") List<String> admins,
            MongoDatabase mongoDatabase
    ) {
        this.admins = admins;
        this.collection = mongoDatabase.getCollection(ACCOUNTS_COLLECTION, AccountEntity.class);
        mongoDatabase.getCollection(REVOKED_ACCOUNT_COLLECTION, RevokedAccountEntity.class);
    }

    @Override
    public CompletableFuture<Account> getByMobileNumber(String mobileNumber) {
        return CompletableFuture.supplyAsync(() ->
                Optional.ofNullable(collection.find(eq("mobileNumber", mobileNumber)).first())
                        .map(AccountEntity::toDomain)
                        .orElse(null)
        );
    }

    @Override
    public CompletableFuture<Void> save(Account account) {
        return CompletableFuture.supplyAsync(() -> {
            var entity = AccountEntity.from(account);
            collection.replaceOne(
                    eq("accountId", entity.accountId()),
                    entity,
                    new ReplaceOptions().upsert(true)
            );
            return null;
        });
    }

    @Override
    public CompletableFuture<Boolean> isAdmin(String mobileNumber) {
        return CompletableFuture.completedFuture(admins.contains(mobileNumber));
    }
}