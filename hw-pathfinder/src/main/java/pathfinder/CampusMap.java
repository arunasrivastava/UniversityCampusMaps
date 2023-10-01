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
import graph.*;

import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static pathfinder.parser.CampusPathsParser.parseCampusBuildings;
import static pathfinder.parser.CampusPathsParser.parseCampusPaths;


public class CampusMap implements ModelAPI {
    //List storing buildings and paths between locations on campus, the campus is
    //represented as a Graph<Point, Double> object.
    //RI: buildings(0), buildings(1), ..., buildings(n) != null,
    //paths(0), paths(1), ..., paths(m) != null, buildings, paths != null,
    //map != null and contains all buildings
    //AF(this):
    //List of buildings on campus == this.buildings
    //List of possible paths between points on campus == this.paths
    //Map of campus == this.map
    private Map<String, String> shortToLong;
    private Map<String, Point> shortToPoint;
    private Graph<Point, Double> graph;

    /**
     * Constructs a new CampusMap with the campus' csv files as input
     */
    public CampusMap() {
        graph = new Graph<>();
        shortToLong = new HashMap<>();
        shortToPoint = new HashMap<>();
        List<CampusBuilding> buildings = parseCampusBuildings("campus_buildings.csv");
        List<CampusPath> paths = parseCampusPaths("campus_paths.csv");
        for (CampusBuilding b : buildings) {
            shortToPoint.put(b.getShortName(), new Point(b.getX(),b.getY()));
            shortToLong.put(b.getShortName(), b.getLongName());
        }
        for (CampusPath p : paths) {
            Point start = new Point(p.getX1(),p.getY1());
            Point end = new Point(p.getX2(),p.getY2());
            graph.addNode(start);
            graph.addNode(end);
            graph.addEdge(start, end, p.getDistance());
        }
    }
    @Override
    public boolean shortNameExists(String shortName) {
        return shortToLong.containsKey(shortName);
    }

    @Override
    public String longNameForShort(String shortName) {
        if (!shortToLong.containsKey(shortName)) {
            throw new IllegalArgumentException();
        }
        return shortToLong.get(shortName);
    }

    @Override
    public Map<String, String> buildingNames() {
        HashMap<String, String> copy = new HashMap<>(shortToLong);
        return copy;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if (startShortName == null || !shortToLong.containsKey(startShortName)) {
            throw new IllegalArgumentException();
        }
        if (endShortName == null || !shortToLong.containsKey(endShortName)) {
            throw new IllegalArgumentException();
        }
        Path<Point> path = Dijkstra.FindPath(this.graph, shortToPoint.get(startShortName), shortToPoint.get(endShortName));
        return path;
    }

}
