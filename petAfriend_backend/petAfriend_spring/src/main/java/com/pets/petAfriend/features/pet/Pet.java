package com.pets.petAfriend.features.pet;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "PET")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TYPE")
    private String specie;
    @Column(name = "SPECIE")
    private String breed;
    @Column(name = "PERSONALITY")
    private String personality;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CREATED_AT")
    private Date createdAt;
}
