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
        cargo.setPhoneNumber(source.getPhoneNumber());
        cargo.setLocalOrTransshipment(source.getLocalOrTransshipment());
        cargo.setToTheDoor(source.getToTheDoor());
        cargo.setNameTransportCompany(source.getNameTransportCompany());
        cargo.setPayer(source.getPayer());
        cargo.setFormOfPayment(source.getFormOfPayment());
        cargo.setRelabelingPecCode(source.isRelabelingPecCode());
        cargo.setOldPecCode(source.getOldPecCode());
        cargo.setNote(source.getNote());
        cargo.setShippingToRegions(source.isShippingToRegions());
        cargo.setTimeOfIssue(source.getTimeOfIssueAtWarehouse());
        cargo.setTimeOfProcessed(source.getTimeOfProcessed());
        cargo.setIssuedToClientByUserId(source.getIssuedToClientByUserId());
        cargo.setIssuedAtWarehouseByUserId(source.getIssuedAtWarehouseByUserId());
        cargo.setProcessedByUserId(source.getProcessedByUserId());
        cargo.setClientIssue(source.isClientIssue());
        cargo.setIssuance(source.isIssuance());
        cargo.setProcessed(source.isProcessed());
        return cargo;
    }
}
