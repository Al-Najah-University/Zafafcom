package main;

import java.util.logging.*;

public class LoggerUtility {
    private static Logger logger;
    private LoggerUtility() {}

    public static Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger(LoggerUtility.class.getName());
            setupLogger();
        }
        return logger;
    }
    private static void setupLogger() {
        logger.setUseParentHandlers(false);
        SimpleFormatter simpleFormatter = new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord logRecord) {
                return logRecord.getMessage();
            }
        };

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);
    }
    public static void logInfo(Logger logger, String format, Object... args) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format(format, args));
        }
    }
}
