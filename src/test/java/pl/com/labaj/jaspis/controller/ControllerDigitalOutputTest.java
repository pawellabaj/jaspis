package pl.com.labaj.jaspis.controller;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.com.labaj.jaspis.AppConfig;

import java.util.Optional;

import static com.pi4j.io.gpio.PinMode.DIGITAL_OUTPUT;
import static com.pi4j.io.gpio.PinState.HIGH;
import static com.pi4j.io.gpio.PinState.LOW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pl.com.labaj.jaspis.controller.PinConfig.pin;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ControllerDigitalOutputTest.TestConfig.class})
public class ControllerDigitalOutputTest {

    private static final int PIN_NUMBER = 2;

    @Autowired
    private GPIOController controller;

    @Before
    public void setUp() {
        controller.configure(pin(PIN_NUMBER, DIGITAL_OUTPUT));
    }

    @Test
    public void testHigh() {
        //given

        //when
        controller.high(PIN_NUMBER);

        //then
        Optional<PinState> state = controller.getState(PIN_NUMBER);
        assertThat(state).isNotEmpty().hasValue(HIGH);
    }

    @Test
    public void testLow() {
        //given

        //when
        controller.low(PIN_NUMBER);

        //then
        Optional<PinState> state = controller.getState(PIN_NUMBER);
        assertThat(state).isNotEmpty().hasValue(LOW);
    }

    @Test
    public void testHighToggle() {
        //given

        //when
        controller.high(PIN_NUMBER);
        controller.toggle(PIN_NUMBER);

        //then
        Optional<PinState> state = controller.getState(PIN_NUMBER);
        assertThat(state).isNotEmpty().hasValue(LOW);
    }

    @Test
    public void testLowToggle() {
        //given

        //when
        controller.low(PIN_NUMBER);
        controller.toggle(PIN_NUMBER);

        //then
        Optional<PinState> state = controller.getState(PIN_NUMBER);
        assertThat(state).isNotEmpty().hasValue(HIGH);
    }

    @Configuration
    @Import(AppConfig.class)
    public static class TestConfig {

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
}
