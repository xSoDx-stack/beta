package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.repositories.CargoRepositories;

@Service
public class SearchService {
    private final CargoRepositories cargoRepositories;
    private final ConversionService conversionService;

    @Autowired
    public SearchService(CargoRepositories cargoRepositories, ConversionService conversionService) {
        this.cargoRepositories = cargoRepositories;
        this.conversionService = conversionService;
    }

    public Page<CargoDTO> searchByAllCode(String keyword, int page, int size){
        return cargoRepositories.searchCode(keyword, PageRequest.of(pageNumber(page),size)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }
    public int pageNumber(int page){
        return page <= 1 ? 0 : --page;
    }
}
