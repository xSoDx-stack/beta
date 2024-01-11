package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.repositories.CargoRepositories;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CargoService {

    private final CargoRepositories cargoRepositories;
    private final ConversionService conversionService;

    @Autowired
    public CargoService(CargoRepositories cargoRepositories, ConversionService conversionService) {
        this.cargoRepositories = cargoRepositories;
        this.conversionService = conversionService;
    }

    public List<CargoDTO> findAllUnloaded(){
        return cargoRepositories.findAllByProcessedFalse().stream().map(cargo ->
                conversionService.convert(cargo, CargoDTO.class)).collect(Collectors.toList());
    }

    public List<CargoDTO> findAllByProcessed(){
        return cargoRepositories.findAllByProcessedTrueAndIssuanceFalse().stream().map(cargo ->
                conversionService.convert(cargo, CargoDTO.class)).collect(Collectors.toList());
    }

    public List<CargoDTO> findAllByTrackId(Integer id){
        return cargoRepositories.findAllByTruckId(id).stream().map(cargo ->
                conversionService.convert(cargo, CargoDTO.class)).collect(Collectors.toList());
    }








}
