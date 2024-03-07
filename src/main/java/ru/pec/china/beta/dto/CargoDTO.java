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
    private BigDecimal weightOfOnePiece; //все 1 места
    private String recipient; // Получатель
    private String city; //город
    private String phoneNumber;  // номер телефона
    private String localOrTransshipment; //местный или перевалочный
    private String toTheDoor; // до двери
    private String nameTransportCompany; //название ТК
    private String payer; // плательщик
    private String formOfPayment; // форма оплаты
    private String relabelingPecCode; // перимаркировка
    private String note; // примечание
    private boolean shippingToRegions;  //отправка на регионы

    private String processedByUser; //кто обработал груз
    private Integer processedByUserId; //кто обработал груз id

    private String issuedToClientByUser; //кто сделал клиентскую выдачу
    private Integer issuedToClientByUserId; //кто сделал клиентскую выдачу id

    private String issuedAtWarehouseByUser; //кто сделал cкладскую выдачу
    private Integer issuedAtWarehouseByUserId; //кто сделал cкладскую выдачу id


    private ZonedDateTime timeOfIssueAtWarehouse; //Время выдачи
    private ZonedDateTime timeOfProcessed; //Время обработки
    private ZonedDateTime timeOfIssueToClient; //время клиентской выдачи

    private String truckName; //имя рейса
    private Integer truckId; //имя рейса id
    private boolean processed;//обработан да/нет
    private boolean issuance; //выдан да/нет
    private boolean clientIssue; // клиентская выдача
}


