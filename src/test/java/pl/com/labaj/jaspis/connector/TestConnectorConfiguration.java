package pl.com.labaj.jaspis.connector;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.com.labaj.jaspis.numbering.PinNumbering;
import pl.com.labaj.jaspis.numbering.RaspiBcmPinNumbering;

import static com.pi4j.io.gpio.PinState.HIGH;
import static com.pi4j.io.gpio.PinState.LOW;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Configuration
@Import(ConnectorConfiguration.class)
public class TestConnectorConfiguration {

     @Bean
    public GpioController getGpioController() {
        GpioController gpioControllerMock = mock(GpioController.class);
        GpioPinDigitalOutput gpioPinDigitalOutputMock = mock(GpioPinDigitalOutput.class);

        final PinState[] pinState = new PinState[1];

        doReturn(gpioPinDigitalOutputMock)
                .when(gpioControllerMock).provisionDigitalOutputPin(any(Pin.class));

        doAnswer(invocationOnMock -> pinState[0] = HIGH)
                .when(gpioPinDigitalOutputMock).high();
        doAnswer(invocationOnMock -> pinState[0] = LOW)
                .when(gpioPinDigitalOutputMock).low();

        doAnswer(invocationOnMock -> pinState[0] = pinState[0] == HIGH ? LOW : HIGH)
                .when(gpioPinDigitalOutputMock).toggle();

        doAnswer(invocationOnMock -> pinState[0])
                .when(gpioPinDigitalOutputMock).getState();

        return gpioControllerMock;
    }
}
