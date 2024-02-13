package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
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
    public void registerNewPerson(PersonDTO personDTO){
        if(personDTO != null){
            if(Objects.equals(personDTO.getRole(), "ROLE_ADMIN")){
                personDTO.setRole("ROLE_ADMIN");
            }
            if(Objects.equals(personDTO.getRole(), "ROLE_MODERATOR")){
                personDTO.setRole("ROLE_MODERATOR");
            }
            personDTO.setPassword(passwordEncoder.encode(personDTO.getPassword()));
            Person person = new Person();
            person.setLogin(personDTO.getLogin());
            person.setFullName(personDTO.getFullName());
            person.setPassword(personDTO.getPassword());
            person.setRole("ROLE_OPERATOR");
            personRepositories.save(person);
        }
    }
}
