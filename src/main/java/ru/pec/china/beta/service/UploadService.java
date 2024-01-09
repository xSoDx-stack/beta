package ru.pec.china.beta.service;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.pec.china.beta.entity.Cargo;
import ru.pec.china.beta.entity.Person;
import ru.pec.china.beta.entity.Truck;
import ru.pec.china.beta.repositories.CargoRepositories;
import ru.pec.china.beta.repositories.PersonRepositories;
import ru.pec.china.beta.repositories.TruckRepositories;

import java.io.IOException;
import java.time.ZonedDateTime;

@Service
public class UploadService {
    private final PersonRepositories personRepositories;
    private final TruckRepositories truckRepositories;
    private final CargoRepositories cargoRepositories;

//    private final LocalDateTime  data = LocalDateTime.now();

    private final ZonedDateTime date = ZonedDateTime.now();

    @Autowired
    public UploadService(PersonRepositories personRepositories, TruckRepositories truckRepositories, CargoRepositories cargoRepositories) {
        this.personRepositories = personRepositories;
        this.truckRepositories = truckRepositories;
        this.cargoRepositories = cargoRepositories;
    }

    @Transactional
    public void upload(MultipartFile file) throws IOException {
        System.out.println(date);

        HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
        HSSFSheet sheet = wb.getSheetAt(0);
        int i = 0;
        Person person = personRepositories.findById(1).orElse(null);
        Truck truck = new Truck();
        while (true) {
            try{
                int tmp =Integer.parseInt(sheet.getRow(i).getCell(0).getStringCellValue());
                truck.setTrackName(sheet.getRow(i).getCell(1).getStringCellValue());
                truck.setDateCreate(date);
                truckRepositories.save(truck);
                break;
            }catch (NumberFormatException ignore){
                i++;
            }
        }
        i = 0;
        while (sheet.getRow(i) != null) {
            try {
                int tmp = Integer.parseInt(sheet.getRow(i).getCell(0).getStringCellValue());
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
    }
}