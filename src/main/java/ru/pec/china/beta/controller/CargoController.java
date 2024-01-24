package ru.pec.china.beta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.service.CargoService;

@Controller
@RequestMapping("/cargo")
public class CargoController {

    private final CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(defaultValue = "1",value = "page") int page,
                        @ModelAttribute("cargo") CargoDTO cargoDTO){
        model.addAttribute("cargos", cargoService.findAll(page, 25));
        model.addAttribute("href", "/cargo?");
        return "cargo/cargo";
    }

    @GetMapping("/unloaded")
    public String cargoListUnloaded(Model model,
                                    @RequestParam(defaultValue = "1", value = "page") int page){
        model.addAttribute("cargo",new CargoDTO());
        model.addAttribute("cargos", cargoService.findAllUnloaded(page, 25));
        model.addAttribute("href", "/cargo/unloaded?");
        return "cargo/cargo";
    }

    @GetMapping("/processed")
    public String processed(Model model,
                            @RequestParam(defaultValue = "1", value = "page") int page){
        model.addAttribute("cargo",new CargoDTO());
        model.addAttribute("cargos", cargoService.findAllByProcessed(page, 25));
        model.addAttribute("href", "/cargo/processed?");
        return "cargo/cargo";
    }

    @GetMapping("/issuance")
    public String issuance(Model model,
                           @RequestParam(defaultValue = "1", value = "page") int page){
        model.addAttribute("cargo",new CargoDTO());
        model.addAttribute("cargos", cargoService.findAllByIssuance(page, 25));
        model.addAttribute("href", "/cargo/issuance?");
        return "cargo/cargo";
    }

    @PostMapping("/process")
    public String  process (@ModelAttribute("cargo") @Validated CargoDTO cargoDTO){
        cargoService.cargoUpdate(cargoDTO);
        return "redirect:/cargo";
    }
}
