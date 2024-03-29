package ru.pec.china.beta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pec.china.beta.service.SearchService;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping()
    public String search(Model model, @RequestParam("search") String search,
                         @RequestParam(defaultValue = "1", value = "page") int page){
        if (search.isEmpty()){
            return "redirect:/";
        }
        model.addAttribute("href", "?search=" + search + "&");
        model.addAttribute("cargos", searchService.searchByCodeClient(search, page, 25));
        return "/cargo/cargo-warehouse";
    }
}
