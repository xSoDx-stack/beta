package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pec.china.beta.dto.PersonDTO;
import ru.pec.china.beta.entity.Person;
import ru.pec.china.beta.repositories.PersonRepositories;
import ru.pec.china.beta.security.PersonDetails;
import ru.pec.china.beta.util.PersonNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService implements UserDetailsService {
    private final PersonRepositories personRepositories;
    private final ConversionService conversionService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepositories personRepositories, ConversionService conversionService, PasswordEncoder passwordEncoder) {
        this.personRepositories = personRepositories;
        this.conversionService = conversionService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepositories.findByLogin(username);

        if(person.isEmpty()){
            throw new UsernameNotFoundException("Неправильный логин или пароль");
        }
        return new PersonDetails(person.get());
    }

    @Transactional(readOnly = true)
    public List<PersonDTO> findAll(){
        return personRepositories.findAll().stream().map(person->
                conversionService.convert(person, PersonDTO.class)).collect(Collectors.toList());
    }
    @Transactional
    public void registerNewPerson(PersonDTO personDTO) {

        Person person;
        if(personDTO.getId() != null){
            person = personRepositories.findById(personDTO.getId()).orElse(new Person());
        }else {
            person = new Person();
        }

        if(Objects.equals(personDTO.getRole(), "ROLE_ADMIN")){
            personDTO.setRole("ROLE_ADMIN");
        }
        else if(Objects.equals(personDTO.getRole(), "ROLE_MODERATOR")){
            personDTO.setRole("ROLE_MODERATOR");
        }else {
            personDTO.setRole("ROLE_OPERATOR");
        }

        if(personDTO.getPassword() != null){
            person.setPassword(passwordEncoder.encode(personDTO.getPassword()));
        }
        System.out.println("-----------------------------");
        System.out.println(personDTO.getPassword());
        System.out.println("-----------------------------");

        person.setLogin(personDTO.getLogin());
        person.setFullName(personDTO.getFullName());
        person.setRole(personDTO.getRole());
        personRepositories.save(person);

    }
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deletePerson(int id, UserDetails userDetails) throws PersonNotFoundException {
        Person person = personRepositories.findById(id).orElseThrow(PersonNotFoundException::new);
        if(!person.getLogin().equals(userDetails.getUsername())){
            personRepositories.delete(person);
        }
        System.out.println("Невозможно удалить самого себя");
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PersonDTO findById(int id) throws PersonNotFoundException {
       return personRepositories.findById(id).map((person)->conversionService.convert(person, PersonDTO.class)).orElseThrow(PersonNotFoundException::new);
    }
}
