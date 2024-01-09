package ru.pec.china.beta.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cargo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "client_barcode")
    private String clientBarcode;  //ШК клиента

    @Column(name = "pec_code")
    private String pecCode;     //Код ПЭК

    @Column(name = "number_of_seats")
    private Integer numberOfSeats; //Количество мест

    @Column(name = "number_of_seats_user_scan", columnDefinition = "integer default 0")
    private int numberOfSeatsUserScan;

    @Column(name = "weight")
    private Double weight; //Вес

    @Column(name = "volume")
    private Double volume; // Объём

    @Column(name = "recipient")
    private String recipient; // Получатель

    @Column(name = "city")
    private String city; //Город

    @Column(name = "local_or_transshipment")
    private String localOrTransshipment; //Местный или перевалочный

    @Column(name = "issuance", columnDefinition = "boolean  default false")
    private boolean issuance; //выданный да/нет

    @Column(name = "processed_by_user")
    private Integer processedByUser; //Обработка пользователем(id)

    @Column(name = "processed", columnDefinition = "boolean  default false")
    private boolean processed; //обработанный да/нет

    @ManyToOne
    @JoinColumn(name = "track_id", nullable = false, updatable = false, insertable = false)
    private Truck truck;

    @Column(name = "track_id")
    private Integer truckId;
}
