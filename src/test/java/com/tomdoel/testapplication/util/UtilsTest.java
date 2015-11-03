/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.util;

import com.google.common.io.Files;
import com.tomdoel.testapplication.common.Utils;
import junit.framework.Assert;
import org.junit.Test;

import java.io.File;

public class UtilsTest {

    @Test
    public void testIsDirectoryWritable() throws Exception {
        File tempDir = Files.createTempDir();
        final String tempDirString = tempDir.getPath();
        Assert.assertTrue(Utils.isDirectoryWritable(tempDirString));

        final String rootDir = "/";
        Assert.assertFalse(Utils.isDirectoryWritable(rootDir));
    }

    @Test
    public void testCompareVersionStrings() throws Exception {
        Assert.assertEquals(0, Utils.compareVersionStrings("", ""));
        Assert.assertEquals(0, Utils.compareVersionStrings("1", "1"));
        Assert.assertEquals(0, Utils.compareVersionStrings("1.0", "1.0"));
        Assert.assertEquals(0, Utils.compareVersionStrings("1.2.0", "1.2.0"));
        Assert.assertEquals(0, Utils.compareVersionStrings("1.3.2", "1.3.2"));

        Assert.assertEquals(-1, Utils.compareVersionStrings("", "1"));
        Assert.assertEquals(-1, Utils.compareVersionStrings("1", "1.0"));
        Assert.assertEquals(-1, Utils.compareVersionStrings("1", "2"));
        Assert.assertEquals(-1, Utils.compareVersionStrings("1", "1."));
        Assert.assertEquals(-1, Utils.compareVersionStrings("1.2.2", "1.3.0"));
        Assert.assertEquals(-1, Utils.compareVersionStrings("1.2.2", "2.2.2"));
        Assert.assertEquals(-1, Utils.compareVersionStrings("1.2.2", "1.3.2"));
        Assert.assertEquals(-1, Utils.compareVersionStrings("1.3.2", "1.3.3"));
        Assert.assertEquals(-1, Utils.compareVersionStrings("1.3.2", "1.3.2.0"));
        Assert.assertEquals(-1, Utils.compareVersionStrings("1.3.2", "1.3.2.1"));
        Assert.assertEquals(-1, Utils.compareVersionStrings("1.3.2", "1.3.2.debug"));

        Assert.assertEquals(1, Utils.compareVersionStrings("1", ""));
        Assert.assertEquals(1, Utils.compareVersionStrings("1.0", "1"));
        Assert.assertEquals(1, Utils.compareVersionStrings("2", "1"));
        Assert.assertEquals(1, Utils.compareVersionStrings("1.", "1"));
        Assert.assertEquals(1, Utils.compareVersionStrings("1.3.2", "1.2.0"));
        Assert.assertEquals(1, Utils.compareVersionStrings("2.2.2", "1.2.2"));
        Assert.assertEquals(1, Utils.compareVersionStrings("1.3.2", "1.2.2"));
        Assert.assertEquals(1, Utils.compareVersionStrings("1.3.3", "1.3.2"));
        Assert.assertEquals(1, Utils.compareVersionStrings("1.3.2.0", "1.3.2"));
        Assert.assertEquals(1, Utils.compareVersionStrings("1.3.2.1", "1.3.2"));
        Assert.assertEquals(1, Utils.compareVersionStrings("1.3.2.debug", "1.3.2"));
    }
}