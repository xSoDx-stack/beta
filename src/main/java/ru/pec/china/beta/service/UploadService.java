package ru.pec.china.beta.service;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.pec.china.beta.entity.Cargo;
import ru.pec.china.beta.entity.Truck;
import ru.pec.china.beta.repositories.CargoRepositories;
import ru.pec.china.beta.repositories.TruckRepositories;
import ru.pec.china.beta.util.ErrorResponse;

import java.time.ZonedDateTime;

@Service
public class UploadService {
    private final TruckRepositories truckRepositories;
    private final CargoRepositories cargoRepositories;
    private final ZonedDateTime date = ZonedDateTime.now();

    @Autowired
    public UploadService(TruckRepositories truckRepositories, CargoRepositories cargoRepositories) {
        this.truckRepositories = truckRepositories;
        this.cargoRepositories = cargoRepositories;
    }

    @Transactional
    public ResponseEntity<ErrorResponse> upload(MultipartFile file){
        ErrorResponse exception = new ErrorResponse(
                "Неверный формат файла"
        );
        try {
            HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
            HSSFSheet sheet = wb.getSheetAt(0);

            int i = 0;
            Truck truck = new Truck();
            while (true) {
                try {
                    Integer.parseInt(sheet.getRow(i).getCell(0).getStringCellValue());
                    truck.setTrackName(sheet.getRow(i).getCell(1).getStringCellValue());
                    truck.setDateCreate(date);
                    truckRepositories.save(truck);
                    break;
                } catch (NumberFormatException ignore) {
                    i++;
                }
            }
            i = 0;
            while (sheet.getRow(i) != null) {
                try {
                    Integer.parseInt(sheet.getRow(i).getCell(0).getStringCellValue());
                    Cargo cargo = new Cargo();
                    cargo.setClientBarcode(sheet.getRow(i).getCell(2).getStringCellValue());
                    cargo.setNumberOfSeats((int) sheet.getRow(i).getCell(4).getNumericCellValue());
                    cargo.setWeight(sheet.getRow(i).getCell(5).getNumericCellValue());
                    cargo.setVolume(sheet.getRow(i).getCell(6).getNumericCellValue());
                    cargo.setCity(sheet.getRow(i).getCell(9).getStringCellValue());
                    cargo.setLocalOrTransshipment(sheet.getRow(i).getCell(12).getStringCellValue());
                    cargo.setTruckId(truck.getId());
                    cargoRepositories.save(cargo);
                    i++;
                } catch (NumberFormatException ignore) {
                    i++;
                }
            }

        }catch (Exception ex){
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
        ErrorResponse excellent = new ErrorResponse(
                "Файл успешно загружен"
        );
        return new ResponseEntity<>(excellent, HttpStatus.OK);
    }
}
