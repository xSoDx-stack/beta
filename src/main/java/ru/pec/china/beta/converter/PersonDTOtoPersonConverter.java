package ru.pec.china.beta.converter;


import org.springframework.core.convert.converter.Converter;
import ru.pec.china.beta.dto.PersonDTO;
import ru.pec.china.beta.entity.Person;

public class PersonDTOtoPersonConverter implements Converter<PersonDTO, Person> {
    @Override
    public Person convert(PersonDTO source) {
        var person = new Person();
        person.setId(source.getId());
        person.setLogin(source.getLogin());
        person.setFullName(source.getFullName());
        person.setRole(source.getRole());
        return person;

    }
}
