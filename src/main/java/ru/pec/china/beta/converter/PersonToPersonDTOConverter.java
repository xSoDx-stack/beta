package ru.pec.china.beta.converter;

import org.springframework.core.convert.converter.Converter;
import ru.pec.china.beta.dto.PersonDTO;
import ru.pec.china.beta.entity.Person;
import ru.pec.china.beta.entity.Role;

import java.util.Optional;

public class PersonToPersonDTOConverter implements Converter<Person, PersonDTO> {
    @Override
    public PersonDTO convert(Person source) {
        Optional<Role> role = Optional.ofNullable(source.getRole());
        return new PersonDTO(
                source.getId(),
                source.getLogin(),
                source.getFullName(),
                source.getPassword(),
                role.map(Role::getRole).orElse(null),
                source.getRoleId()
                );
    }
}
