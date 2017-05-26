package pl.com.labaj.jaspis;

import java.util.Optional;

public interface GPIOConnector {
    Optional<DigitalState> high(int pin);

    Optional<DigitalState> low(int pin);
}
