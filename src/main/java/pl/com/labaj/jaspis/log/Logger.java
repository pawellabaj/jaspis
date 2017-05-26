package pl.com.labaj.jaspis.log;

import java.util.function.Supplier;

public class Logger {
    private final org.slf4j.Logger logger;

    Logger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    public void warn(Supplier<Message> messageSupplier) {
        if (logger.isDebugEnabled()) {
            Message message = messageSupplier.get();
            logger.warn(message.getPattern(), message.getArguments());
        }
    }

    public void info(Supplier<Message> messageSupplier) {
        if (logger.isInfoEnabled()) {
            Message message = messageSupplier.get();
            logger.info(message.getPattern(), message.getArguments());
        }
    }


    public void error(Supplier<Message> messageSupplier) {
        if (logger.isInfoEnabled()) {
            Message message = messageSupplier.get();
            logger.error(message.getPattern(), message.getArguments());
        }
    }
}
