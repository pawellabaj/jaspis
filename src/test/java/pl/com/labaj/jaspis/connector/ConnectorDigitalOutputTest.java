package pl.com.labaj.jaspis.connector;

import com.pi4j.io.gpio.PinState;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static com.pi4j.io.gpio.PinMode.DIGITAL_OUTPUT;
import static com.pi4j.io.gpio.PinState.HIGH;
import static com.pi4j.io.gpio.PinState.LOW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static pl.com.labaj.jaspis.connector.PinConfig.pin;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConnectorConfiguration.class})
public class ConnectorDigitalOutputTest {

    private static final int PIN_NUMBER = 2;

    @Autowired
    private GPIOConnector controller;

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

}
