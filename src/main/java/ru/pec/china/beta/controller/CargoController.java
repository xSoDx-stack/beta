package ru.pec.china.beta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.entity.Cargo;
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
    public String index(Model model){
        model.addAttribute("cargos", cargoService.findAll());
        model.addAttribute("cargo",new CargoDTO());
        return "cargo/cargo";
    }

    @GetMapping("/unloaded")
    public String cargoListUnloaded(Model model){
        model.addAttribute("cargo",new CargoDTO());
        model.addAttribute("cargos", cargoService.findAllUnloaded());
        return "cargo/cargo";
    }

    @GetMapping("/processed")
    public String processed(Model model,
                            Cargo cargo){
        model.addAttribute("cargo",new CargoDTO());
        model.addAttribute("cargos", cargoService.findAllByProcessed());
        return "cargo/cargo";
    }

    @GetMapping("/issuance")
    public String issuance(Model model){
        model.addAttribute("cargo",new CargoDTO());
        model.addAttribute("cargos", cargoService.findAllByIssuance());
        return "cargo/cargo";
    }

    @PostMapping("/process")
    public String  process (@ModelAttribute("cargo") CargoDTO cargo,
                           Model model){
        System.out.println("**************************");
        System.out.println(cargo.getId());
        System.out.println(cargo.getPecCode());
        System.out.println(cargo.isProcessed());
        System.out.println(cargo.isIssuance());
        System.out.println("**************************");

        return "redirect:/cargo";
    }


}
