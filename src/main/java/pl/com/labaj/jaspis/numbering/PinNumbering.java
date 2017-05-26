package pl.com.labaj.jaspis.numbering;

import com.pi4j.io.gpio.Pin;

import java.util.Optional;

public interface PinNumbering {
    Optional<Pin> getPin(int number);
}
