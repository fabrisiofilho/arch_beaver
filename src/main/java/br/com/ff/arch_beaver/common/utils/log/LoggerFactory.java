package br.com.ff.arch_beaver.common.utils.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggerFactory {

    public static Logger getLogger(Class<?> aClass) {
        var loggerConfig = new LoggerConfig(aClass);
        var thread = new Thread(loggerConfig);

        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            thread.interrupt();
        }

        var logger = loggerConfig.getLogger();
        logger.setAdditive(false);
        logger.setLevel(Level.DEBUG);

        if (Boolean.TRUE.equals(LogValues.HIDE_LOGS_IN_CONSOLE)) {
            logger.detachAppender(LogValues.CONSOLE_APPENDER_NAME);
        }

        return logger;
    }
}
