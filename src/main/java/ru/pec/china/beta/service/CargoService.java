package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.entity.Cargo;
import ru.pec.china.beta.repositories.CargoRepositories;
import ru.pec.china.beta.repositories.PersonRepositories;
import ru.pec.china.beta.util.CargoNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public CargoDTO findByOne(UUID id) throws CargoNotFoundException {
        return cargoRepositories.findById(id).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class)).orElseThrow(CargoNotFoundException::new);
    }

    public Page<CargoDTO> findAll(int page, int size){
        return cargoRepositories.findAll(PageRequest.of(pageNumber(page), size)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }
    public List<CargoDTO>findAll(){
        return cargoRepositories.findAll().stream().map(cargo ->
                conversionService.convert(cargo, CargoDTO.class)).collect(Collectors.toList());
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

    public void cargoUpdate( CargoDTO cargoDTO) throws CargoNotFoundException {
        Cargo cargo = cargoRepositories.findById(cargoDTO.getId()).orElseThrow(CargoNotFoundException::new);

        if(cargoDTO.isProcessed() & !cargoDTO.getPecCode().isEmpty()) {
//            cargo.setProcessedByUser(personRepositories.findById(cargoDTO.getProcessedByUserId()).orElse(null));
            cargo.setProcessed(true);
            cargo.setPecCode(cargoDTO.getPecCode());
            cargo.setTimeOfProcessed(date);
//            cargo.setProcessedByUser(conversionService.convert(cargoDTO.getProcessedByUser(), Person.class));
            if (true) {  //toDo cargoDTO.isClientIssue()
//                cargo.setIssuedToClientByUser(personRepositories.findById(cargoDTO.getIssuedToClientByUserId()).orElse(null));
                cargo.setTimeClientIssue(date);
                cargo.setClientIssue(true);
                if (cargoDTO.isIssuance()) {
//                    cargo.setIssuedAtWarehouseByUser(personRepositories.findById(cargoDTO.getIssuedAtWarehouseByUserId()).orElse(null));
                    cargo.setIssuance(true);
                    cargo.setTimeOfIssue(date);
                }
            }
            cargoRepositories.save(cargo);
        }
    }

    private int pageNumber(int page){
        return page <= 1 ? 0 : --page;
    }

    public CargoDTO deletePecCode(UUID id) throws CargoNotFoundException {
        Cargo cargo = cargoRepositories.findById(id).orElseThrow(CargoNotFoundException::new);
        if(cargo.isIssuance()){
            return conversionService.convert(cargo, CargoDTO.class);
        }
        cargo.setPecCode(null);
        cargoRepositories.save(cargo);
        return conversionService.convert(cargo, CargoDTO.class);
    }
}
