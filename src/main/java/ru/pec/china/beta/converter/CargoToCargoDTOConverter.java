package ru.pec.china.beta.converter;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.dto.PersonDTO;
import ru.pec.china.beta.entity.Cargo;
import ru.pec.china.beta.entity.Person;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class CargoToCargoDTOConverter implements Converter<Cargo, CargoDTO> {
    private ConversionService conversionService;

    @Override
    public CargoDTO convert(Cargo source) {
        Person processedByUser = source.getProcessedByUser();
        Person issuedToClientByUser = source.getIssuedToClientByUser();
        Person issuedAtWarehouseByUser = source.getIssuedAtWarehouseByUser();

        return new CargoDTO(
                source.getId(),
                source.getClientBarcode(),
                source.getPecCode(),
                source.getNumberOfSeats(),
                source.getNumberOfSeatsUserScan(),
                source.getWeight(),
                source.getVolume(),
                dimension(source.getVolume(), source.getNumberOfSeats()),
                source.getRecipient(),
                source.getCity(),
                source.getLocalOrTransshipment(),
                person(processedByUser).map(PersonDTO::getFullName).orElse(null),
                person(processedByUser).map(PersonDTO::getId).orElse(null),
                person(issuedToClientByUser).map(PersonDTO::getFullName).orElse(null),
                person(issuedToClientByUser).map(PersonDTO::getId).orElse(null),
                person(issuedAtWarehouseByUser).map(PersonDTO::getFullName).orElse(null),
                person(issuedAtWarehouseByUser).map(PersonDTO::getId).orElse(null),
                source.getTimeOfIssue(),
                source.getTimeOfProcessed(),
                source.getTimeOfIssue(),
                source.getTruck().getTrackName(),
                source.getTruckId(),
                source.isProcessed(),
                source.isIssuance(),
                source.isClientIssue()
        );
    }


    private Optional<PersonDTO> person(Person person){
        if(person !=null)
            return Optional.ofNullable(conversionService.convert(person, PersonDTO.class));
        return Optional.empty();
    }

    private BigDecimal dimension(Double volume, Integer numberOfSeats){
        BigDecimal bd = new BigDecimal(Double.toString(volume/numberOfSeats));
        return bd.setScale(3, RoundingMode.HALF_UP);
    }
}
