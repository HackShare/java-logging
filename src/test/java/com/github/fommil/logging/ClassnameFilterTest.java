// Copyright (c) 2013 Samuel Halliday

package com.github.fommil.logging;


import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;

import static org.junit.Assert.assertEquals;

public class ClassnameFilterTest {

    private final LogManager manager = LogManager.getLogManager();

    @Before
    public void setup() throws Exception {
        String config = ".level = SEVERE\n"
                + "com.github.fommil.logging.ClassnameFilter.level = CONFIG\n"
                + "my.package.level = INFO\n"
                + "my.package.Class.level = WARNING\n"
                + "my.package.Class$Inner.level = FINER";
        InputStream is = new ByteArrayInputStream(config.getBytes("UTF-8"));
        manager.readConfiguration(is);
    }

    @Test
    public void fqnTest() throws Exception {
        ClassnameFilter filter = new ClassnameFilter();

        assertEquals(Level.INFO, filter.getLevel(manager, "my.package"));
        assertEquals(Level.WARNING, filter.getLevel(manager, "my.package.Class"));
        assertEquals(Level.FINER, filter.getLevel(manager, "my.package.Class$Inner"));

        assertEquals(Level.WARNING, filter.getLevel(manager, "my.package.Class$Other"));
        assertEquals(Level.CONFIG, filter.getLevel(manager, ""));
        assertEquals(Level.CONFIG, filter.getLevel(manager, null));
        assertEquals(Level.CONFIG, filter.getLevel(manager, "com.another"));
    }
}
