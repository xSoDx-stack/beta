package ru.pec.china.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.entity.Cargo;
import ru.pec.china.beta.entity.Person;
import ru.pec.china.beta.repositories.CargoRepositories;
import ru.pec.china.beta.repositories.PersonRepositories;
import ru.pec.china.beta.util.CargoBadSaveException;
import ru.pec.china.beta.util.CargoNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

public class CargoService {

    private final CargoRepositories cargoRepositories;
    private final ConversionService conversionService;
    private final PersonRepositories personRepositories;

    @Autowired
    public CargoService(CargoRepositories cargoRepositories, ConversionService conversionService, PersonRepositories personRepositories) {
        this.cargoRepositories = cargoRepositories;
        this.conversionService = conversionService;
        this.personRepositories = personRepositories;
    }

    @Transactional
    public CargoDTO findByOne(UUID id) throws CargoNotFoundException {
        return cargoRepositories.findById(id).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class)).orElseThrow(CargoNotFoundException::new);
    }

    @Transactional
    public Page<CargoDTO> findAll(int page, int size){
        return cargoRepositories.findAll(PageRequest.of(pageNumber(page), size)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }

    @Transactional
    public List<CargoDTO>findAll(){
        return cargoRepositories.findAll().stream().map(cargo ->
                conversionService.convert(cargo, CargoDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public Page<CargoDTO> findAllUnloaded(int page, int size){
        return cargoRepositories.findAllByProcessedFalse(PageRequest.of(pageNumber(page), size)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }

    @Transactional
    public Page<CargoDTO> findAllByProcessed(int page, int size){
        return cargoRepositories.findAllByProcessedTrueAndIssuanceFalse(PageRequest.of(pageNumber(page),size)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }

    @Transactional
    public Page<CargoDTO> findAllByIssuance(int page, int size){
        return cargoRepositories.findAllByIssuanceTrue(PageRequest.of(pageNumber(page),size)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }

    @Transactional
    public Page<CargoDTO> findAllByTrackId(Integer id, int page, int size){
        return cargoRepositories.findAllByTruckIdOrderByTimeClientIssue(id, PageRequest.of(pageNumber(page), size)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }

    @Transactional
    public Page<CargoDTO> findAllByTrackIdAndCargoProcessedTrue(Integer id, int page, int size){
        return cargoRepositories.findAllByTruckIdAndProcessedTrue(id, PageRequest.of(pageNumber(page), size)).map(cargo ->
                conversionService.convert(cargo, CargoDTO.class));
    }

    @Transactional
    public CargoDTO cargoUpdateWarehouse(CargoDTO cargoDTO, String userDetails) throws CargoNotFoundException, CargoBadSaveException {
        ZonedDateTime date = ZonedDateTime.now();
        Cargo cargo = cargoRepositories.findById(cargoDTO.getId()).orElseThrow(CargoNotFoundException::new);
        Person person = personRepositories.findByUsername(userDetails).orElseThrow(null);

        if(cargoDTO.getPecCode() == null)
            cargoDTO.setPecCode("");

        if(cargoDTO.isProcessed() & !cargoDTO.getPecCode().isEmpty() ) {
            cargo.setProcessedByUserId(person.getId());
            cargo.setProcessed(true);
            cargo.setPecCode(cargoDTO.getPecCode());
            cargo.setTimeOfProcessed(date);
            cargo.setProcessedByUserId(person.getId());
            if (cargo.isClientIssue()) {
                cargo.setIssuedToClientByUserId(person.getId());
                cargo.setTimeClientIssue(date);
                cargo.setClientIssue(true);
                if (cargoDTO.isIssuance()) {
                    cargo.setIssuedAtWarehouseByUserId(person.getId());
                    cargo.setIssuance(true);
                    cargo.setTimeOfIssue(date);
                }
            }
            cargoRepositories.save(cargo);
            return conversionService.convert(cargo, CargoDTO.class);
        }
        throw new CargoBadSaveException();
    }

    @Transactional
    public CargoDTO cargoUpdateClient(CargoDTO cargoDTO, int userDetails) throws CargoNotFoundException, CargoBadSaveException {
        ZonedDateTime date = ZonedDateTime.now();
        Cargo cargo = cargoRepositories.findById(cargoDTO.getId()).orElseThrow(CargoNotFoundException::new);
        Person person = personRepositories.findById(userDetails).orElseThrow(null);

        if(cargo.isProcessed() && !cargo.isIssuance()){
            cargo.setClientIssue(true);
            cargo.setNote(cargoDTO.getNote());
            cargo.setIssuedToClientByUserId(person.getId());
            cargo.setTimeClientIssue(date);
            cargo.setShippingToRegions(cargoDTO.isShippingToRegions());
            cargoRepositories.save(cargo);
            return conversionService.convert(cargo, CargoDTO.class);
        }
        throw new CargoBadSaveException();
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_OPERATOR')")
    public CargoDTO cargoRelabeling(CargoDTO cargoDTO, int userDetails) throws CargoNotFoundException, CargoBadSaveException {
        ZonedDateTime date = ZonedDateTime.now();
        Cargo cargo = cargoRepositories.findById(cargoDTO.getId()).orElseThrow(CargoNotFoundException::new);
        Person person = personRepositories.findById(userDetails).orElseThrow(null);

        if(cargo.isProcessed() && !cargo.isIssuance()){
            if(cargoDTO.getPecCode().isBlank() && cargo.getOldPecCode().equals(cargoDTO.getPecCode())){
                cargo.setPecCode(cargo.getOldPecCode());
                cargo.setOldPecCode(null);
                cargo.setRelabelingPecCode(false);
            }
            else {
                cargo.setPecCode(cargoDTO.getPecCode());
                cargo.setShippingToRegions(true);
                cargo.setRelabelingPecCode(true);
            }

            cargo.setClientIssue(true);
            cargo.setTimeClientIssue(date);
            cargo.setIssuedToClientByUserId(person.getId());
            cargoRepositories.save(cargo);
            return conversionService.convert(cargo, CargoDTO.class);
        }
        throw new CargoBadSaveException();
    }

    private int pageNumber(int page){
        return page <= 1 ? 0 : --page;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_OPERATOR')")
    public CargoDTO deletePecCode(UUID id) throws CargoNotFoundException {
        Cargo cargo = cargoRepositories.findById(id).orElseThrow(CargoNotFoundException::new);
        if(cargo.isIssuance()){
            return conversionService.convert(cargo, CargoDTO.class);
        }
        cargo.setPecCode(null);
        cargoRepositories.save(cargo);
        return conversionService.convert(cargo, CargoDTO.class);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_SPECIALIST')")
    public CargoDTO relabelingPecCode(UUID id) throws CargoNotFoundException {
        Cargo cargo = cargoRepositories.findById(id).orElseThrow(CargoNotFoundException::new);
        if(cargo.isIssuance()){
            return conversionService.convert(cargo, CargoDTO.class);
        }
        cargo.setOldPecCode(cargo.getPecCode());
        cargo.setPecCode(null);

        cargoRepositories.save(cargo);
        return conversionService.convert(cargo, CargoDTO.class);

    }
}
