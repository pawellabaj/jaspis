package pl.com.labaj.jaspis.numbering;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiBcmPin;
import pl.com.labaj.jaspis.log.Logger;
import pl.com.labaj.jaspis.log.LoggerFactory;

import java.util.Optional;

import static java.util.Objects.isNull;
import static pl.com.labaj.jaspis.log.Message.msg;

public class RaspiBcmPinNumbering implements PinNumbering {

    private Logger logger = LoggerFactory.getLogger(RaspiBcmPinNumbering.class);

    @Override
    public Optional<Pin> getPin(int number) {
        Pin pin = RaspiBcmPin.getPinByAddress(number);
        if (isNull(pin)) {
            logger.error(() -> msg("Pin with number {} cannot be provisioned", number));
        }
        return Optional.ofNullable(pin);
    }
}
