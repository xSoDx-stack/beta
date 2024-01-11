package ru.pec.china.beta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pec.china.beta.service.CargoService;
import ru.pec.china.beta.service.TruckService;

@Controller
@RequestMapping("/cargo")
public class CargoController {

    private final CargoService cargoService;
    private final TruckService truckService;

    @Autowired
    public CargoController(CargoService cargoService, TruckService truckService) {
        this.cargoService = cargoService;
        this.truckService = truckService;
    }

    @GetMapping("/all")
    public String allCargoList(Model model){
        model.addAttribute("cargos", cargoService.findAllUnloaded());
        return "cargo/cargo";
    }

    @GetMapping("/all/processed")
    public String allProcessed(Model model){
        model.addAttribute("cargos", cargoService.findAllByProcessed());
        return "cargo/cargo";
    }


}
