package ru.pec.china.beta.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.ZonedDateTime;
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

    @Column(name = "phone-number")
    private String phoneNumber; // номер телефона

    @Column(name = "local_or_transshipment")
    private String localOrTransshipment; //Местный или перевалочный

    @Column(name = "to-the-door")
    private String toTheDoor; // до двери

    @Column(name = "name-transport-company")
    private String nameTransportCompany; //название ТК

    @Column(name = "payer")
    private String payer; // плательщик

    @Column(name = "form_of_payment")
    private String formOfPayment; // форма оплаты

    @Column(name = "issuance", columnDefinition = "boolean  default false")
    private boolean issuance; //выданный да/нет

    @ManyToOne
    @JoinColumn(name = "processed_by_user", updatable = false, insertable = false)
    @Cascade(CascadeType.DETACH)
    private Person processedByUser; //Обработка пользователем

    @Column(name = "processed_by_user")
    private Integer processedByUserId;

    @ManyToOne
    @JoinColumn(name = "issuance_by_user", updatable = false, insertable = false)
    @Cascade(CascadeType.DETACH)
    private Person issuedAtWarehouseByUser; //Выдан пользователем

    @Column(name = "issuance_by_user")
    private Integer issuedAtWarehouseByUserId;

    @Column(name = "time_of_issue")
    private ZonedDateTime timeOfIssue; //Время выдачи

    @Column(name = "processed", columnDefinition = "boolean default false")
    private boolean processed; //обработанный да/нет

    @Column(name = "time_of_processed")
    private ZonedDateTime timeOfProcessed; //Время обработки

    @Column(name = "client_issue", columnDefinition = "boolean default false")
    private boolean clientIssue; //клиентская выдача

    @ManyToOne
    @JoinColumn(name = "user_client_issue", updatable = false, insertable = false)
    @Cascade(CascadeType.DETACH)
    private Person issuedToClientByUser; // кто сделал клиентскую выдачу

    @Column(name = "user_client_issue")
    private Integer issuedToClientByUserId;

    @Column(name = "time_client_issue")
    private ZonedDateTime timeClientIssue; //время клиентской выдачи

    @ManyToOne
    @JoinColumn(name = "track_id", nullable = false, updatable = false, insertable = false)
    private Truck truck;

    @Column(name = "track_id")
    private Integer truckId; // Ид рейса

    @Column(name = "note")
    private String note; // примечание

    @Column(name = "old_pec_code")
    private String oldPecCode; // старый код ПЭК

    @Column(name = "relabeling_pec_code", columnDefinition = "boolean default false")
    private boolean relabelingPecCode; // перемаркировка да/нет

    @Column(name = "shipping_to_regions", columnDefinition = "boolean default false")
    private boolean shippingToRegions;  //отправка на регионы
}
