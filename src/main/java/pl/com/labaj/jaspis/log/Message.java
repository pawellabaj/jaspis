package pl.com.labaj.jaspis.log;

public class Message {
    private String pattern;
    private Object[] arguments;

    private Message(String pattern, Object... arguments) {
        this.pattern = pattern;
        this.arguments = arguments;
    }

    public static Message msg(String pattern, Object... arguments) {
        return new Message(pattern, arguments);
    }

    String getPattern() {
        return pattern;
    }

    Object[] getArguments() {
        return arguments;
    }
}
