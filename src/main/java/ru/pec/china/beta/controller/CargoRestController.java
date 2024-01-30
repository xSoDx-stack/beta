package ru.pec.china.beta.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.service.CargoService;
import ru.pec.china.beta.service.SearchService;
import ru.pec.china.beta.util.CargoErrorResponse;
import ru.pec.china.beta.util.CargoNotFoundException;

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
    public CargoDTO getByID (@PathVariable("id") UUID id) throws CargoNotFoundException {
        return cargoService.findByOne(id);
    }

    @ExceptionHandler
    private ResponseEntity<CargoErrorResponse> handleException(CargoNotFoundException e){
        CargoErrorResponse response = new CargoErrorResponse(
                "Груз с таким ID не найден",System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/give-all")
    public List<CargoDTO> findAllByCargo(){
        return cargoService.findAll();
    }


    @PostMapping("/save")
    public CargoDTO saveCargo(@RequestBody CargoDTO cargoDTO) throws CargoNotFoundException {
        cargoService.cargoUpdate(cargoDTO);
        return cargoService.findByOne(cargoDTO.getId());
    }

}
