package ru.pec.china.beta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pec.china.beta.dto.PersonDTO;
import ru.pec.china.beta.service.PersonService;
import ru.pec.china.beta.util.ErrorResponse;
import ru.pec.china.beta.util.PersonNotFoundException;

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

    @GetMapping("/person/delete/{id}")
    public String deletePerson(@PathVariable("id") int id,
                               @AuthenticationPrincipal UserDetails userDetails) throws PersonNotFoundException {
        personService.deletePerson(id, userDetails);
        return "redirect:/administration/person";
    }

    @ResponseBody
    @GetMapping("/person/get/{id}")
    public PersonDTO getPersonById(@PathVariable("id") int id) throws PersonNotFoundException {
            return personService.findById(id);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(PersonNotFoundException ex){
        ErrorResponse response = new ErrorResponse(
                "Пользователь не найден"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @PostMapping("/person/save")
    private ResponseEntity<HttpStatus> editPerson(@RequestBody PersonDTO personDTO){
        personService.registerNewPerson(personDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
