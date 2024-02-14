package ru.pec.china.beta.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Truck {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "truck_name")
    private String trackName;

    @Column(name = "date_create")
    private ZonedDateTime dateCreate;

    @OneToMany(mappedBy = "truckId", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Cargo> cargos;

}
