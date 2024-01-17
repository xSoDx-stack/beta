package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.entity.Cargo;
import ru.pec.china.beta.repositories.CargoRepositories;

import java.time.ZonedDateTime;

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
        cargo.setIssuance(cargoDTO.isIssuance());
        cargo.setProcessed(cargoDTO.isProcessed());
        cargo.setPecCode(cargoDTO.getPecCode());
        cargo.setTimeOfIssue(date);
        cargoRepositories.save(cargo);
    }

    public int pageNumber(int page){
        return page <= 1 ? 0 : --page;
    }








}
