
/*
 * Copyright (c) Tom Doel 2015. Released under the BSD-3 license.
 */

package com.tomdoel.testapplication.common;

import org.apache.commons.lang.StringUtils;

import java.io.File;

public class Utils {


    private Utils() {}

    /**
     * Returns true if it is possible to create and delete files in a specified directory
     *
     * @param directory the folder to test
     * @return true if a file can be created in the specified directory
     */
    public static boolean isDirectoryWritable(final String directory) {
        File testFile = null;
        try {
            final File baseFolder = new File(directory);

            final String testfileName = "~testsavetestapplication";
            testFile = new File(baseFolder, testfileName);

            // If a previous test file exists then delete this
            if (testFile.exists()) {
                if (!testFile.delete()) {
                    return false;
                }
                if (testFile.exists()) {
                    return false;
                }
                testFile = new File(baseFolder, testfileName);
            }

            // Attempt to create a new test file
            if (!testFile.createNewFile()) {
                return false;
            }

            // Check that the new test file exists
            if (!testFile.exists()) {
                return false;
            }

            // Attempt to delete the new test file
            if (!testFile.delete()) {
                return false;
            }

            return true;
        } catch (Throwable t) {
            return false;
        } finally {
            if (testFile != null) {
                try {
                    if (testFile.exists()) {
                        testFile.delete();
                    }
                } catch (Throwable t) {

                }
            }
        }
    }




    /**
     * Compares two version strings, e.g. "1.3.1" "1.3.2" etc. Non-numeric sub-versions are permitted provided they are equal in both strings
     * @param versionString1
     * @param versionString2
     * @return 1 if version1 is greater than version2, -1 if version1 is less than version 2, or 0 if they are equal
     */
    public static int compareVersionStrings(final String versionString1, final String versionString2)
    {
        final String[] versionNumbers1 = StringUtils.isBlank(versionString1) ? new String[]{} : versionString1.split("\\.", -1);
        final String[] versionNumbers2 = StringUtils.isBlank(versionString2) ? new String[]{} : versionString2.split("\\.", -1);

        int compareIndex = 0;
        while (compareIndex < versionNumbers1.length && compareIndex < versionNumbers2.length) {
            final String subString1 = versionNumbers1[compareIndex];
            final String subString2 = versionNumbers2[compareIndex];
            if (subString1.equals(subString2)) {
                compareIndex++;
            } else {
                return Integer.signum(Integer.valueOf(subString1).compareTo(Integer.valueOf(subString2)));
            }
        }

        // If we get here it means the strings are equal or they have different numbers of substrings
        return Integer.signum(versionNumbers1.length - versionNumbers2.length);
    }
}
