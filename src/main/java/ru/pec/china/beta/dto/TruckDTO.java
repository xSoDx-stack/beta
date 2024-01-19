package ru.pec.china.beta.dto;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class TruckDTO {
    private Integer id;
    private String trackName;
    private ZonedDateTime dateCreate;
    private int cargos; //количество грузов
    private Integer processed; //количество обработанных
    private Integer issuance; // количество выданных
}

