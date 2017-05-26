package pl.com.labaj.jaspis.controller;

import com.pi4j.io.gpio.PinMode;

public class PinConfig {
    private int number;
    private PinMode mode;

    public PinConfig(int number, PinMode mode) {
        this.number = number;
        this.mode = mode;
    }

    public static PinConfig pin(int number, PinMode mode) {
        return new PinConfig(number, mode);
    }

    int getNumber() {
        return number;
    }

    PinMode getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return "Pin{" + number + " :" + mode + '}';
    }
}
