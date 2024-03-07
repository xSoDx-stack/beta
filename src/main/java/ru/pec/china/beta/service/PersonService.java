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
import ru.pec.china.beta.entity.PersonRole;
import ru.pec.china.beta.entity.Role;
import ru.pec.china.beta.repositories.PersonRepositories;
import ru.pec.china.beta.repositories.PersonRoleRepositories;
import ru.pec.china.beta.repositories.RoleRepositories;
import ru.pec.china.beta.security.PersonDetails;
import ru.pec.china.beta.util.PersonNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService implements UserDetailsService {
    private final PersonRepositories personRepositories;
    private final ConversionService conversionService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepositories roleRepositories;
    private final PersonRoleRepositories personRoleRepositories;

    @Autowired
    public PersonService(PersonRepositories personRepositories, ConversionService conversionService, PasswordEncoder passwordEncoder, RoleRepositories roleRepositories, PersonRoleRepositories personRoleRepositories) {
        this.personRepositories = personRepositories;
        this.conversionService = conversionService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepositories = roleRepositories;
        this.personRoleRepositories = personRoleRepositories;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepositories.findByUsername(username);

        if(person.isEmpty()){
            throw new UsernameNotFoundException("Неправильный логин или пароль");
        }
        if(person.get().isActive()){
            return new PersonDetails(person.get());
        }
        System.out.println("Пользователь отключён");
        throw new UsernameNotFoundException("Пользователь отключён");
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<PersonDTO> findAll(){
        return personRepositories.findAll().stream().map(person->
                conversionService.convert(person, PersonDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void saveOrUpdatePerson(PersonDTO personDTO) {
        ZonedDateTime date = ZonedDateTime.now();
        Person person = conversionService.convert(personDTO, Person.class);
        List<Role> roles = roleRepositories.findAllById(personDTO.getRoleId());
        if (roles.size() != personDTO.getRoleId().size()) {
            throw new RuntimeException("Одна или несколько ролей не найдены");
        }

        if (person != null && person.getId() == null) {
            person.setPassword(passwordEncoder.encode(personDTO.getPassword()));
            person.setDateTimeActive(date);
            Person savePerson = personRepositories.save(person);
            personRoleRepositories.saveAll(createPersonRoles(savePerson, roles));

        } else if (person != null){
            Person existingPerson = personRepositories.findById(person.getId()).orElseThrow(()->
                    new RuntimeException("Пользователь не найден"));
            personRoleRepositories.deleteByPersonId(existingPerson.getId());
            person.setPassword(passwordEncoder.encode(personDTO.getPassword()));
            Person savePerson = personRepositories.save(person);
            personRoleRepositories.saveAll(createPersonRoles(savePerson, roles));
        } else
            throw new RuntimeException("Пользователь не может быть null");
    }

    private List<PersonRole> createPersonRoles(Person person, List<Role> roles){
        return roles.stream()
                .map(role-> {
                    PersonRole personRole = new PersonRole();
                    personRole.setPersonId(person.getId());
                    personRole.setRoleId(role.getId());
                    return personRole;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void enableDisablePerson (int id, UserDetails userDetails) throws PersonNotFoundException {
        ZonedDateTime date = ZonedDateTime.now();
        Person person = personRepositories.findById(id).orElseThrow(PersonNotFoundException::new);
        if(!person.getUsername().equals(userDetails.getUsername())){
            person.setActive(!person.isActive());
            person.setDateTimeActive(date);
            personRepositories.save(person);
        }
        System.out.println("Невозможно удалить самого себя");
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PersonDTO findById(int id) throws PersonNotFoundException {
       return personRepositories.findById(id).map((person)->conversionService.convert(person, PersonDTO.class)).orElseThrow(PersonNotFoundException::new);
    }

}
