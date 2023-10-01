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

/**
 * Represents one of the 4 major or 4 minor compass directions, as is capable of resolving the
 * direction between two points or from the origin to a point.
 */
public enum Direction {

    /**
     * North
     */
    N,

    /**
     * Northeast
     */
    NE,

    /**
     * East
     */
    E,

    /**
     * Southeast
     */
    SE,

    /**
     * South
     */
    S,

    /**
     * Southwest
     */
    SW,

    /**
     * West
     */
    W,

    /**
     * Northwest
     */
    NW;

    /**
     * Determines the direction represented by the ray cast from {@literal (0, 0)} to {@literal
     * (x, y)} in a cartesian plane, after accounting for differing coordinate system basis
     * directions using the options in {@link CoordinateProperties}. In the case that a
     * coordinate pair borders on the end between two directions, the more counter-clockwise
     * of the two is returned.
     *
     * @param x         The x-coordinate of the head of the ray being measured.
     * @param y         The y-coordinate of the head of the ray being measured.
     * @param coordType The type of coordinate system that {@code x} and {@code y} exist in.
     * @return The direction represented by the ray from the origin to {@literal (x, y)}.
     * @throws IllegalArgumentException if the calculation cannot be completed, such as if {@code x}
     *                                  or {@code y} are NaN.
     */
    public static Direction resolveDirection(double x, double y, CoordinateProperties coordType) {
        return resolveDirection(0.0D, 0.0D, x, y, coordType);
    }

    /**
     * Determines the direction represented by the ray cast from {@literal (x1, y1)} to {@literal
     * (x2, y2)} in a cartesian plane, after accounting for differing coordinate system basis
     * directions using the options in {@link CoordinateProperties}. In the case that a
     * coordinate pair borders on the end between two directions, the more counter-clockwise
     * of the two is returned.
     *
     * @param x1        The x-coordinate of the base of the ray being measured.
     * @param y1        The y-coordinate of the base of the ray being measured.
     * @param x2        The x-coordinate of the head of the ray being measured.
     * @param y2        The y-coordinate of the head of the ray being measured.
     * @param coordType The type of coordinate system that {@code x1, x2, y1, y2} exist in.
     * @return The direction represented by the ray from {@literal (x1, y1)} to {@literal (x2, y2)}.
     * @throws IllegalArgumentException if the calculation cannot be completed, such as if
     *                                  {@code x1}, {@code x2}, {@code y1}, or {@code y2} are NaN.
     */
    public static Direction resolveDirection(double x1, double y1, double x2, double y2,
                                             CoordinateProperties coordType) {
        double normX = x2 - x1;
        double normY = y2 - y1;
        switch(coordType) {
            case INCREASING_UP_RIGHT:
                break;
            case INCREASING_UP_LEFT:
                normX *= -1.0D;
                break;
            case INCREASING_DOWN_RIGHT:
                normY *= -1.0D;
                break;
            case INCREASING_DOWN_LEFT:
                normX *= -1.0D;
                normY *= -1.0D;
                break;
            default:
                break;
        }
        double theta = Math.atan2(normY, normX);
        if(Double.isNaN(theta)) {
            throw new IllegalArgumentException(
                    "Distance calculation from the point: (" + x1 + ", " + y1 + ") to (" +
                    x2 + ", " + y2 + ") failed.");
        }
        if(Double.compare(theta, -Math.PI) >= 0 &&
           Double.compare(theta, -7.0D * Math.PI / 8.0D) < 0) {
            return W;
        } else if(Double.compare(theta, -7.0D * Math.PI / 8.0D) >= 0 &&
                  Double.compare(theta, -5.0D * Math.PI / 8.0D) < 0) {
            return SW;
        } else if(Double.compare(theta, -5.0D * Math.PI / 8.0D) >= 0 &&
                  Double.compare(theta, -3.0D * Math.PI / 8.0D) < 0) {
            return S;
        } else if(Double.compare(theta, -3.0D * Math.PI / 8.0D) >= 0 &&
                  Double.compare(theta, -1.0D * Math.PI / 8.0D) < 0) {
            return SE;
        } else if(Double.compare(theta, -1.0D * Math.PI / 8.0D) >= 0 &&
                  Double.compare(theta, 1.0D * Math.PI / 8.0D) < 0) {
            return E;
        } else if(Double.compare(theta, 1.0D * Math.PI / 8.0D) >= 0 &&
                  Double.compare(theta, 3.0D * Math.PI / 8.0D) < 0) {
            return NE;
        } else if(Double.compare(theta, 3.0D * Math.PI / 8.0D) >= 0 &&
                  Double.compare(theta, 5.0D * Math.PI / 8.0D) < 0) {
            return N;
        } else if(Double.compare(theta, 5.0D * Math.PI / 8.0D) >= 0 &&
                  Double.compare(theta, 7.0D * Math.PI / 8.0D) < 0) {
            return NW;
        } else if(Double.compare(theta, 7.0D * Math.PI / 8.0D) >= 0 &&
                  Double.compare(theta, Math.PI) <= 0) {
            return W;
        } else {
            throw new IllegalArgumentException(
                    "Distance calculation from the point: (" + x1 + ", " + y1 + ") to (" +
                    x2 + ", " + y2 + ") failed.");
        }
    }
}
