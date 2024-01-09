package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pec.china.beta.dto.TruckDTO;
import ru.pec.china.beta.repositories.CargoRepositories;
import ru.pec.china.beta.repositories.TruckRepositories;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TruckService {

    private final TruckRepositories truckRepositories;
    private final CargoRepositories cargoRepositories;
    private final ConversionService conversionService;

    @Autowired
    public TruckService(TruckRepositories truckRepositories, CargoRepositories cargoRepositories, ConversionService conversionService) {
        this.truckRepositories = truckRepositories;
        this.cargoRepositories = cargoRepositories;
        this.conversionService = conversionService;
    }


    public List<TruckDTO>findAll(){
        return truckRepositories.findAll().stream().map(truck -> conversionService.convert(truck, TruckDTO.class)).collect(Collectors.toList());
    }

    public void delete(Integer id){
        truckRepositories.findById(id).ifPresent(truckRepositories::delete);
    }

}
