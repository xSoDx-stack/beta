package ru.pec.china.beta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pec.china.beta.service.CargoService;

@Controller
@RequestMapping("/cargo")
public class CargoController {

    private final CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping("/unloaded")
    public String CargoListUnloaded(Model model){
        model.addAttribute("cargos", cargoService.findAllUnloaded());
        return "cargo/cargo";
    }

    @GetMapping("/processed")
    public String Processed(Model model){
        model.addAttribute("cargos", cargoService.findAllByProcessed());
        return "cargo/cargo";
    }

    @GetMapping("/issuance")
    public String Issuance(Model model){
        model.addAttribute("cargos", cargoService.findAllByIssuance());
        return "cargo/cargo";
    }


}
