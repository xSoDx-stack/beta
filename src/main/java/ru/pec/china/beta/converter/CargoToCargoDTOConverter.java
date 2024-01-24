package ru.pec.china.beta.converter;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.dto.PersonDTO;
import ru.pec.china.beta.entity.Cargo;
import ru.pec.china.beta.entity.Person;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CargoToCargoDTOConverter implements Converter<Cargo, CargoDTO> {
    private ConversionService conversionService;

    @Override
    public CargoDTO convert(Cargo source) {
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
                source.getProcessedByUser().getFullName(),
                source.getProcessedByUser().getId(),
                source.getUserClientIssue().getFullName(),
                source.getUserClientIssue().getId(),
                source.getIssuanceByUser().getFullName(),
                source.getIssuanceByUser().getId(),
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

    private PersonDTO person(Person person){
        if(person != null)
            return conversionService.convert(person, PersonDTO.class);
        return null;
    }




    private BigDecimal dimension(Double volume, Integer numberOfSeats){
        BigDecimal bd = new BigDecimal(Double.toString(volume/numberOfSeats));
        return bd.setScale(3, RoundingMode.HALF_UP);
    }
}
