package ru.pec.china.beta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pec.china.beta.service.CargoService;

@Controller
@RequestMapping("/cargo/client/service")
public class CargoOfficeController {

    private final CargoService cargoService;

    public CargoOfficeController(CargoService cargoService) {
        this.cargoService = cargoService;
    }



}

