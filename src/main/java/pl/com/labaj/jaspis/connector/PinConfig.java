package pl.com.labaj.jaspis.connector;

import com.pi4j.io.gpio.PinMode;

import java.io.Serializable;

//@XmlRootElement
//@JsonRootName("pinConfig")
public class PinConfig implements Serializable {
    public static final String NONE = "NONE";
    private int number;
    private PinMode mode;

    @SuppressWarnings("unused")
    public PinConfig() {
    }

    private PinConfig(int number, PinMode mode) {
        this.number = number;
        this.mode = mode;
    }

    public static PinConfig pin(int number, PinMode mode) {
        return new PinConfig(number, mode);
    }

    @SuppressWarnings("unused")
    public void setNumber(int number) {
        this.number = number;
    }

    @SuppressWarnings("unused")
    public void setMode(PinMode mode) {
        this.mode = mode;
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
