package ru.pec.china.beta.converter;

import org.springframework.core.convert.converter.Converter;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.entity.Cargo;

public class CargoDTOtoCargoConverter implements Converter<CargoDTO, Cargo> {
    @Override
    public Cargo convert(CargoDTO source) {
        var cargo = new Cargo();
        cargo.setId(source.getId());
        cargo.setClientBarcode(source.getClientBarcode());
        cargo.setPecCode(source.getPecCode());
        cargo.setNumberOfSeats(source.getNumberOfSeats());
        cargo.setNumberOfSeatsUserScan(source.getNumberOfSeatsUserScan());
        cargo.setWeight(source.getWeight());
        cargo.setVolume(source.getVolume());
        cargo.setRecipient(source.getRecipient());
        cargo.setCity(source.getCity());
        cargo.setLocalOrTransshipment(source.getLocalOrTransshipment());
        cargo.setProcessedByUser(source.getProcessedByUser());
        cargo.setIssuanceByUser(source.getIssuanceByUser());
        cargo.setTimeOfIssue(source.getTimeOfIssue());
        cargo.setTimeOfProcessed(source.getTimeOfProcessed());
        cargo.setTruckId(source.getTruckId());
        cargo.setClientIssue(source.isClientIssue());
        cargo.setIssuance(source.isIssuance());
        cargo.setProcessed(source.isProcessed());
        return cargo;
    }
}
