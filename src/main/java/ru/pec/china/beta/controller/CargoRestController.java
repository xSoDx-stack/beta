package ru.pec.china.beta.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pec.china.beta.dto.CargoDTO;
import ru.pec.china.beta.service.CargoService;
import ru.pec.china.beta.service.SearchService;
import ru.pec.china.beta.service.UploadService;
import ru.pec.china.beta.util.CargoBadSaveException;
import ru.pec.china.beta.util.CargoNotFoundException;
import ru.pec.china.beta.util.ErrorResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cargo")
public class CargoRestController {
    private final CargoService cargoService;
    private final SearchService searchService;
    private final UploadService uploadService;

    @Autowired
    public CargoRestController(CargoService cargoService, SearchService searchService, UploadService uploadService) {
        this.cargoService = cargoService;
        this.searchService = searchService;
        this.uploadService = uploadService;
    }

    @GetMapping("/search/{clientCode}")
    public CargoDTO searchByClientCode(@PathVariable("clientCode") String clientCode) throws CargoNotFoundException {
        return searchService.searchByCodeClient(clientCode);
    }

    @GetMapping("/{id}")
    public CargoDTO getByID(@PathVariable("id") UUID id) throws IllegalArgumentException, CargoNotFoundException {
            return cargoService.findByOne(id);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException( CargoNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
                "Груз не найден"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(IllegalArgumentException ex){
        ErrorResponse response = new ErrorResponse(
                "Неверно задан ID груза"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/give-all")
    public List<CargoDTO> findAllByCargo() {
        return cargoService.findAll();
    }


    @PostMapping("/save")
    public CargoDTO saveCargo(@RequestBody CargoDTO cargoDTO,
                              @AuthenticationPrincipal UserDetails userDetails) throws CargoBadSaveException, CargoNotFoundException {

        return cargoService.cargoUpdate(cargoDTO, userDetails.getUsername());
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(CargoBadSaveException ex){
        ErrorResponse response = new ErrorResponse(
                "Ошибка при обработке груза"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @PostMapping("/upload")
    public ResponseEntity<ErrorResponse> upload(@RequestParam("file") MultipartFile file){
               return uploadService.upload(file);
    }

    @PostMapping("/delete/{id}")
    public CargoDTO deletePecCode(@PathVariable("id") UUID id ) throws CargoNotFoundException {
        return cargoService.deletePecCode(id);
    }

}
