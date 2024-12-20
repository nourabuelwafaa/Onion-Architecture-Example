package com.example.infrastructure.repositories;

import static com.mongodb.client.model.Filters.eq;

import com.example.domain_services.outputports.IInvitationRepo;
import com.example.infrastructure.db.InvitationEntity;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Repository
public class InvitationRepo implements IInvitationRepo {
    private static final String COLLECTION = "invitations";

    private final MongoCollection<InvitationEntity> collection;

    public InvitationRepo(MongoDatabase mongoDatabase) {
        this.collection = mongoDatabase.getCollection(COLLECTION, InvitationEntity.class);
    }

    @Override
    public CompletableFuture<Boolean> hasInvitation(String mobileNumber) {
        return CompletableFuture.supplyAsync(() ->
                collection.find(eq("mobileNumber", mobileNumber)).first() != null
        );
    }
}