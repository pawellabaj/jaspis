import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.com.labaj.jaspis.DigitalState;
import pl.com.labaj.jaspis.GPIOConnector;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BasicTest.Config.class)
public class BasicTest {

    private GPIOConnector connector;

    @Test
    public void testHigh() {
        //given

        //when
        Optional<DigitalState> state = connector.high(1);

        //then
        assertThat(state.isPresent()).isTrue();
        assertThat(state.get()).isEqualTo(DigitalState.HIGH);
    }

    @Test
    public void testLow() {
        //given

        //when
        Optional<DigitalState> state = connector.low(1);

        //then
        assertThat(state.isPresent()).isTrue();
        assertThat(state.get()).isEqualTo(DigitalState.LOW);
    }


    @Configuration
    class Config {
    }
}
