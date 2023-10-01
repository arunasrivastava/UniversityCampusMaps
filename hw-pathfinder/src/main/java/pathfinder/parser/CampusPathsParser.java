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

package pathfinder.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class capable of parsing data in campus buildings and
 * campus paths files in their expected formats.
 */
public class CampusPathsParser {

    /**
     * Parses the campus buildings file (in src/main/resources/data/) and
     * returns a list of objects containing all the data in that file.
     *
     * @param file The simple filename of a campus buildings file to parse.
     * @return A {@link List} of {@link CampusBuilding} objects, one for each
     * line in the parsed file, containing the data contained within that line.
     * @throws ParserException if the file cannot be found or parsed as expected
     */
    public static List<CampusBuilding> parseCampusBuildings(String file) {
        List<CampusBuilding> buildings = new ArrayList<>();
        List<String> lines = readLines(file);
        //
        for (String line : lines) {
            String[] fields = line.split(",");
            if (fields.length != 4) {
                throw new ParserException("Wrong number of fields in line.");
            }
            String shortName = fields[0];
            String longName = fields[1];
            double x, y;
            try {
                x = Double.parseDouble(fields[2]);
                y = Double.parseDouble(fields[3]);
            } catch (NumberFormatException e) {
                throw new ParserException("Cannot parse x/y coordinates as numbers", e);
            }
            //
            buildings.add(new CampusBuilding(shortName, longName, x, y));
        }
        //
        return buildings;
    }

    /**
     * Parses the campus paths file (in src/main/resources/data/) and
     * returns a list of objects containing all the data in that file.
     *
     * @param file The simple filename of a campus paths file to parse.
     * @return A {@link List} of {@link CampusPath} objects, one for each
     * line in the parsed file, containing the data contained within that line.
     * @throws ParserException if the file cannot be found or parsed as expected
     */
    public static List<CampusPath> parseCampusPaths(String file) {
        List<CampusPath> paths = new ArrayList<>();
        List<String> lines = readLines(file);
        //
        for (String line : lines) {
            String[] fields = line.split(",");
            if (fields.length != 5) {
                throw new ParserException("Wrong number of fields in line");
            }
            double x1, x2, y1, y2, distance;
            try {
                x1 = Double.parseDouble(fields[0]);
                y1 = Double.parseDouble(fields[1]);
                x2 = Double.parseDouble(fields[2]);
                y2 = Double.parseDouble(fields[3]);
                distance = Double.parseDouble(fields[4]);
            } catch (NumberFormatException e) {
                throw new ParserException("Cannot parse x/y coordinates as numbers", e);
            }
            //
            paths.add(new CampusPath(x1, y1, x2, y2, distance));
        }
        //
        return paths;
    }

    /**
     * Reads all lines contained within the provided data file, which is located
     * relative to the data/ folder in this parser's classpath.
     *
     * @param filename The file to read.
     * @throws ParserException if the file doesn't exist, has an invalid name, or can't be read
     * @return A new {@link List<String>} containing all lines in the file.
     */
    private static List<String> readLines(String filename) {
        // See MarvelParser.java (from hw-marvel) for an explanation of this code
        InputStream stream = CampusPathsParser.class.getResourceAsStream("/data/" + filename);
        if (stream == null) {
            throw new ParserException("No such file: " + filename);
        }
        return new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.toList());
    }

    /**
     * An Exception class representing an error during parsing.
     */
    public static class ParserException extends RuntimeException {

        /**
         * Creates a new ParserException with the provided message.
         *
         * @param message A message to include in the exception.
         */
        public ParserException(String message) {
            super(message);
        }

        /**
         * Creates a new ParserException with the provided message and cause.
         *
         * @param message A message to include in the exception.
         * @param cause   The exception (or other {@link Throwable}) that
         *                caused this exception to be thrown.
         */
        public ParserException(String message, Throwable cause) {
            super(message, cause);
        }

    }
}
