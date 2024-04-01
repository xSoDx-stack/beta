package ru.pec.china.beta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pec.china.beta.service.CargoService;

@Controller
@RequestMapping("/cargo/warehouse")
public class CargoWarehouseController {

    private final CargoService cargoService;

    @Autowired
    public CargoWarehouseController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(defaultValue = "1",value = "page") int page){
        model.addAttribute("cargos", cargoService.findAll(page, 25));
        model.addAttribute("href", "/cargo/warehouse?");
        model.addAttribute("titles", "Грузы");
        return "cargo/cargo-warehouse";
    }

    @GetMapping("/unloaded")
    public String cargoListUnloaded(Model model,
                                    @RequestParam(defaultValue = "1", value = "page") int page){
        model.addAttribute("cargos", cargoService.findAllUnloaded(page, 25));
        model.addAttribute("href", "/cargo/warehouse/unloaded?");
        model.addAttribute("titles", "Грузы выгруженные");
        return "cargo/cargo-warehouse";
    }

    @GetMapping("/processed")
    public String processed(Model model,
                            @RequestParam(defaultValue = "1", value = "page") int page){
        model.addAttribute("cargos", cargoService.findAllByProcessed(page, 25));
        model.addAttribute("href", "/cargo/warehouse/processed?");
        model.addAttribute("titles", "Грузы принятые");
        return "cargo/cargo-warehouse";
    }

    @GetMapping("/issuance")
    public String issuance(Model model,
                           @RequestParam(defaultValue = "1", value = "page") int page){
        model.addAttribute("cargos", cargoService.findAllByIssuance(page, 25));
        model.addAttribute("href", "/cargo/warehouse/issuance?");
        model.addAttribute("titles", "Грузы Выданные");
        return "cargo/cargo-warehouse";
    }

}

