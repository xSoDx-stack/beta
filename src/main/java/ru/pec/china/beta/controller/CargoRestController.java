package ru.pec.china.beta.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.service.CargoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CargoRestController {
    private final CargoService cargoService;

    @Autowired
    public CargoRestController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping("/{id}")
    public CargoDTO offCanvas(@PathVariable("id") UUID id){
        CargoDTO cargoDTO = cargoService.findByOne(id);
        System.out.println(cargoDTO);
        return cargoDTO;
    }

    @GetMapping("/give-all")
    public List<CargoDTO> findAllByCargo(){
        return cargoService.findAll(1, 10).getContent();
    }

}
