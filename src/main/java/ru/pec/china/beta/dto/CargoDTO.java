package ru.pec.china.beta.dto;

import lombok.*;
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
    private Integer numberOfSeatsUserScan = 0; //количество отсканированных мест
    private Double weight; //Вес
    private Double volume; // Объём
    private BigDecimal dimensions; //габариты
    private String recipient; // Получатель
    private String city; //город
    private Integer processedByUser; //Обработка
    private String localOrTransshipment; //обработка пользователем(id)
    private Integer issuanceByUser; //Выдан пользователем
    private ZonedDateTime timeOfIssue; //Время выдачи
    private ZonedDateTime timeOfProcessed; //Время обработки
    private Integer truckId;
    private Truck truck;
    private boolean processed;//обработан да/нет
    private boolean issuance; //выдан да/нет
    private boolean clientIssue; // клиентская выдача
}


