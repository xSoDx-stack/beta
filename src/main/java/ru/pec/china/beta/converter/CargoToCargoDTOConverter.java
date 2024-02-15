package ru.pec.china.beta.converter;

import org.springframework.core.convert.converter.Converter;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.entity.Cargo;
import ru.pec.china.beta.entity.Person;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class CargoToCargoDTOConverter implements Converter<Cargo, CargoDTO> {


    @Override
    public CargoDTO convert(Cargo source) {
        Optional<Person> processedByUser = Optional.ofNullable(source.getProcessedByUser());
        Optional<Person> issuedToClientByUser = Optional.ofNullable(source.getIssuedToClientByUser());
        Optional<Person> issuedAtWarehouseByUser = Optional.ofNullable(source.getIssuedAtWarehouseByUser());

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
                processedByUser.map(Person::getFullName).orElse(null),
                processedByUser.map(Person::getId).orElse(null),
                issuedToClientByUser.map(Person::getFullName).orElse(null),
                issuedToClientByUser.map(Person::getId).orElse(null),
                issuedAtWarehouseByUser.map(Person::getFullName).orElse(null),
                issuedAtWarehouseByUser.map(Person::getId).orElse(null),
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

    private BigDecimal dimension(Double volume, Integer numberOfSeats){
        BigDecimal bd = new BigDecimal(Double.toString(volume/numberOfSeats));
        return bd.setScale(3, RoundingMode.HALF_UP);
    }
}
