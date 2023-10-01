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

import pathfinder.ModelAPI;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;

import java.util.Map;

/**
 * This class does most of the controller work for the text user interface version
 * of the pathfinder application. It does things like decide how to respond to input,
 * communicate requests for information or computation to the Model, instruct
 * the View on how to respond, and pass information from the Model to the View.
 */
public class TextInterfaceController implements InputHandler {

    // This class does not represent an ADT.

    /**
     * The data-carrier and processor for the application.
     */
    private ModelAPI model;

    /**
     * The user-facing view and input receiver for this application.
     */
    private TextInterfaceView view;

    /**
     * Creates a new TextInterfaceController with the provided model and view
     * classes to manage.
     *
     * @param model A model to use for computation and data.
     * @param view  A view to use to display data to the user.
     */
    public TextInterfaceController(ModelAPI model, TextInterfaceView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Begins the application by displaying some basic information
     * and prompting the user for the starting command.
     * <p>
     * On normal operation, this method does not return until the application
     * is being shut down. Calling this method transfers control of the current
     * thread to the provided {@link TextInterfaceView}.
     */
    public void launchApplication() {
        view.showMenu();
        view.basePrompt();
        view.begin();
    }

    /**
     * Responds to user commands based on the text of the command. Echoes blank commands and
     * lines beginning with "#" to the standard output.
     *
     * @param input The text command entered by the user.
     */
    @Override
    public void handleInput(String input) {
        if(input.isEmpty() || input.startsWith("#")) {
            System.out.println(input);
            return;
        }
        switch(input) {
            case "m":
                doInputM();
                break;
            case "b":
                doInputB();
                break;
            case "r":
                doInputR();
                break;
            case "q":
                doInputQ();
                break;
            default:
                doUnknownInput();
                break;
        }
    }

    /**
     * Responds properly to the user requesting the main menu for the application.
     */
    private void doInputM() {
        view.showMenu();
        view.basePrompt();
    }

    /**
     * Responds properly to the user requesting a list of all buildings and their full names.
     */
    private void doInputB() {
        Map<String, String> buildings = model.buildingNames();
        view.showBuildings(buildings);
        view.basePrompt();
    }

    /**
     * Responds properly to the user requesting a route between buildings.
     */
    private void doInputR() {
        view.promptBuildingInput("starting building");
        String start = view.blockingInput();
        view.promptBuildingInput("ending building");
        String end = view.blockingInput();
        if(!model.shortNameExists(start) || !model.shortNameExists(end)) {
            if(!model.shortNameExists(start)) {
                view.showErrorUnknownBuilding(start);
            }
            if(!model.shortNameExists(end) && !start.equals(end)) {
                view.showErrorUnknownBuilding(end);
            }
            view.basePrompt();
            return;
        }
        Path<Point> path = model.findShortestPath(start, end);
        if(path == null) {
            // No path. This is guaranteed not to happen by the homework spec,
            // so let's fall on our face if it does.
            throw new IllegalStateException("No found path between " + start + " and " + end);
        }
        view.showPath(model.longNameForShort(start), model.longNameForShort(end), path);
        view.basePrompt();
    }

    /**
     * Responds to the user requesting that the system quits.
     */
    private void doInputQ() {
        view.exit();
    }

    /**
     * Responds properly to an unknown command being input by the user.
     */
    private void doUnknownInput() {
        view.showErrorUnknownCommand();
        view.basePrompt();
    }

}
