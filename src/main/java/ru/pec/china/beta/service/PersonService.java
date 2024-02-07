package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pec.china.beta.entity.Person;
import ru.pec.china.beta.repositories.PersonRepositories;
import ru.pec.china.beta.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonService implements UserDetailsService {
    private final PersonRepositories personRepositories;

    @Autowired
    public PersonService(PersonRepositories personRepositories) {
        this.personRepositories = personRepositories;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepositories.findByLogin(username);

        if(person.isEmpty()){
            throw new UsernameNotFoundException("Неправильный логин или пароль");
        }
        return new PersonDetails(person.get());
    }
}
