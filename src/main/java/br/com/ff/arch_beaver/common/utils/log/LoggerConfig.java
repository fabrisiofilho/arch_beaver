package br.com.ff.arch_beaver.common.utils.log;


import br.com.ff.arch_beaver.common.constants.PatternsRegex;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.util.FileSize;
import lombok.Getter;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoggerConfig implements Runnable {
    private final Class<?> clazz;
    @Getter
    private Logger logger;

    public LoggerConfig(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void run() {
        var loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger log = (Logger) LoggerFactory.getLogger(clazz.getName());
        log.addAppender(this.rollingFileAppender(loggerContext));
        log.addAppender(this.consoleAppender(loggerContext));
        this.logger = log;
    }

    public RollingFileAppender<ILoggingEvent> rollingFileAppender(LoggerContext loggerContext) {
        var rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
        rollingFileAppender.setContext(loggerContext);
        rollingFileAppender.setFile("logs/" + this.appenderName() + ".log");
        rollingFileAppender.setName(this.appenderName());

        var fwRollingPolicy = new FixedWindowRollingPolicy();
        fwRollingPolicy.setContext(loggerContext);
        fwRollingPolicy.setMinIndex(1);
        fwRollingPolicy.setMaxIndex(3);
        fwRollingPolicy.setFileNamePattern("logs/archive/" + this.appenderName() + "-%i.log.zip");
        fwRollingPolicy.setParent(rollingFileAppender);
        fwRollingPolicy.start();

        rollingFileAppender.setEncoder(this.patternLayoutEncoder(loggerContext, LogValues.PATTERN_TYPE_FILE));
        rollingFileAppender.setRollingPolicy(fwRollingPolicy);
        rollingFileAppender.setTriggeringPolicy(this.sizeBasedTriggeringPolicy(loggerContext));
        rollingFileAppender.start();

        return rollingFileAppender;
    }

    public ConsoleAppender<ILoggingEvent> consoleAppender(LoggerContext loggerContext) {
        var consoleAppender = new ConsoleAppender<ILoggingEvent>();
        consoleAppender.setContext(loggerContext);
        consoleAppender.setName(LogValues.CONSOLE_APPENDER_NAME);
        consoleAppender.setEncoder(this.patternLayoutEncoder(loggerContext, LogValues.PATTERN_TYPE_CONSOLE));
        consoleAppender.start();
        return consoleAppender;
    }

    public PatternLayoutEncoder patternLayoutEncoder(LoggerContext loggerContext, String pattern) {
        var encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        if (pattern.equals(LogValues.PATTERN_TYPE_FILE)) {
            encoder.setPattern(LogValues.PATTERN_LOG_FILE);
        } else if (pattern.equals(LogValues.PATTERN_TYPE_CONSOLE)) {
            encoder.setPattern(LogValues.PATTERN_LOG_CONSOLE);
        }
        encoder.start();
        return encoder;
    }

    public SizeBasedTriggeringPolicy<ILoggingEvent> sizeBasedTriggeringPolicy(LoggerContext loggerContext) {
        var triggeringPolicy = new SizeBasedTriggeringPolicy<ILoggingEvent>();
        triggeringPolicy.setContext(loggerContext);
        triggeringPolicy.setMaxFileSize(FileSize.valueOf(LogValues.LOG_FILE_SIZE));
        triggeringPolicy.start();
        return triggeringPolicy;
    }

    private String appenderName() {
        List<String> list = List.of(this.clazz.getPackageName().split(PatternsRegex.REGEX_DOT));
        return list.get(list.size() - 1);
    }
}
