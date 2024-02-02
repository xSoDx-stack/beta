package ru.pec.china.beta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pec.china.beta.dto.CargoDTO;
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

    @GetMapping("favicon.ico")
    @ResponseBody
    public void disableFavicon() {
    }

    @GetMapping()
    public String index(Model model,
                        @ModelAttribute("truck") TruckDTO truck,
                        @RequestParam(defaultValue = "1", value = "page") int page){
        model.addAttribute("trucks", truckService.getCustomerPage(page, 20));
        model.addAttribute("href","/?");
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
                            Model model,
                            @RequestParam(defaultValue = "1", value = "page") int page){
        model.addAttribute("cargos", cargoService.findAllByTrackId(id, page, 25));
        model.addAttribute("trackName", truckService.TruckName(id));
        model.addAttribute("href","/truck/" + id +"/cargo?");
        return "cargo/cargo";
    }
}
