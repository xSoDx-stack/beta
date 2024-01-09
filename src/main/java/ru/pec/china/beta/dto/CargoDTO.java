package ru.pec.china.beta.dto;

public record CargoDTO(
    String clientBarcode,  //ШК клиента
    String pecCode,     //Код ПЭК
    Integer numberOfSeats, //Количество мест
    Integer numberOfSeatsUserScan, //количество отсканированных мест
    Double weight, //Вес
    Double volume, // Объём
    Double dimensions, //габариты
    String recipient, // Получатель
    String city, //Город
    String localOrTransshipment, //Местный или перевалочный
    Integer processedByUser, //Обработка пользователем(id)
    boolean processed, //обработан да/нет
    boolean issuance //выдан да/нет
){}
