package ru.pec.china.beta.converter;

import org.springframework.core.convert.converter.Converter;
import ru.pec.china.beta.dto.PersonDTO;
import ru.pec.china.beta.entity.Person;

public class PersonToPersonDTOConverter implements Converter<Person, PersonDTO> {
    @Override
    public PersonDTO convert(Person source) {
        return new PersonDTO(
                source.getId(),
                source.getFullName(),
                source.getRole()
        );
    }
}
