package ru.pec.china.beta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pec.china.beta.service.CargoService;

@Controller
@RequestMapping("/cargo/client/service")
public class CargoClientController {

    private final CargoService cargoService;

    public CargoClientController(CargoService cargoService) {
        this.cargoService = cargoService;
    }


    @GetMapping()
    public String index(Model model,
                        @RequestParam(defaultValue = "1",value = "page") int page){
        model.addAttribute("cargos", cargoService.findAll(page, 25));
        model.addAttribute("href", "/?");
        model.addAttribute("titles", "Грузы");
        return "/cargo/cargo-warehouse";
    }

}

