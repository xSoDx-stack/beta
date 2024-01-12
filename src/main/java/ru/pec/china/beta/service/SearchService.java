package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.repositories.CargoRepositories;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final CargoRepositories cargoRepositories;
    private final ConversionService conversionService;

    @Autowired
    public SearchService(CargoRepositories cargoRepositories, ConversionService conversionService) {
        this.cargoRepositories = cargoRepositories;
        this.conversionService = conversionService;
    }

    public List<CargoDTO> searchByAllCode(String keyword){
        return cargoRepositories.searchCode(keyword).stream().map(cargo ->
                conversionService.convert(cargo, CargoDTO.class)).collect(Collectors.toList());
    }
}
