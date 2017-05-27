package pl.com.labaj.jaspis.connector;

import com.pi4j.io.gpio.PinState;

import java.util.List;
import java.util.Optional;

public interface GPIOConnector {
    void configure(List<PinConfig> configList);

    void configure(PinConfig pinConfig);

    void high(int number);

    void low(int number);

    Optional<PinState> getState(int number);

    void toggle(int number);

    String printConfiguration();

}
