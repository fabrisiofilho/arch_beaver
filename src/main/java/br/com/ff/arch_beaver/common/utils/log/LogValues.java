package br.com.ff.arch_beaver.common.utils.log;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogValues {

    public static final Boolean HIDE_LOGS_IN_CONSOLE = true;
    public static final String CONSOLE_APPENDER_NAME = "CONSOLE";
    public static final String PATTERN_LOG_CONSOLE = "%date %highlight(%5level) %magenta(%relative) %(---) [%thread] %cyan(%logger{35}) %(:) %msg%n";
    public static final String PATTERN_LOG_FILE = "%date %5level %relative %(---) [%thread] %logger{35} %(:) %msg%n";
    public static final String PATTERN_TYPE_CONSOLE = "CONSOLE";
    public static final String PATTERN_TYPE_FILE = "FILE";
    public static final String LOG_FILE_SIZE = "10MB";

}
