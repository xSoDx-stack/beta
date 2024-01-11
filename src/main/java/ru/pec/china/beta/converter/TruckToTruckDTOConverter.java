package ru.pec.china.beta.converter;

import org.springframework.core.convert.converter.Converter;
import ru.pec.china.beta.dto.TruckDTO;
import ru.pec.china.beta.entity.Cargo;
import ru.pec.china.beta.entity.Truck;

public class TruckToTruckDTOConverter implements Converter<Truck, TruckDTO> {

    @Override
    public TruckDTO convert(Truck source) {
        return new TruckDTO(
                source.getId(),
                source.getTrackName(),
                source.getDateCreate(),
                source.getCargos(),
                source.getCargos().stream().filter(Cargo::isProcessed).toList().size(),
                source.getCargos().stream().filter(Cargo::isIssuance).toList().size()
        );
    }

}


