package pl.com.labaj.jaspis.connector;


import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiGpioProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.labaj.jaspis.log.Logger;
import pl.com.labaj.jaspis.log.LoggerFactory;
import pl.com.labaj.jaspis.numbering.PinNumbering;
import pl.com.labaj.jaspis.numbering.RaspiBcmPinNumbering;

import static com.pi4j.io.gpio.RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING;

@Configuration
public class ConnectorConfiguration {
    private Logger logger = LoggerFactory.getLogger(ConnectorConfiguration.class);

    @Bean
    public GPIOConnector getGpioConnector() {
        return new GPIOConnectorImpl();
    }

    @Bean
    public PinNumbering getPinNumbering() {
        return new RaspiBcmPinNumbering();
    }

    @Bean
    public GpioController getGpioController() {
        GpioFactory.setDefaultProvider(new RaspiGpioProvider(BROADCOM_PIN_NUMBERING));
        return GpioFactory.getInstance();
    }
}
