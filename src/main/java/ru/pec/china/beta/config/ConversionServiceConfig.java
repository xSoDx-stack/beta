package ru.pec.china.beta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import ru.pec.china.beta.converter.CargoDTOtoCargoConverter;
import ru.pec.china.beta.converter.CargoToTruckDTOConverter;
import ru.pec.china.beta.converter.TruckDTOtoTruckConverter;
import ru.pec.china.beta.converter.TruckToTruckDTOConverter;

@Configuration
public class ConversionServiceConfig {

    @Bean
    public ConversionService conversionService(){
        var conversionService = new DefaultConversionService();
        conversionService.addConverter(new TruckDTOtoTruckConverter());
        conversionService.addConverter(new TruckToTruckDTOConverter());
        conversionService.addConverter(new CargoDTOtoCargoConverter());
        conversionService.addConverter(new CargoToTruckDTOConverter());
        return conversionService;
    }
}