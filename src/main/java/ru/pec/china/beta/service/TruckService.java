package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pec.china.beta.dto.TruckDTO;
import ru.pec.china.beta.repositories.TruckRepositories;

@Service
@Transactional
public class TruckService {

    private final TruckRepositories truckRepositories;
    private final ConversionService conversionService;

    @Autowired
    public TruckService(TruckRepositories truckRepositories, ConversionService conversionService) {
        this.truckRepositories = truckRepositories;
        this.conversionService = conversionService;
    }

    public Page<TruckDTO> getCustomerPage(int page, int size) {
        return truckRepositories.findAllByOrderByDateCreateDesc(PageRequest.of(page, size)).map(customer ->
                conversionService.convert(customer, TruckDTO.class));
    }

    public void delete(Integer id){
        truckRepositories.findById(id).ifPresent(truckRepositories::delete);
    }

}
