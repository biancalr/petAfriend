package com.pets.petAfriend.features.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long>, JpaRepository<Client, Long> {
    boolean existsByUsername(String username);
}
