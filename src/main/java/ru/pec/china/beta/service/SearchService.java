package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.repositories.CargoRepositories;
import ru.pec.china.beta.util.CargoNotFoundException;

@Service
public class SearchService {
    private final CargoRepositories cargoRepositories;
    private final ConversionService conversionService;

    @Autowired
    public SearchService(CargoRepositories cargoRepositories, ConversionService conversionService) {
        this.cargoRepositories = cargoRepositories;
        this.conversionService = conversionService;
    }


    @Transactional
    public Page<CargoDTO> searchByCodeClient(String keyword, int page, int size){
        return cargoRepositories.searchCode(keyword, PageRequest.of(pageNumber(page),size)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }

    @Transactional
    public CargoDTO searchByCodeClient(String keyword) throws CargoNotFoundException {
        return conversionService.convert(cargoRepositories.searchCargoByPecCode(keyword).orElseThrow(CargoNotFoundException::new), CargoDTO.class);
    }

    public int pageNumber(int page){
        return page <= 1 ? 0 : --page;
    }

    @Transactional
    public Page<CargoDTO> searchProcessedCargo() {
        return cargoRepositories.searchCargoByProcessedTrueAndClientIssueFalseAndIssuanceIsFalse(
                PageRequest.of(0,1)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }
}
