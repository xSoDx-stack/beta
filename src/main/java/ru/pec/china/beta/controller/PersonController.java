package ru.pec.china.beta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pec.china.beta.dto.PersonDTO;
import ru.pec.china.beta.service.PersonService;

@Controller
@RequestMapping("/administration")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person")
    public String personList(Model model,
                             @ModelAttribute("persons") PersonDTO personDTO){
        model.addAttribute("person",personService.findAll());
        return "administration/person";
    }

    @PostMapping("/person/new")
    public String registrationPerson(@ModelAttribute("persons") PersonDTO personDTO){
        personService.registerNewPerson(personDTO);
        return "redirect:/administration/person";
    }
}
