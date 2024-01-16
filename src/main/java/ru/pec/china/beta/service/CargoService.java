package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.entity.Cargo;
import ru.pec.china.beta.repositories.CargoRepositories;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CargoService {

    private final CargoRepositories cargoRepositories;
    private final ConversionService conversionService;
    private final ZonedDateTime date = ZonedDateTime.now();

    @Autowired
    public CargoService(CargoRepositories cargoRepositories, ConversionService conversionService) {
        this.cargoRepositories = cargoRepositories;
        this.conversionService = conversionService;
    }

    public List<CargoDTO> findAll(){
        return cargoRepositories.findAll().stream().map(cargo ->
                conversionService.convert(cargo, CargoDTO.class)).toList();
    }

    public List<CargoDTO> findAllUnloaded(){
        return cargoRepositories.findAllByProcessedFalse().stream().map(cargo ->
                conversionService.convert(cargo, CargoDTO.class)).collect(Collectors.toList());
    }

    public List<CargoDTO> findAllByProcessed(){
        return cargoRepositories.findAllByProcessedTrueAndIssuanceFalse().stream().map(cargo ->
                conversionService.convert(cargo, CargoDTO.class)).collect(Collectors.toList());
    }

    public List<CargoDTO> findAllByIssuance(){
        return cargoRepositories.findAllByIssuanceTrue().stream().map(cargo ->
                conversionService.convert(cargo, CargoDTO.class)).collect(Collectors.toList());
    }

    public List<CargoDTO> findAllByTrackId(Integer id){
        return cargoRepositories.findAllByTruckId(id).stream().map(cargo ->
                conversionService.convert(cargo, CargoDTO.class)).collect(Collectors.toList());
    }

    public void cargoUpdate( CargoDTO cargoDTO){
        Cargo cargo = cargoRepositories.findById(cargoDTO.getId()).orElseThrow();
        cargo.setIssuance(cargoDTO.isIssuance());
        cargo.setProcessed(cargoDTO.isProcessed());
        cargo.setPecCode(cargoDTO.getPecCode());
        cargo.setTimeOfIssue(date);
        cargoRepositories.save(cargo);
    }








}
