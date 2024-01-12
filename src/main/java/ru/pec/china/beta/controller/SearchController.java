package ru.pec.china.beta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String search(Model model, @Param("search") String search){
        if (search.isEmpty()){
            return "redirect:/";
        }
        model.addAttribute("cargos", searchService.searchByAllCode(search));
        model.addAttribute("keyword",search);
        return "cargo/cargo";
    }
}
