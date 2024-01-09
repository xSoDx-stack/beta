package ru.pec.china.beta.converter;

import org.springframework.core.convert.converter.Converter;
import ru.pec.china.beta.dto.TruckDTO;
import ru.pec.china.beta.entity.Truck;

public class TruckDTOtoTruckConverter implements Converter<TruckDTO, Truck> {

    @Override
    public Truck convert(TruckDTO source) {
        var truck = new Truck();
        truck.setTrackName(source.trackName());
        truck.setDateCreate(source.zonedDateTime());
        truck.setCargos(source.cargos());
        return truck;
    }
}
