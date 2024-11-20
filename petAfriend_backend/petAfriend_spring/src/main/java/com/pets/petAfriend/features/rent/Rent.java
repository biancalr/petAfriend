package com.pets.petAfriend.features.rent;

import com.pets.petAfriend.features.client.Client;
import com.pets.petAfriend.features.pet.Pet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "RENT")
public class Rent {

    @Id
    @UuidGenerator
    private UUID id;
    @Column(name = "HOURS")
    private Integer hours;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "STARTS_AT")
    private Date startsAt;
    @Column(name = "CREATED_AT")
    private Date createdAt;
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID", nullable = false)
    private Client client;
    @ManyToOne
    @JoinColumn(name = "PET_ID", referencedColumnName = "ID", nullable = false)
    private Pet pet;

}
