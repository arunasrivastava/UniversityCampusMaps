/*
 * Copyright (C) 2023 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.scriptTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * DO NOT MODIFY
 *
 * <p>This class, along with a complete *TestDriver implementation, can be used to test your graph
 * and associated code that uses your graph.
 *
 * <p>It works by parameterizing test methods over some data values, and then creating an instance
 * for the cross-product of test methods and data values. In this case, it will create one
 * ScriptFileTests instance per .expected file, and for each of those it will run the
 * checkOutput() test. See the JUnit4 Javadoc for more information.
 */
@RunWith(Parameterized.class)
public class ScriptFileTests {

    private static final FileFilter testFileFilter = file -> file.getName().endsWith(".test");

    /**
     * The directory, prefixed with a "/", under src/test/resources that should
     * be explored for *.test and *.expected files.
     */
    private static final String TEST_SCRIPTS_DIR = "/testScripts";

    /**
     * @return list of argument arrays that should be provided to the two ScriptFileTests public
     * variables by the Parameterized test runner. The first is the file to be tested itself, the
     * second is a name used as a convenience for readable test results, (see argument to the
     * {@code @Parameters} annotation).
     */
    @Parameters(name = "{1}")
    public static List<Object[]> getTestFiles() {
        List<Object[]> filesToTest = new ArrayList<>();
        //
        try {
            URL url = ScriptFileTests.class.getResource(TEST_SCRIPTS_DIR);
            if (url == null) {
                System.err.println("There are no script tests to run.");
                return filesToTest; // Empty
            }
            File baseDir = new File(url.toURI());
            if (!baseDir.exists()) {
                System.err.println("There are no script tests to run.");
                System.err.println("Expected in a directory at: " + baseDir.toString());
                return filesToTest; // Empty
            }
            if (!baseDir.isDirectory()) {
                System.err.println("There are no script tests to run.");
                System.err.println("Expected in a directory at: " + baseDir.toString());
                return filesToTest; // Empty
            }
            //
            File[] contents = baseDir.listFiles(ScriptFileTests.testFileFilter);
            if (contents == null) {
                throw new RuntimeException("Error accessing files in: " + baseDir.toString());
            }
            for (File f : contents) {
                // We're going to get only the "name" stub of the file without the extension, for printing purposes
                filesToTest.add(new Object[] {f, f.getName().split("\\.")[0]});
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to process script directory URI", e);
        }
        //
        return filesToTest;
    }

    // ============================================================
    // ============================================================

    /**
     * Reads in the contents of a file.
     *
     * @return the contents of that file
     * @throws IOException              if an error occurs accessing the file
     * @throws IllegalArgumentException if f is null or represents a non-existent file
     */
    private static String fileContents(File f) throws IOException {
        if (f == null) {
            throw new IllegalArgumentException("No file specified");
        }
        if (!f.exists()) {
            throw new IllegalArgumentException("File does not exist: " + f.toString());
        }
        if (!f.isFile()) {
            throw new IllegalArgumentException("File isn't a standard file: " + f.toString());
        }
        return Files.readString(f.toPath(), StandardCharsets.UTF_8);
    }

    /**
     * @param file         THe base file to use.
     * @param newExtension The new file extension to use
     * @return a File with the same name and location as file, but with the newExtension instead of whatever
     * extension it had before. If {@param file} had no extension, adds the newExtension to the filename.
     */
    private static File fileWithExtension(File file, String newExtension) {
        String fileName = file.getName();
        int dot = fileName.lastIndexOf('.');
        String newName;
        if (dot == -1) {
            newName = fileName + "." + newExtension;
        } else {
            newName = fileName.substring(0, dot + 1) + newExtension;
        }
        return new File(file.getParentFile(), newName);
    }

    /**
     * Runs the specified test script file, which is expected to be a valid .test file. Generates a .actual
     * file with the result of running the script, and returns the contents of that file.
     *
     * @return the contents of the output file
     * @throws IOException if an error occurs creating outputs or accessing test inputs
     */
    private static String runScriptFile(File testScriptFile) throws IOException {
        if (testScriptFile == null) {
            throw new RuntimeException("No file specified");
        }

        File actual = fileWithExtension(testScriptFile, "actual");

        Reader r = new FileReader(testScriptFile);
        Writer w = new FileWriter(actual);

        PathfinderTestDriver td = new PathfinderTestDriver(r, w);
        td.runTests();

        return fileContents(actual);
    }

    // ============================================================
    // ============================================================

    @Parameterized.Parameter(0)
    public File testScriptFile;

    @Parameterized.Parameter(1)
    public String filename;

    /**
     * The only test that is run: run a script file and test its output.
     *
     * @throws IOException on a failure reading the test files
     */
    @Test(timeout = 30000)
    public void checkOutput() throws IOException {
        File expectedFile = fileWithExtension(testScriptFile, "expected");
        if (!expectedFile.exists()) {
            throw new RuntimeException("No .expected file, cannot run tests.");
        }
        String expected = fileContents(expectedFile);
        String actual = runScriptFile(testScriptFile);

        // Perform some normalization to be more forgiving with whitespace:
        //  - Sequences of tabs and spaces are compressed to a single space character.
        //  - Whitespace characters are removed from the beginning and end of the strings.
        //  - Replaces any DOS-style line endings with unix-style line endings.

        String normalizedExpected = expected.replaceAll("[ \\t]+", " ")
                                            .replaceAll("\\r\\n", "\n")
                                            .trim();
        String normalizedActual = actual.replaceAll("[ \\t]+", " ")
                                        .replaceAll("\\r\\n", "\n")
                                        .trim();

        assertEquals(filename, normalizedExpected, normalizedActual);
    }
}
