package ru.pec.china.beta.converter;

import org.springframework.core.convert.converter.Converter;
import ru.pec.china.beta.dto.RoleDTO;
import ru.pec.china.beta.entity.Role;

public class RoleToRoleDTOConverter implements Converter <Role, RoleDTO> {
    @Override
    public RoleDTO convert(Role source) {
        return new RoleDTO(
                source.getId(),
                source.getName()
        );
    }
}
