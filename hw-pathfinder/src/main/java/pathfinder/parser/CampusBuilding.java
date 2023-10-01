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

/**
 * This represents one immutable entry of data in campus_buildings.csv,
 * including the short and long names for buildings and other locations
 * on campus, and the (x, y) image coordinates of that location.
 */
public class CampusBuilding {

    /**
     * The short (abbreviated) name of the building.
     */
    private final String shortName;

    /**
     * The full name of the building.
     */
    private final String longName;

    /**
     * The pixel-x coordinate of the building location.
     */
    private final double x;

    /**
     * The pixel-y coordinate of the building location.
     */
    private final double y;

    /**
     * Creates a new immutable CampusBuilding with the provided attributes.
     *
     * @param shortName The abbreviated name of the building entry.
     * @param longName  The full name of the building entry.
     * @param x         The pixel-x coordinate of the location.
     * @param y         The pixel-y coordinate of the location.
     */
    public CampusBuilding(String shortName, String longName, double x, double y) {
        this.shortName = shortName;
        this.longName = longName;
        this.x = x;
        this.y = y;
    }

    /**
     * @return The abbreviated name of this building entry.
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @return The full name of this building entry.
     */
    public String getLongName() {
        return longName;
    }

    /**
     * @return The pixel-x coordinate of this building entry.
     */
    public double getX() {
        return x;
    }

    /**
     * @return The pixel-y coordinate of this building entry.
     */
    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("[Short: %s; Long: %s; Location: (%.3f, %.3f)]",
                             shortName, longName, x, y);
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }
        if(!(other instanceof CampusBuilding)) {
            return false;
        }
        if(this.hashCode() != other.hashCode()) {
            // Equal objects must have equal hashCodes, according
            // to the hashCode spec, so if their hashCodes aren't equal,
            // the objects can't be equal.
            return false;
        }
        CampusBuilding that = (CampusBuilding) other;
        return this.shortName.equals(that.shortName)
               && this.longName.equals(that.longName)
               && (Double.compare(this.x, that.x) == 0)
               && (Double.compare(this.y, that.y) == 0);
    }

    @Override
    public int hashCode() {
        int result = this.shortName.hashCode();
        result = (31 * result) + this.longName.hashCode();
        result = (31 * result) + Double.hashCode(this.x);
        result = (31 * result) + Double.hashCode(this.y);
        return result;
    }
}
