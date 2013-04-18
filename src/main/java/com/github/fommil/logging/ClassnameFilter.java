/* Copyright Samuel Halliday 2012 */
package com.github.fommil.logging;

import java.util.logging.*;

/**
 * Custom filter that matches categories defined in the
 * logging properties file against the classname of
 * {@link LogRecord}s.
 * <p>
 * Note that this may appear to be the behaviour of the
 * default filter, but in fact the default filter is
 * matching against the {@link Logger}'s name and
 * convention is to use classnames as logger names. This
 * is not true in Actor systems such as Akka.
 * <p>
 * Classnames need to be calculated for most log messages:
 * there is therefore a performance cost to use this filter.
 * <p>
 * The default level is specified by {@value #DEFAULT_LEVEL_PROP},
 * not {@code .level} as one might have expected. This is to workaround
 * JUL's use of {@code .level} elsewhere which may filter before reaching
 * this.
 *
 * @author Sam Halliday
 * @see <a href="http://akka.io">Akka</a>
 */
public class ClassnameFilter implements Filter {

    private static final String DEFAULT_LEVEL_PROP = "com.github.fommil.logging.ClassnameFilter.level";

    @Override
    public boolean isLoggable(LogRecord record) {
        LogManager manager = LogManager.getLogManager();
        String source = record.getSourceClassName();

        Level allowed = getLevel(manager, source);
        if (allowed == Level.OFF)
            return false;
        return (allowed.intValue() <= record.getLevel().intValue());
    }

    // recursively ascend the fqn and check the properties
    // visible for testing
    Level getLevel(LogManager manager, String fqn) {
        if (fqn == null || fqn.isEmpty()) {
            String level = manager.getProperty(DEFAULT_LEVEL_PROP);
            if (level != null)
                return Level.parse(level);
            return Level.ALL;
        }

        String level = manager.getProperty(fqn + ".level");
        if (level != null)
            return Level.parse(level);

        int dollar = fqn.lastIndexOf("$");
        if (dollar > 0) return getLevel(manager, fqn.substring(0, dollar));

        int dot = fqn.lastIndexOf(".");
        if (dot <= 0) return getLevel(manager, "");

        return getLevel(manager, fqn.substring(0, dot));
    }
}
