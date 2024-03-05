package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.pec.china.beta.dto.RoleDTO;
import ru.pec.china.beta.repositories.RoleRepositories;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepositories roleRepositories;
    private final ConversionService conversionService;

    @Autowired
    public RoleService(RoleRepositories roleRepositories, ConversionService conversionService) {
        this.roleRepositories = roleRepositories;
        this.conversionService = conversionService;
    }

    public List<RoleDTO> findByAll(){
        return roleRepositories.findAll().stream().map(role ->
                conversionService.convert(role, RoleDTO.class)).collect(Collectors.toList());
    }
}
