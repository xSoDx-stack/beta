package ru.pec.china.beta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pec.china.beta.entity.Cargo;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TruckDTO {
    private Integer id;
    private String trackName;
    private ZonedDateTime zonedDateTime;
    private List<Cargo> cargos;
    private Integer processed; //количество обработанных
    private Integer issuance; // количество выданных
}

