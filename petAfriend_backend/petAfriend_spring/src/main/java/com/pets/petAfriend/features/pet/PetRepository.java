package com.pets.petAfriend.features.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PetRepository extends PagingAndSortingRepository<Pet, UUID>, JpaRepository<Pet, UUID> {
}