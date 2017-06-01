package pl.com.labaj.jaspis.log;

public class LoggerFactory {
    public static Logger getLogger(Class<?> aClass) {
        return new Logger(org.slf4j.LoggerFactory.getLogger(aClass));
    }
}
