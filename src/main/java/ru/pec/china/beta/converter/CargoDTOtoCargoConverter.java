package ru.pec.china.beta.converter;

import org.springframework.core.convert.converter.Converter;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.entity.Cargo;

public class CargoDTOtoCargoConverter implements Converter<CargoDTO, Cargo> {
    @Override
    public Cargo convert(CargoDTO source) {
        var cargo = new Cargo();
        cargo.setClientBarcode(source.clientBarcode());
        cargo.setPecCode(source.pecCode());
        cargo.setNumberOfSeats(source.numberOfSeats());
        cargo.setNumberOfSeatsUserScan(source.numberOfSeatsUserScan());
        cargo.setWeight(source.weight());
        cargo.setVolume(source.volume());
        cargo.setRecipient(source.recipient());
        cargo.setCity(source.city());
        cargo.setLocalOrTransshipment(source.localOrTransshipment());
        cargo.setProcessedByUser(source.processedByUser());

        return cargo;
    }
}
