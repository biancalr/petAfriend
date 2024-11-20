package com.pets.petAfriend.features.pet;

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
@Table(name = "PET")
public class Pet {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
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
