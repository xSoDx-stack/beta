package ru.pec.china.beta.converter;

import org.springframework.core.convert.converter.Converter;
import ru.pec.china.beta.dto.PersonDTO;
import ru.pec.china.beta.entity.Person;

import java.util.stream.Collectors;

public class PersonToPersonDTOConverter implements Converter<Person, PersonDTO> {
    @Override
    public PersonDTO convert(Person source) {
        return new PersonDTO(
                source.getId(),
                source.getUsername(),
                source.getPassword(),
                source.getFullName(),
                source.isActive(),
                source.getDateTimeActive(),
                source.getPersonRoles().stream().map(role->role.getRole().getId()).collect(Collectors.toList()),
                source.getPersonRoles().stream().map(role->role.getRole().getName()).collect(Collectors.toList())
                );
    }
}
