package pl.com.labaj.jaspis.connector;

import com.pi4j.io.gpio.PinState;

import java.util.Optional;

public interface GPIOConnector {
    void configure(PinConfig... pinConfigs);

    void high(int number);

    void low(int number);

    Optional<PinState> getState(int number);

    void toggle(int number);

    String printConfiguration();

}
