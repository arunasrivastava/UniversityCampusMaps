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

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // Create a single instance of a UW Map
        CampusMap UWMap = new CampusMap();

        // Route 1: find the path between the start and end buildings
        Spark.get("/find-path", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String startBuilding = request.queryParams("start");
                String endBuilding = request.queryParams("end");
                if (startBuilding == null || endBuilding == null) {
                    Spark.halt(400, "must have start and end");
                }
                //creates the shortest path between two buildings through Djikstra's Algorithm
                Path<Point> shortestPath = UWMap.findShortestPath(startBuilding, endBuilding);
                //creates a list of PathInfo objects representing every point within the path
                List<PathInfo> path = new ArrayList<>();
                for (Path<Point>.Segment p: shortestPath) {
                    double x1 = p.getStart().getX();
                    double y1 = p.getStart().getY();
                    double x2 = p.getEnd().getX();
                    double y2 = p.getEnd().getY();
                    PathInfo current = new PathInfo(x1, y1, x2, y2);
                    path.add(current);
                }
                Gson gson = new Gson();
                return gson.toJson(path);
            }
        });

        // Route 2: Retrieve the building names
        Spark.get("/get-buildings", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Map<String, String> buildings = UWMap.buildingNames();
                Gson gson = new Gson();
                return gson.toJson(buildings);
            }
        });
    }
}