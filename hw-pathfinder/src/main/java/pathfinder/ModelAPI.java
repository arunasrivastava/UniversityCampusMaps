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

package pathfinder;

import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;

import java.util.Map;

/*
In the pathfinder homework, the text user interface calls these methods to talk
to your model. In the campus paths homework, your graphical user interface
will ultimately make calls to these methods (through a web server) to
talk to your model the same way.

This is the power of the Model-View-Controller pattern, two completely different
user interfaces can use the same model to display and interact with data in
different ways, without requiring a lot of work to change things over.
*/

/**
 * This interface represents the API that the text interface
 * view/controller require models to implement.
 */
public interface ModelAPI {

    // Note: Do not change any of these method specifications, since code inside the view
    // and controller depend on this API.
    // Exception: You'll need to tweak the return type of findShortestPath to correctly
    // use your new generic Path ADT once you've edited Path.

    /**
     * @param shortName The short name of a building to query.
     * @return {@literal true} iff the short name provided exists in this campus map.
     */
    public boolean shortNameExists(String shortName);

    /**
     * @param shortName The short name of a building to look up.
     * @return The long name of the building corresponding to the provided short name.
     * @throws IllegalArgumentException if the short name provided does not exist.
     */
    public String longNameForShort(String shortName);

    /**
     * @return A mapping from all the buildings' short names to their long names in this campus map.
     */
    public Map<String, String> buildingNames();

    /**
     * Finds the shortest path, by distance, between the two provided buildings.
     *
     * @param startShortName The short name of the building at the beginning of this path.
     * @param endShortName   The short name of the building at the end of this path.
     * @return A path between {@code startBuilding} and {@code endBuilding}, or {@literal null}
     * if none exists.
     * @throws IllegalArgumentException if {@code startBuilding} or {@code endBuilding} are
     *                                  {@literal null}, or not valid short names of buildings in
     *                                  this campus map.
     */
    public Path<Point> findShortestPath(String startShortName, String endShortName);
    // You'll need to change this return type to use the generic Path once you've
    // updated the Path ADT to be generic.

}
