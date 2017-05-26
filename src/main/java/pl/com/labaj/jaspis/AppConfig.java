package pl.com.labaj.jaspis;


import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiGpioProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.labaj.jaspis.controller.GPIOController;
import pl.com.labaj.jaspis.controller.GPIOControllerImpl;
import pl.com.labaj.jaspis.numbering.PinNumbering;
import pl.com.labaj.jaspis.numbering.RaspiBcmPinNumbering;

import static com.pi4j.io.gpio.RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING;

@Configuration
public class AppConfig {

    @Bean
    public GPIOController getGpioConnector() {
        return new GPIOControllerImpl();
    }

    @Bean
    public GpioController getGpioController() {
        GpioFactory.setDefaultProvider(new RaspiGpioProvider(BROADCOM_PIN_NUMBERING));
        return GpioFactory.getInstance();
    }

    @Bean
    public PinNumbering getPinNumbering() {
        return new RaspiBcmPinNumbering();
    }
}
