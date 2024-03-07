package ru.pec.china.beta.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.pec.china.beta.dto.PersonDTO;
import ru.pec.china.beta.service.PersonService;
import ru.pec.china.beta.service.RoleService;
import ru.pec.china.beta.util.ErrorResponse;
import ru.pec.china.beta.util.PersonNotFoundException;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/administration")
public class PersonController {

    private final PersonService personService;
    private final RoleService roleService;

    @Autowired
    public PersonController(PersonService personService, RoleService roleService) {
        this.personService = personService;
        this.roleService = roleService;
    }

    @GetMapping("/person")
    public String personList(Model model,
                             @ModelAttribute("persons") PersonDTO personDTO){
        model.addAttribute("person",personService.findAll());
        model.addAttribute("roles", roleService.findByAll());
        return "/administration/person";
    }

    @ResponseBody
    @PostMapping("/person/save-update")
    public ResponseEntity<?> registrationPerson(@Valid @RequestBody PersonDTO personDTO,
                                                BindingResult result ) {
        if(result.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                String errorMessage = error.getDefaultMessage();
                if(errorMessage == null){
                    errorMessage = "No correct value " + error.getField();
                }
                errorMap.put(error.getField(), errorMessage);
            }
            return ResponseEntity.badRequest().body(errorMap);
        }
        personService.saveOrUpdatePerson(personDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/person/active/{id}")
    public String deletePerson(@PathVariable("id") int id,
                               @AuthenticationPrincipal UserDetails userDetails) throws PersonNotFoundException {
        personService.enableDisablePerson(id, userDetails);
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
        personService.saveOrUpdatePerson(personDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
