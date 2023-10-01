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

package pathfinder.datastructures;

/**
 * Represents an immutable cartesian coordinate point.
 */
public class Point {

    // AF(this) =
    //      the x coordinate -> x
    //      the y coordinate -> y;

    // Rep Invariant:
    //      Double.isFinite(x) &&
    //      Double.isFinite(y)
    //   In other words, both x and y are not infinite and not NaN

    /**
     * The left object in the pair.
     */
    private final double x;

    /**
     * The right object in the pair.
     */
    private final double y;

    /**
     * Constructs a new ordered pair using the provided coordinates. Neither coordinate
     * may be NaN or infinite.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @throws IllegalArgumentException if x or y are NaN or infinite.
     */
    public Point(double x, double y) {
        if(!Double.isFinite(x) || !Double.isFinite(y)) {
            throw new IllegalArgumentException("x and y cannot be NaN or infinite.");
        }
        this.x = x;
        this.y = y;
        // checkRep not necessary, the representation fields are final and immutable.
    }

    /**
     * @return The x coordinate of this point.
     */
    public double getX() {
        return x;
    }

    /**
     * @return The y coordinate of this point.
     */
    public double getY() {
        return y;
    }

    /**
     * @param obj An object to compare {@code this} to for equality.
     * @return {@literal true} if and only if {@code obj} is a point representing the same
     * location in cartesian space.
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Point)) {
            return false;
        }
        Point other = (Point) obj;
        return (Double.compare(this.x, other.x) == 0) && (Double.compare(this.y, other.y) == 0);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int hashCode() {
        return (31 * Double.hashCode(x)) + Double.hashCode(y);
    }
}
