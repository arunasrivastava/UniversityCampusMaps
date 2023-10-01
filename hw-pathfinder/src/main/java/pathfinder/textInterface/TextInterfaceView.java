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

package pathfinder.textInterface;

import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.TreeSet;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * This is a view that allows interaction with a command-line user through a series of
 * commands. It passes off input events from the user to an {@link InputHandler}, which can
 * respond by calling methods of this interface.
 */
public class TextInterfaceView {

    // This class does not represent an ADT.

    /**
     * The handler that responds to all input events.
     */
    private InputHandler inputHandler;

    /**
     * Whether this interface should continue to poll for user input and maintain control of the
     * currently running thread.
     */
    private boolean active = true;

    /**
     * The input reader that receives user input.
     */
    private BufferedReader input;

    /**
     * Creates a new {@link TextInterfaceView}, initializing the user's input to the standard input.
     * The initialization of this interface must be completed by calling
     * {@link TextInterfaceView#setInputHandler(InputHandler)} and providing it an input handler.
     */
    public TextInterfaceView() {
        input = new BufferedReader(new InputStreamReader(System.in, UTF_8));
    }

    /**
     * Sets the input handler for this interface, which will be called to respond to all
     * user-input events in the future.
     *
     * @param handler The new {@link InputHandler} for this interface, which will be called
     *                whenever this interface receives user input.
     */
    public void setInputHandler(InputHandler handler) {
        this.inputHandler = handler;
    }

    /**
     * Transfers thread control to this object, where it blocks between user-input events. The
     * {@link InputHandler} that's been provided to this object will be called for input events,
     * if one has been provided. When
     * {@link InputHandler#handleInput(String)} returns, if it is called, this method continues
     * blocking indefinitely. To suspend this blocking and shut down this text interface, use
     * {@link TextInterfaceView#exit()}.
     *
     * @throws IllegalStateException if there's no {@link InputHandler} provided to this interface
     * @throws UncheckedIOException  if the user input experiences an I/O error
     */
    public void begin() {
        if(inputHandler == null) {
            throw new IllegalStateException("No InputHandler has been provided to respond to "
                                            + "user input. Call TextInterfaceView#setInputHandler()"
                                            + " first");
        }
        while(active) {
            inputHandler.handleInput(blockingInput());
        }
    }

    /**
     * Shuts down the external view and returns control to whatever method called
     * {@link TextInterfaceView#begin()}. Stops receiving user input. After this method
     * returns, this {@link TextInterfaceView} is invalid and should not be used again.
     *
     * @throws UncheckedIOException if there's an error when shutting down user input.
     */
    public void exit() {
        this.active = false;
        try {
            input.close();
        } catch(IOException ioe) {
            // See TextInterfaceView#blockingInput for an explanation.
            throw new UncheckedIOException(ioe);
        }
    }

    /**
     * Displays the main menu to the user.
     */
    public void showMenu() {
        System.out.println("Menu:");
        System.out.println("\tr to find a route");
        System.out.println("\tb to see a list of all buildings");
        System.out.println("\tq to quit");
    }

    /**
     * Displays the list of building information provided to the user, sorted alphabetically by
     * short name.
     *
     * @param buildings A mapping from building short names to their long names.
     */
    public void showBuildings(Map<String, String> buildings) {
        System.out.println("Buildings:");
        TreeSet<String> sortedShortNames = new TreeSet<>(buildings.keySet());
        for(String shortName : sortedShortNames) {
            System.out.println("\t" + shortName + ": " + buildings.get(shortName));
        }
    }

    /**
     * Displays the proved path to the user.
     *
     * @param start The long name of the building at the start of the path.
     * @param end   The long name of the building at the end of the path.
     * @param path  The path to show to the user.
     */
    public void showPath(String start, String end, Path<Point> path) {
        System.out.println("Path from " + start + " to " + end + ":");
        for(Path<Point>.Segment pathSegment : path) {
            Direction dir = Direction.resolveDirection(pathSegment.getStart().getX(),
                                                       pathSegment.getStart().getY(),
                                                       pathSegment.getEnd().getX(),
                                                       pathSegment.getEnd().getY(),
                                                       CoordinateProperties.INCREASING_DOWN_RIGHT);
            System.out.printf("\tWalk %.0f feet %s to (%.0f, %.0f)",
                              pathSegment.getCost(),
                              dir.name(),
                              pathSegment.getEnd().getX(),
                              pathSegment.getEnd().getY());
            System.out.println();
        }
        System.out.printf("Total distance: %.0f feet", path.getCost());
        System.out.println();
    }

    /**
     * Blocks until the user has inputted an complete line of text, then returns that line of
     * text and resumes operation as normal.
     *
     * @return The line of text inputted by the user.
     * @throws IllegalStateException if there's no {@link InputHandler} provided to this interface
     * @throws UncheckedIOException  if the user input experiences an I/O error
     */
    public String blockingInput() {
        if(inputHandler == null) {
            throw new IllegalStateException("No InputHandler has been provided to respond to "
                                            + "user input. Call TextInterfaceView#setInputHandler()"
                                            + " first");
        }
        String inputValue = null;
        do {
            try {
                if(input.ready()) {
                    inputValue = input.readLine();
                }
            } catch(IOException ioe) {
                // We're going to want to fail, but it makes little semantic sense for the
                // caller to have to deal with an IOException and be forced to have a try/catch
                // So, rethrowing in a manner that allows the client to deal with the
                // situation as they please (including by ignoring it) is better.
                throw new UncheckedIOException(ioe);
            }
        } while(inputValue == null);
        return inputValue;
    }

    /**
     * Displays a message to the user asking for a top-level command to be entered.
     */
    public void basePrompt() {
        System.out.println(); // Begins with a newline always.
        System.out.print("Enter an option ('m' to see the menu): ");
        System.out.flush();
    }

    /**
     * Displays a prompt to the user about inputting a building name.
     *
     * @param buildingName The name of the building to use in the prompt.
     */
    public void promptBuildingInput(String buildingName) {
        System.out.print("Abbreviated name of " + buildingName + ": ");
        System.out.flush();
    }

    /**
     * Displays an error to the user about an unknown command being used.
     */
    public void showErrorUnknownCommand() {
        System.out.println("Unknown option");
    }

    /**
     * Displays an error to the user about an unknown building.
     *
     * @param building The short name of the building that's unknown.
     */
    public void showErrorUnknownBuilding(String building) {
        System.out.println("Unknown building: " + building);
    }

}
