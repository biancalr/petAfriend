package com.pets.petAfriend.features.rent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface RentRepository extends PagingAndSortingRepository<Rent, UUID>, JpaRepository<Rent, UUID> {

    List<Rent> findByStatusAndPet_Id(String status, UUID id);

    @Query(value = "SELECT * FROM RENT WHERE STATUS = 'STARTED' AND DATE_ADD(STARTS_AT, INTERVAL HOURS HOUR) = NOW();", nativeQuery = true)
    List<Rent> findAllFinishedByNow();

    @Query("select r from Rent r where r.status = 'CREATED' and r.startsAt = ?1")
    List<Rent> findAllStartedByNow(Date startsAt);
}
