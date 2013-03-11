/* Copyright Samuel Halliday 2012 */
package com.github.fommil.logging;

import java.util.logging.*;

/**
 * Custom filter that matches categories defined in the
 * logging properties file against the classname of
 * {@link LogRecord}s.
 * <p/>
 * Note that this may appear to be the behaviour of the
 * default filter, but in fact the default filter is
 * matching against the {@link Logger}'s name and
 * convention is to use classnames as logger names. This
 * is not true in Actor systems such as Akka.
 * <p/>
 * Classnames need to be calculated for most log messages:
 * there is therefore a performance cost to use this filter.
 *
 * @author Sam Halliday
 * @see <a href="http://akka.io">Akka</a>
 */
public class ClassnameFilter implements Filter {

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
        String prop = fqn == null ? ".level" : fqn + ".level";
        String level = manager.getProperty(prop);
        if (level != null)
            return Level.parse(level);
        if (fqn == null || fqn.isEmpty())
            return Level.ALL;

        int dollar = fqn.lastIndexOf("$");
        if (dollar > 0) return getLevel(manager, fqn.substring(0, dollar));

        int dot = fqn.lastIndexOf(".");
        if (dot <= 0) return getLevel(manager, "");

        return getLevel(manager, fqn.substring(0, dot));
    }
}
