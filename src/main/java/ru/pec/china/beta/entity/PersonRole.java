package ru.pec.china.beta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "person_role")

public class PersonRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    private Person person;

    @Column(name = "person_id")
    private Integer personId;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;

    @Column(name = "date_from")
    private ZonedDateTime dateFrom;

    @Column(name = "date_to")
    private ZonedDateTime dateTo;

    @Column(name = "region")
    private Integer region;
}
