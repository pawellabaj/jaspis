package pl.com.labaj.jaspis.connector;

import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.com.labaj.jaspis.log.Logger;
import pl.com.labaj.jaspis.log.LoggerFactory;
import pl.com.labaj.jaspis.numbering.PinNumbering;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.pi4j.io.gpio.PinMode.DIGITAL_OUTPUT;
import static java.util.stream.Collectors.toList;
import static pl.com.labaj.jaspis.log.Message.msg;

public class GPIOConnectorImpl implements GPIOConnector {
    private Logger logger = LoggerFactory.createLogger(getClass());

    private Map<Integer, Optional<? extends GpioPin>> pins = new HashMap<>();

    @Autowired
    private PinNumbering pinNumbering;

    @Autowired
    private GpioController gpioController;

    @Override
    public void configure(List<PinConfig> configList) {
        configList.forEach(this::configure);
    }

    @Override
    public void configure(PinConfig pinConfig) {
        int number = pinConfig.getNumber();
        PinMode pinMode = pinConfig.getMode();

        Optional<GpioPinDigitalOutput> pin = null;

        if (pins.containsKey(number) && pins.get(number).isPresent()) {
            logger.warn(() -> msg("Pin {} is already configured", number));
        }

        switch (pinMode) {
            case DIGITAL_OUTPUT:
                pin = provisionDigitalOutputPin(number);
        }

        pins.put(number, pin);
    }


    @Override
    public void high(int number) {
        configureDigitalOutputIfMissing(number);
        pins.get(number)
                .filter(GpioPinDigitalOutput.class::isInstance)
                .map(GpioPinDigitalOutput.class::cast)
                .ifPresent(GpioPinDigitalOutput::high);

    }


    @Override
    public void low(int number) {
        configureDigitalOutputIfMissing(number);
        pins.get(number)
                .filter(GpioPinDigitalOutput.class::isInstance)
                .map(GpioPinDigitalOutput.class::cast)
                .ifPresent(GpioPinDigitalOutput::low);
    }

    @Override
    public void toggle(int number) {
        configureDigitalOutputIfMissing(number);
        pins.get(number)
                .filter(GpioPinDigitalOutput.class::isInstance)
                .map(GpioPinDigitalOutput.class::cast)
                .ifPresent(GpioPinDigitalOutput::toggle);
    }

    @Override
    public Optional<PinState> getState(int number) {
        configureDigitalOutputIfMissing(number);
        return pins.get(number)
                .filter(GpioPinDigitalOutput.class::isInstance)
                .map(GpioPinDigitalOutput.class::cast)
                .map(GpioPinDigital::getState);
    }

    @Override
    public String printConfiguration() {
        return pins.entrySet().stream()
                .map(entry -> entry.getKey() + " : " + (entry.getValue().isPresent() ? entry.getValue().get() : "NONE"))
                .collect(toList())
                .toString();
    }

    private void configureDigitalOutputIfMissing(int number) {
        if (!pins.containsKey(number) || !pins.get(number).isPresent()) {
            logger.info(() -> msg("Pin {} has not been configured. Try to configure as {}", number, DIGITAL_OUTPUT));
            pins.put(number, provisionDigitalOutputPin(number));
        }
    }

    private Optional<GpioPinDigitalOutput> provisionDigitalOutputPin(int number) {
        Optional<Pin> pinOptional = pinNumbering.getPin(number);
        return pinOptional.map(pin -> gpioController.provisionDigitalOutputPin(pin));
    }
}
