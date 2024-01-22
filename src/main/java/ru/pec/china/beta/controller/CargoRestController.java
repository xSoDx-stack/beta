package ru.pec.china.beta.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.service.CargoService;
import ru.pec.china.beta.service.SearchService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cargo")
public class CargoRestController {
    private final CargoService cargoService;
    private final SearchService searchService;

    @Autowired
    public CargoRestController(CargoService cargoService, SearchService searchService) {
        this.cargoService = cargoService;
        this.searchService = searchService;
    }

    @GetMapping("/search/{clientCode}")
    public CargoDTO searchByClientCode (@PathVariable("clientCode") String clientCode){
        return searchService.searchByCodeClient(clientCode);
    }

    @GetMapping("/{id}")
    public CargoDTO getByID (@PathVariable("id") UUID id){
        return cargoService.findByOne(id);
    }

    @GetMapping("/give-all")
    public List<CargoDTO> findAllByCargo(){
        return cargoService.findAll();
    }

}
