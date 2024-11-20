package com.pets.petAfriend.features.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, UUID>, JpaRepository<Client, UUID> {
    boolean existsByUsername(String username);
}
