package ru.pec.china.beta.converter;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.dto.PersonDTO;
import ru.pec.china.beta.entity.Cargo;
import ru.pec.china.beta.entity.Person;

public class CargoDTOtoCargoConverter implements Converter<CargoDTO, Cargo> {

    private ConversionService conversionService;


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
        cargo.setTimeOfIssue(source.getTimeOfIssue());
        cargo.setTimeOfProcessed(source.getTimeOfProcessed());
        cargo.setUserClientIssue(person(source.getUserClientIssue()));
        cargo.setIssuanceByUser(person(source.getIssuanceByUser()));
        cargo.setProcessedByUser(person(source.getProcessedByUser()));
        cargo.setClientIssue(source.isClientIssue());
        cargo.setIssuance(source.isIssuance());
        cargo.setProcessed(source.isProcessed());
        return cargo;
    }

    private Person person(PersonDTO personDTO){
        return conversionService.convert(personDTO, Person.class);
    }
}
