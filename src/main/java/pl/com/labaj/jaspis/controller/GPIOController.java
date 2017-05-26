package pl.com.labaj.jaspis.controller;

import com.pi4j.io.gpio.PinState;

import java.util.Optional;

public interface GPIOController {
    void configure(PinConfig pinConfig);

    void high(int number);

    void low(int number);

    Optional<PinState> getState(int number);

    void toggle(int number);
}
