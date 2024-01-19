package ru.pec.china.beta.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    private String city; //город
    private String localOrTransshipment; //местный или перевалочный

    private PersonDTO processedByUser; //кто обработал груз
    private PersonDTO userClientIssue; //кто сделал клиентскую выдачу
    private PersonDTO issuanceByUser; //кто сделал выдачу
    private ZonedDateTime timeOfIssue; //Время выдачи
    private ZonedDateTime timeOfProcessed; //Время обработки
    private ZonedDateTime timeOfClientIssue; //время клиентской выдачи
    private String truckName; //имя рейса
    private Integer truckId;
    private boolean processed;//обработан да/нет
    private boolean issuance; //выдан да/нет
    private boolean clientIssue; // клиентская выдача
}


