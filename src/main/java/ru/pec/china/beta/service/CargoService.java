package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.entity.Cargo;
import ru.pec.china.beta.entity.Person;
import ru.pec.china.beta.repositories.CargoRepositories;
import ru.pec.china.beta.repositories.PersonRepositories;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class CargoService {

    private final CargoRepositories cargoRepositories;
    private final ConversionService conversionService;
    private final PersonRepositories personRepositories;
    private final ZonedDateTime date = ZonedDateTime.now();

    @Autowired
    public CargoService(CargoRepositories cargoRepositories, ConversionService conversionService, PersonRepositories personRepositories) {
        this.cargoRepositories = cargoRepositories;
        this.conversionService = conversionService;
        this.personRepositories = personRepositories;
    }

    public CargoDTO findByOne(UUID id){
        return cargoRepositories.findById(id).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class)).orElseThrow();
    }

    public Page<CargoDTO> findAll(int page, int size){
        return cargoRepositories.findAll(PageRequest.of(pageNumber(page), size)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }

    public Page<CargoDTO> findAllUnloaded(int page, int size){
        return cargoRepositories.findAllByProcessedFalse(PageRequest.of(pageNumber(page), size)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }

    public Page<CargoDTO> findAllByProcessed(int page, int size){
        return cargoRepositories.findAllByProcessedTrueAndIssuanceFalse(PageRequest.of(pageNumber(page),size)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }

    public Page<CargoDTO> findAllByIssuance(int page, int size){
        return cargoRepositories.findAllByIssuanceTrue(PageRequest.of(pageNumber(page),size)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }

    public Page<CargoDTO> findAllByTrackId(Integer id, int page, int size){
        return cargoRepositories.findAllByTruckId(id, PageRequest.of(pageNumber(page), size)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }

    public void cargoUpdate( CargoDTO cargoDTO){
        Cargo cargo = cargoRepositories.findById(cargoDTO.getId()).orElseThrow();

        if(cargoDTO.isProcessed() & !cargoDTO.getPecCode().isEmpty()) {
            cargo.setProcessedByUser(personRepositories.findById(cargoDTO.getProcessedByUser().getId()).orElse(null));
            cargo.setProcessed(true);
            cargo.setPecCode(cargoDTO.getPecCode());
            cargo.setTimeOfProcessed(date);
            cargo.setProcessedByUser(conversionService.convert(cargoDTO.getProcessedByUser(), Person.class));
            if (true) {  //toDo cargoDTO.isClientIssue()
                cargo.setUserClientIssue(personRepositories.findById(cargoDTO.getUserClientIssue().getId()).orElse(null));
                cargo.setTimeClientIssue(date);
                cargo.setClientIssue(true);
                if (cargoDTO.isIssuance()) {
                    cargo.setIssuanceByUser(personRepositories.findById(cargoDTO.getIssuanceByUser().getId()).orElse(null));
                    cargo.setIssuance(true);
                    cargo.setTimeOfIssue(date);
                    cargo.setIssuanceByUser(conversionService.convert(cargoDTO.getIssuanceByUser(), Person.class));
                }
            }
            cargoRepositories.save(cargo);
        }
        else
            System.out.println("Параметры не изменены");

    }

    public int pageNumber(int page){
        return page <= 1 ? 0 : --page;
    }








}
