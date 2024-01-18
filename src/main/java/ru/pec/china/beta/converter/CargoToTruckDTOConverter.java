package ru.pec.china.beta.converter;

import org.springframework.core.convert.converter.Converter;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.entity.Cargo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CargoToTruckDTOConverter implements Converter<Cargo, CargoDTO> {
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
                dimension(source.getVolume(),source.getNumberOfSeats()),
                source.getRecipient(),
                source.getCity(),
                source.getProcessedByUser(),
                source.getLocalOrTransshipment(),
                source.getIssuanceByUser(),
                source.getTimeOfIssue(),
                source.getTimeOfProcessed(),
                source.getTruckId(),
                source.getTruck(),
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
