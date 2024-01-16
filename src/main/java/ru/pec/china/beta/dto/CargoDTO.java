package ru.pec.china.beta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pec.china.beta.entity.Truck;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CargoDTO {
    private UUID id;
    private String clientBarcode;  //ШК клиента
    private String pecCode;     //Код ПЭК
    private Integer numberOfSeats; //Количество мест
    private Integer numberOfSeatsUserScan; //количество отсканированных мест
    private Double weight; //Вес
    private Double volume; // Объём
    private BigDecimal dimensions; //габариты
    private String recipient; // Получатель
    private String city;
    private Integer processedByUser; //Обработка
    private String localOrTransshipment; //обработка пользователем(id)
    private Integer issuanceByUser; //Выдан пользователем
    private ZonedDateTime timeOfIssue; //Время выдачи
    private Truck track;
    private boolean processed;//обработан да/нет
    private boolean issuance; //выдан да/нет
}


