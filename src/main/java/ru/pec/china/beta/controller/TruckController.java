package ru.pec.china.beta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pec.china.beta.dto.TruckDTO;
import ru.pec.china.beta.service.CargoService;
import ru.pec.china.beta.service.TruckService;

@Controller
@RequestMapping("/")
public class TruckController {

    private final TruckService truckService;
    private final CargoService cargoService;

    @Autowired
    public TruckController(TruckService truckService, CargoService cargoService) {
        this.truckService = truckService;
        this.cargoService = cargoService;
    }

    @GetMapping()
    public String index(Model model,
                        @ModelAttribute("truck") TruckDTO truck){
        model.addAttribute("trucks", truckService.findAll());
        return "truck/truck";
    }

    @GetMapping("/truck/delete/{id}/")
    public String delete(@PathVariable("id") int id){
        System.out.println(id);
        truckService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/truck/{id}/cargo")
    public String cargoList(@PathVariable("id") int id,
                            Model model){
        model.addAttribute("cargos", cargoService.findAllByTrackId(id));
        model.addAttribute("track_name", truckService.findById(id));
        return "cargo/cargo";
    }
}
