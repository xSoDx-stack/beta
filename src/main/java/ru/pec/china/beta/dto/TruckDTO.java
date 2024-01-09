package ru.pec.china.beta.dto;

import ru.pec.china.beta.entity.Cargo;

import java.time.ZonedDateTime;
import java.util.List;

public record TruckDTO(
        Integer id,
        String trackName,
        ZonedDateTime zonedDateTime,
        List<Cargo> cargos
){}
